package dao;

import static dao.DAOUtilitaire.fermetureSilencieuse;
import static dao.DAOUtilitaire.initialisationRequetePreparee;
import static dao.DAOUtilitaire.map;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import com.mysql.jdbc.PreparedStatement;

import beans.ClientB;
import exception.DaoException;

public class ClientDaoImpl implements ClientDao {

	private DaoFactory factory;

	private final static String INSERER_CLIENT = "insert into tp_filerouge.Client(nom, prenom, adresse, "
			+ "telephone, email, image) values(?, ?, ?, ?, ?, ?);";

	private static final String SUPPRIMER_CLIENT = "delete from tp_filerouge.Client where id = ?";

	private static final String RECUPERER_TOUS_LES_CLIENTS = "select * from tp_filerouge.Client;";

	public ClientDaoImpl(DaoFactory factory) {
		this.factory = factory;
	}

	public void creer(ClientB utilisateur) throws DaoException {
		// TODO Auto-generated method stub
		PreparedStatement request = null;
		ResultSet valeursAutoGenerees = null;

		// Préparer une requête
		try {
			request = initialisationRequetePreparee(factory.getConnection(), INSERER_CLIENT, true,
					utilisateur.getNomClient(), utilisateur.getPrenomClient(), utilisateur.getAdresseClient(),
					utilisateur.getTelephoneClient(), utilisateur.getEmailClient(), utilisateur.getPathProfilePic());
			int status = request.executeUpdate();
			// On teste si la requête a bien ete executee
			if (status == 0) {
				throw new DaoException("Erreur lors de l'execution de la requete");
			}

			else {
				// On recupere l'id du tuple et on la reporte à notre bean
				valeursAutoGenerees = request.getGeneratedKeys();
				if (valeursAutoGenerees.next()) {
					utilisateur.setId(valeursAutoGenerees.getLong(1));
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

	public void supprimer(Long id) throws DaoException {
		// TODO Auto-generated method stub
		PreparedStatement request = null;

		// Préparer une requête
		try {
			request = initialisationRequetePreparee(factory.getConnection(), SUPPRIMER_CLIENT, false, id);
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
	public HashMap<Long, ClientB> lister() throws DaoException {
		HashMap<Long, ClientB> allClient = new HashMap<>();
		PreparedStatement request = null;
		ResultSet curseur = null;

		// Prepare la requête
		try {
			request = initialisationRequetePreparee(factory.getConnection(), RECUPERER_TOUS_LES_CLIENTS, true);
			curseur = request.executeQuery();
			while (curseur.next()) {
				ClientB toAdd = map(curseur);
				allClient.put(curseur.getLong(1), toAdd);
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			fermetureSilencieuse(request);
		}

		return allClient;

	}

}
