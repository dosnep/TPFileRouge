package forms;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import org.joda.time.DateTime;

import beans.ClientB;
import beans.CommandeB;
import dao.ClientDao;
import dao.CommandeDao;
import eu.medsea.mimeutil.MimeUtil;
import exception.DaoException;

public class HandleFormClient {

	private static final String NAME_PARAM_NON_CLIENT = "nomClient";
	private static final String NAME_PARAM_PRENOM_CLIENT = "prenomClient";
	private static final String NAME_PARAM_ADR_CLIENT = "adresseClient";
	private static final String NAME_PARAM_TEL_CLIENT = "telephoneClient";
	private static final String NAME_PARAM_EMAIL_CLIENT = "emailClient";

	private static final String NAME_PARAM_DATE_COMMANDE = "dateCommande";
	private static final String NAME_PARAM_MONTANT_COMMANDE = "montantCommande";
	private static final String NAME_PARAM_MODE_PAIEMENT_COMMANDE = "modePaiementCommande";
	private static final String NAME_PARAM_STATUT_PAIEMENT_COMMANDE = "statutPaiementCommande";
	private static final String NAME_PARAM_MODE_LIVRAISON_COMMANDE = "modeLivraisonCommande";
	private static final String NAME_PARAM_STATUT_LIVRAISON_COMMANDE = "statutLivraisonCommande";
	private static final int TAILLE_TAMPON = 10240; // 10 ko

	private static final String ATTR_SESSION_LIST_CLIENT = "listClient";
	private static final String NAME_PARAM_FILE = "fichier";
	private static final String DAO_UTILISATEUR_ERROR = "daoUserError";
	private static final String DAO_COMMANDE_ERROR = "daoCommandeError";

	private HashMap<String, String> errors = new HashMap<String, String>();

	private String resultat;

	public ClientB inscrireClient(HttpServletRequest request, ClientDao clientDao, String chemin) {

		ClientB client = new ClientB();
		String nomClient = request.getParameter(NAME_PARAM_NON_CLIENT);
		String prenomClient = request.getParameter(NAME_PARAM_PRENOM_CLIENT);
		String adresseClient = request.getParameter(NAME_PARAM_ADR_CLIENT);
		String telephoneClient = request.getParameter(NAME_PARAM_TEL_CLIENT);
		String emailClient = request.getParameter(NAME_PARAM_EMAIL_CLIENT);

		client.setNomClient(nomClient);
		client.setPrenomClient(prenomClient);
		client.setAdresseClient(adresseClient);
		client.setTelephoneClient(telephoneClient);
		client.setEmailClient(emailClient);
		client.setPathProfilePic(null);
		client.setId(null);

		try {
			checkSize(NAME_PARAM_NON_CLIENT, nomClient, 2);
		} catch (Exception e) {
			errors.put(NAME_PARAM_NON_CLIENT, e.getMessage());
		}

		try {
			checkSize(NAME_PARAM_PRENOM_CLIENT, prenomClient, 2);
		} catch (Exception e) {
			errors.put(NAME_PARAM_PRENOM_CLIENT, e.getMessage());
		}

		try {
			checkSize(NAME_PARAM_ADR_CLIENT, adresseClient, 10);
		} catch (Exception e) {
			errors.put(NAME_PARAM_ADR_CLIENT, e.getMessage());
		}

		try {
			checkSize(NAME_PARAM_TEL_CLIENT, telephoneClient, 4);

		} catch (Exception e) {
			errors.put(NAME_PARAM_TEL_CLIENT, e.getMessage());
		}

		// try {
		// checkTelephone(telephoneClient);
		// } catch (Exception e) {
		// errors.put(NAME_PARAM_TEL_CLIENT, e.getMessage());
		// }

		try {
			validationEmail(emailClient);
		} catch (Exception e) {
			errors.put(NAME_PARAM_EMAIL_CLIENT, e.getMessage());
		}

		try {
			String nomFichier = null;
			Part part = request.getPart(NAME_PARAM_FILE);
			nomFichier = getNomFichier(part);
			InputStream contenuFichier = null;
			if (nomFichier != null && !nomFichier.isEmpty()) {
				/*
				 * Antibug pour Internet Explorer, qui transmet pour une raison
				 * mystique le chemin du fichier local à la machine du client...
				 * 
				 * Ex : C:/dossier/sous-dossier/fichier.ext
				 * 
				 * On doit donc faire en sorte de ne sélectionner que le nom et
				 * l'extension du fichier, et de se débarrasser du superflu.
				 */
				nomFichier = nomFichier.substring(nomFichier.lastIndexOf('/') + 1)
						.substring(nomFichier.lastIndexOf('\\') + 1);
				contenuFichier = part.getInputStream();

				MimeUtil.registerMimeDetector("eu.medsea.mimeutil.detector.MagicMimeMimeDetector");
				Collection<?> mimeTypes = MimeUtil.getMimeTypes(contenuFichier);
				if (mimeTypes.toString().startsWith("image")) {

					try {
						ecrireFichier(contenuFichier, nomFichier, chemin);
						client.setPathProfilePic(nomFichier);
					} catch (Exception e) {
						errors.put(NAME_PARAM_FILE, e.getMessage());
					}
				}

				else {
					errors.put(NAME_PARAM_FILE, "Le fichier doit être de type image");
				}

			}

		} catch (IllegalStateException e) {
			e.printStackTrace();
			errors.put(NAME_PARAM_FILE, "Les données envoyées sont trop volumineuses.");
		} catch (IOException e) {
			e.printStackTrace();
			errors.put(NAME_PARAM_FILE, "Erreur de configuration du serveur.");
		} catch (ServletException e) {
			e.printStackTrace();
			errors.put(NAME_PARAM_FILE,
					"Ce type de requête n'est pas supporté, merci d'utiliser le formulaire prévu pour envoyer votre fichier.");
		}

		try {
			clientDao.creer(client);
		} catch (DaoException e) {
			errors.put(DAO_UTILISATEUR_ERROR, e.getMessage());
		}
		if (errors.isEmpty())
			resultat = "Inscription réussie";
		else
			resultat = "Erreur lors de l'inscription";

		return client;

	}

