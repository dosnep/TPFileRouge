package dao;

import static dao.DAOUtilitaire.fermetureSilencieuse;
import static dao.DAOUtilitaire.initialisationRequetePreparee;
import static dao.DAOUtilitaire.map;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import com.mysql.jdbc.PreparedStatement;

import beans.CommandeB;
import exception.DaoException;

public class CommandoDaoImpl implements CommandeDao {

	private DaoFactory factory;

	private final static String INSERER_COMMANDE = "insert into tp_filerouge.Commande(id_client, date, montant,"
			+ "mode_paiement, statut_paiement, mode_livraison, statut_livraison) values(?, ?, ?, ?, ?, ?, ?);";

	private static final String SUPPRIMER_COMMANDE = "delete from tp_filerouge.Commande where id = ?";

	private static final String RECUPERER_TOUTES_LES_COMMANDES = "select * from tp_filerouge.Commande;";

	private final static String FIND_ASSOCIATE_CLIENT = "select * from tp_filerouge.Client where id = ?;";

	public CommandoDaoImpl(DaoFactory factory) {
		this.factory = factory;
	}

	@Override
	public void creer(CommandeB commande) throws DaoException {
		// TODO Auto-generated method stub
		PreparedStatement request = null;
		ResultSet valeursAutoGenerees = null;

		// Préparer une requête
		try {
			request = initialisationRequetePreparee(factory.getConnection(), INSERER_COMMANDE, true,
					commande.getClient().getId(), commande.getDateCommande(), commande.getMontantCommande(),
					commande.getModePaiementCommande(), commande.getStatutPaiementCommande(),
					commande.getModeLivraisonCommande(), commande.getStatutLivraisonCommande());

			int status = request.executeUpdate();
			// On teste si la requête a bien ete executee
			if (status == 0) {
				throw new DaoException("Erreur lors de l'execution de la requete");
			}

			else {
				// On recupere l'id du tuple et on la reporte à notre bean
				valeursAutoGenerees = request.getGeneratedKeys();
				if (valeursAutoGenerees.next()) {
					commande.setId(valeursAutoGenerees.getLong(1));
				} else {
					throw new DaoException("Erreur retour de la valeur générée");
				}

			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			throw new DaoException(e.getMessage());
		} finally {
			if (request != null)
				fermetureSilencieuse(request);
		}
	}

	@Override
	public void supprimer(Long id) throws DaoException {
		// TODO Auto-generated method stub
		PreparedStatement request = null;
		ResultSet valeursAutoGenerees = null;

		// Préparer une requête
		try {
			request = initialisationRequetePreparee(factory.getConnection(), SUPPRIMER_COMMANDE, false, id);
			int status = request.executeUpdate();
			// On teste si la requête a bien ete executee
			if (status == 0) {
				throw new DaoException("Erreur lors de l'execution de la requete");
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			throw new DaoException(e.getMessage());
		} finally {
			if (request != null)
				fermetureSilencieuse(request);
		}
	}

	@Override
	public HashMap<Long, CommandeB> lister() throws DaoException {
		// TODO Auto-generated method stub
		HashMap<Long, CommandeB> allCommande = new HashMap<>();
		PreparedStatement requestRecupCommandes = null;
		PreparedStatement requestRecupClient = null;
		ResultSet curseurCommande = null;
		ResultSet curseurClient = null;

		// Prepare la requête
		try {
			requestRecupCommandes = initialisationRequetePreparee(factory.getConnection(),
					RECUPERER_TOUTES_LES_COMMANDES, true);
			curseurCommande = requestRecupCommandes.executeQuery();

			while (curseurCommande.next()) {
				requestRecupClient = initialisationRequetePreparee(factory.getConnection(), FIND_ASSOCIATE_CLIENT, true,
						curseurCommande.getLong(2));
				curseurClient = requestRecupClient.executeQuery();
				if (curseurClient.next()) {
					CommandeB commandeToAdd = map(curseurCommande, curseurClient);
					allCommande.put(curseurCommande.getLong(1), commandeToAdd);
				}

			}
		} catch (

		SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (requestRecupCommandes != null)
				fermetureSilencieuse(requestRecupCommandes);
		}

		return allCommande;
	}

}