	public ClientB inscrireClient(HttpServletRequest request, String chemin) {

		ClientB client = new ClientB();
		String nomClient = request.getParameter(NAME_PARAM_NON_CLIENT);
		String prenomClient = request.getParameter(NAME_PARAM_PRENOM_CLIENT);
		String adresseClient = request.getParameter(NAME_PARAM_ADR_CLIENT);
		String telephoneClient = request.getParameter(NAME_PARAM_TEL_CLIENT);
		String emailClient = request.getParameter(NAME_PARAM_EMAIL_CLIENT);

		client.setNomClient(nomClient);
		client.setPrenomClient(prenomClient);
		client.setAdresseClient(adresseClient);
		client.setTelephoneClient(telephoneClient);
		client.setEmailClient(emailClient);
		client.setPathProfilePic(null);

		try {
			checkSize(NAME_PARAM_NON_CLIENT, nomClient, 2);
		} catch (Exception e) {
			errors.put(NAME_PARAM_NON_CLIENT, e.getMessage());
		}

		try {
			checkSize(NAME_PARAM_PRENOM_CLIENT, prenomClient, 2);
		} catch (Exception e) {
			errors.put(NAME_PARAM_PRENOM_CLIENT, e.getMessage());
		}

		try {
			checkSize(NAME_PARAM_ADR_CLIENT, adresseClient, 10);
		} catch (Exception e) {
			errors.put(NAME_PARAM_ADR_CLIENT, e.getMessage());
		}

		try {
			checkSize(NAME_PARAM_TEL_CLIENT, telephoneClient, 4);

		} catch (Exception e) {
			errors.put(NAME_PARAM_TEL_CLIENT, e.getMessage());
		}

		// try {
		// checkTelephone(telephoneClient);
		// } catch (Exception e) {
		// errors.put(NAME_PARAM_TEL_CLIENT, e.getMessage());
		// }

		try {
			validationEmail(emailClient);
		} catch (Exception e) {
			errors.put(NAME_PARAM_EMAIL_CLIENT, e.getMessage());
		}

		try {
			String nomFichier = null;
			Part part = request.getPart(NAME_PARAM_FILE);
			nomFichier = getNomFichier(part);
			InputStream contenuFichier = null;
			if (nomFichier != null && !nomFichier.isEmpty()) {
				/*
				 * Antibug pour Internet Explorer, qui transmet pour une raison
				 * mystique le chemin du fichier local à la machine du client...
				 * 
				 * Ex : C:/dossier/sous-dossier/fichier.ext
				 * 
				 * On doit donc faire en sorte de ne sélectionner que le nom et
				 * l'extension du fichier, et de se débarrasser du superflu.
				 */
				nomFichier = nomFichier.substring(nomFichier.lastIndexOf('/') + 1)
						.substring(nomFichier.lastIndexOf('\\') + 1);
				contenuFichier = part.getInputStream();

				MimeUtil.registerMimeDetector("eu.medsea.mimeutil.detector.MagicMimeMimeDetector");
				Collection<?> mimeTypes = MimeUtil.getMimeTypes(contenuFichier);
				if (mimeTypes.toString().startsWith("image")) {

					try {
						ecrireFichier(contenuFichier, nomFichier, chemin);
						client.setPathProfilePic(nomFichier);
					} catch (Exception e) {
						errors.put(NAME_PARAM_FILE, e.getMessage());
					}
				}

				else {
					errors.put(NAME_PARAM_FILE, "Le fichier doit être de type image");
				}

			}

		} catch (IllegalStateException e) {
			e.printStackTrace();
			errors.put(NAME_PARAM_FILE, "Les données envoyées sont trop volumineuses.");
		} catch (IOException e) {
			e.printStackTrace();
			errors.put(NAME_PARAM_FILE, "Erreur de configuration du serveur.");
		} catch (ServletException e) {
			e.printStackTrace();
			errors.put(NAME_PARAM_FILE,
					"Ce type de requête n'est pas supporté, merci d'utiliser le formulaire prévu pour envoyer votre fichier.");
		}

		if (errors.isEmpty())
			resultat = "Inscription réussie";
		else
			resultat = "Erreur lors de l'inscription";

		return client;

	}

	public CommandeB enregistrerCommandeWithInitUser(HttpServletRequest request, CommandeDao commandeDao,
			ClientDao clientDao, String chemin) {

		CommandeB commande = new CommandeB();
		ClientB client = new ClientB();

		String nomClient = request.getParameter(NAME_PARAM_NON_CLIENT);
		String prenomClient = request.getParameter(NAME_PARAM_PRENOM_CLIENT);
		String adresseClient = request.getParameter(NAME_PARAM_ADR_CLIENT);
		String telephoneClient = request.getParameter(NAME_PARAM_TEL_CLIENT);
		String emailClient = request.getParameter(NAME_PARAM_EMAIL_CLIENT);

		client.setNomClient(nomClient);
		client.setPrenomClient(prenomClient);
		client.setAdresseClient(adresseClient);
		client.setTelephoneClient(telephoneClient);
		client.setEmailClient(emailClient);
		client.setPathProfilePic(null);
		client.setId(null);

		String dateCommande = request.getParameter(NAME_PARAM_DATE_COMMANDE);
		String modeLivraisonCommande = request.getParameter(NAME_PARAM_MODE_LIVRAISON_COMMANDE);
		String modePaiementCommande = request.getParameter(NAME_PARAM_MODE_PAIEMENT_COMMANDE);
		String montantCommande = request.getParameter(NAME_PARAM_MONTANT_COMMANDE);
		String statutPaiementCommande = request.getParameter(NAME_PARAM_STATUT_PAIEMENT_COMMANDE);
		String statutLivraisonCommande = request.getParameter(NAME_PARAM_STATUT_LIVRAISON_COMMANDE);

		DateTime date = DateTime.now().withTimeAtStartOfDay();
		commande.setDateCommande(date.toString("yyyy-MM-dd"));
		commande.setModeLivraisonCommande(modeLivraisonCommande);
		commande.setModePaiementCommande(modePaiementCommande);
		commande.setMontantCommande(montantCommande);
		commande.setStatutPaiementCommande(statutPaiementCommande);
		commande.setStatutLivraisonCommande(statutLivraisonCommande);
		commande.setId(null);
		commande.setClient(null);

		try {
			checkSize(NAME_PARAM_STATUT_PAIEMENT_COMMANDE, statutPaiementCommande, 2);
		} catch (Exception e) {
			errors.put(NAME_PARAM_STATUT_PAIEMENT_COMMANDE, e.getMessage());
		}

		try {
			checkSize(NAME_PARAM_STATUT_LIVRAISON_COMMANDE, statutLivraisonCommande, 2);
		} catch (Exception e) {
			errors.put(NAME_PARAM_STATUT_LIVRAISON_COMMANDE, e.getMessage());
		}

		try {
			checkSize(NAME_PARAM_MODE_PAIEMENT_COMMANDE, modePaiementCommande, 2);
		} catch (Exception e) {
			errors.put(NAME_PARAM_MODE_PAIEMENT_COMMANDE, e.getMessage());
		}

		try {
			checkSize(NAME_PARAM_MODE_LIVRAISON_COMMANDE, modeLivraisonCommande, 2);
		} catch (Exception e) {
			errors.put(NAME_PARAM_MODE_LIVRAISON_COMMANDE, e.getMessage());
		}

		try {
			checkPositiveValue(montantCommande);
		} catch (Exception e) {
			errors.put(NAME_PARAM_MONTANT_COMMANDE, e.getMessage());
		}

		try {
			checkSize(NAME_PARAM_NON_CLIENT, nomClient, 2);
		} catch (Exception e) {
			errors.put(NAME_PARAM_NON_CLIENT, e.getMessage());
		}

		try {
			checkSize(NAME_PARAM_PRENOM_CLIENT, prenomClient, 2);
		} catch (Exception e) {
			errors.put(NAME_PARAM_PRENOM_CLIENT, e.getMessage());
		}

		try {
			checkSize(NAME_PARAM_ADR_CLIENT, adresseClient, 10);
		} catch (Exception e) {
			errors.put(NAME_PARAM_ADR_CLIENT, e.getMessage());
		}

		// try {
		// checkTelephone(telephoneClient);
		// } catch (Exception e) {
		// errors.put(NAME_PARAM_TEL_CLIENT, e.getMessage());
		// }

		try {
			validationEmail(emailClient);
		} catch (Exception e) {
			errors.put(NAME_PARAM_EMAIL_CLIENT, e.getMessage());
		}

		try {
			String nomFichier = null;
			Part part = request.getPart(NAME_PARAM_FILE);
			nomFichier = getNomFichier(part);
			InputStream contenuFichier = null;
			if (nomFichier != null && !nomFichier.isEmpty()) {
				/*
				 * Antibug pour Internet Explorer, qui transmet pour une raison
				 * mystique le chemin du fichier local à la machine du client...
				 * 
				 * Ex : C:/dossier/sous-dossier/fichier.ext
				 * 
				 * On doit donc faire en sorte de ne sélectionner que le nom et
				 * l'extension du fichier, et de se débarrasser du superflu.
				 */
				nomFichier = nomFichier.substring(nomFichier.lastIndexOf('/') + 1)
						.substring(nomFichier.lastIndexOf('\\') + 1);
				contenuFichier = part.getInputStream();

				MimeUtil.registerMimeDetector("eu.medsea.mimeutil.detector.MagicMimeMimeDetector");
				Collection<?> mimeTypes = MimeUtil.getMimeTypes(contenuFichier);
				if (mimeTypes.toString().startsWith("image")) {

					try {
						ecrireFichier(contenuFichier, nomFichier, chemin);
						client.setPathProfilePic(nomFichier);
					} catch (Exception e) {
						errors.put(NAME_PARAM_FILE, e.getMessage());
					}
				}

				else {
					errors.put(NAME_PARAM_FILE, "Le fichier doit être de type image");
				}
			}

		} catch (IllegalStateException e) {
			e.printStackTrace();
			errors.put(NAME_PARAM_FILE, "Les données envoyées sont trop volumineuses.");
		} catch (IOException e) {
			e.printStackTrace();
			errors.put(NAME_PARAM_FILE, "Erreur de configuration du serveur.");
		} catch (ServletException e) {
			e.printStackTrace();
			errors.put(NAME_PARAM_FILE,
					"Ce type de requête n'est pas supporté, merci d'utiliser le formulaire prévu pour envoyer votre fichier.");
		}

		try {
			clientDao.creer(client);
			commande.setClient(client);
		} catch (DaoException e) {
			errors.put(DAO_UTILISATEUR_ERROR, e.getMessage());
		}

		try {
			commandeDao.creer(commande);
		} catch (DaoException e) {
			errors.put(DAO_COMMANDE_ERROR, e.getMessage());
			System.out.println(e.getMessage());
		}

		if (errors.isEmpty())
			resultat = "Inscription réussie";
		else
			resultat = "Erreur lors de l'inscription";

		return commande;

	}

	public CommandeB enregistrerCommandeFromExistingUser(HttpServletRequest request, CommandeDao commandeDao,
			Long idClient) {

		CommandeB commande = new CommandeB();
		HttpSession session = request.getSession();

		HashMap<Long, ClientB> tmp = (HashMap<Long, ClientB>) session.getAttribute(ATTR_SESSION_LIST_CLIENT);
		ClientB client = tmp.get(idClient);
		String dateCommande = request.getParameter(NAME_PARAM_DATE_COMMANDE);
		String modeLivraisonCommande = request.getParameter(NAME_PARAM_MODE_LIVRAISON_COMMANDE);
		String modePaiementCommande = request.getParameter(NAME_PARAM_MODE_PAIEMENT_COMMANDE);
		String montantCommande = request.getParameter(NAME_PARAM_MONTANT_COMMANDE);
		String statutPaiementCommande = request.getParameter(NAME_PARAM_STATUT_PAIEMENT_COMMANDE);
		String statutLivraisonCommande = request.getParameter(NAME_PARAM_STATUT_LIVRAISON_COMMANDE);

		commande.setClient(client);
		DateTime date = DateTime.now().withTimeAtStartOfDay();
		commande.setDateCommande(date.toString("yyyy-MM-dd"));
		commande.setModeLivraisonCommande(modeLivraisonCommande);
		commande.setModePaiementCommande(modePaiementCommande);
		commande.setMontantCommande(montantCommande);
		commande.setStatutPaiementCommande(statutPaiementCommande);
		commande.setStatutLivraisonCommande(statutLivraisonCommande);
		commande.setId(null);

		try {
			checkSize(NAME_PARAM_STATUT_PAIEMENT_COMMANDE, statutPaiementCommande, 2);
		} catch (Exception e) {
			errors.put(NAME_PARAM_STATUT_PAIEMENT_COMMANDE, e.getMessage());
		}

		try {
			checkSize(NAME_PARAM_STATUT_LIVRAISON_COMMANDE, statutLivraisonCommande, 2);
		} catch (Exception e) {
			errors.put(NAME_PARAM_STATUT_LIVRAISON_COMMANDE, e.getMessage());
		}

		try {
			checkSize(NAME_PARAM_MODE_PAIEMENT_COMMANDE, modePaiementCommande, 2);
		} catch (Exception e) {
			errors.put(NAME_PARAM_MODE_PAIEMENT_COMMANDE, e.getMessage());
		}

		try {
			checkSize(NAME_PARAM_MODE_LIVRAISON_COMMANDE, modeLivraisonCommande, 2);
		} catch (Exception e) {
			errors.put(NAME_PARAM_MODE_LIVRAISON_COMMANDE, e.getMessage());
		}

		try {
			checkPositiveValue(montantCommande);
		} catch (Exception e) {
			errors.put(NAME_PARAM_MONTANT_COMMANDE, e.getMessage());
		}

		try {
			commandeDao.creer(commande);
		} catch (DaoException e) {
			errors.put(DAO_COMMANDE_ERROR, e.getMessage());
			System.out.println(e.getMessage());
		}

		if (errors.isEmpty())
			resultat = "Inscription réussie";
		else
			resultat = "Erreur lors de l'inscription";

		return commande;

	}

	public void checkSize(String elemName, String elem, int minSize) throws Exception {

		if (elem != null) {
			if (elem.trim().length() < minSize)
				throw new Exception("La taille de " + elemName + " doit être de " + minSize + " minimum");
		} else {
			throw new Exception("La taille de " + elemName + " doit être de " + minSize + " minimum");
		}
	}

	public void checkPositiveValue(String value) throws Exception {
		if (value != null) {
			if (value != null && value.substring(0, 1).equals("-"))
				throw new Exception("La valeur indiquée doit être positive");
		} else {
			throw new Exception("Renseignez ce champ");

		}
	}

	public void validationEmail(String email) throws Exception {
		if (email != null && email.trim().length() != 0) {
			if (!email.matches("([^.@]+)(\\.[^.@]+)*@([^.@]+\\.)+([^.@]+)")) {
				throw new Exception("Merci de saisir une adresse mail valide.");
			}
		} else {
			throw new Exception("Merci de saisir une adresse mail.");
		}
	}

	public void checkIfNoAlphabeticValue(String num) throws Exception {

		if (num != null) {
			if (num.matches("^[0-9]")) {
				throw new Exception("Le numéro de téléphone ne doit contenir QUE des chiffres");
			}
		} else {
			throw new Exception("Renseignez ce champ");
		}

	}

	public void checkTelephone(String num) throws Exception {

		if (num != null) {
			if (num.trim().length() < 4)
				throw new Exception("La taille du numéro doit être de 4 au minimum");
			else if (num.matches("([a-z]+")) {
				throw new Exception("Le numéro de téléphone ne doit contenir QUE des chiffres");
			}

		} else {
			throw new Exception("Renseignez ce champ");
		}
	}

	private static String getNomFichier(Part part) {
		/*
		 * Boucle sur chacun des paramètres de l'en-tête "content-disposition".
		 */
		for (String contentDisposition : part.getHeader("content-disposition").split(";")) {
			/* Recherche de l'éventuelle présence du paramètre "filename". */
			if (contentDisposition.trim().startsWith("filename")) {
				/*
				 * Si "filename" est présent, alors renvoi de sa valeur,
				 * c'est-à-dire du nom de fichier sans guillemets.
				 */
				return contentDisposition.substring(contentDisposition.indexOf('=') + 1).trim().replace("\"", "");
			}
		}
		/* Et pour terminer, si rien n'a été trouvé... */
		return null;
	}

	private void ecrireFichier(InputStream part, String nomFichier, String chemin) throws IOException {
		/* Prépare les flux. */
		BufferedInputStream entree = null;
		BufferedOutputStream sortie = null;
		try {
			/* Ouvre les flux. */
			entree = new BufferedInputStream(part, TAILLE_TAMPON);
			sortie = new BufferedOutputStream(new FileOutputStream(new File(chemin + nomFichier)), TAILLE_TAMPON);

			byte[] tampon = new byte[TAILLE_TAMPON];
			int longueur;
			while ((longueur = entree.read(tampon)) > 0) {
				sortie.write(tampon, 0, longueur);
			}

		} finally {
			try {
				sortie.close();
			} catch (IOException ignore) {
			}
			try {
				entree.close();
			} catch (IOException ignore) {
			}
		}
	}

	public HashMap<String, String> getErrors() {
		return errors;
	}

	public void setErrors(HashMap<String, String> errors) {
		this.errors = errors;
	}

	public String getResultat() {
		return resultat;
	}

	public void setResultat(String resultat) {
		this.resultat = resultat;
	}

}
