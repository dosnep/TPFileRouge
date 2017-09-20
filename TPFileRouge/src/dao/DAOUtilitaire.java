package dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.Statement;

import beans.ClientB;
import beans.CommandeB;

public class DAOUtilitaire {

	/*
	 * Initialise la requête préparée basée sur la connexion passée en argument,
	 * avec la requête SQL et les objets donnés.
	 */
	public static PreparedStatement initialisationRequetePreparee(Connection connexion, String sql,
			boolean returnGeneratedKeys, Object... objets) throws SQLException {
		PreparedStatement preparedStatement = (PreparedStatement) connexion.prepareStatement(sql,
				returnGeneratedKeys ? Statement.RETURN_GENERATED_KEYS : Statement.NO_GENERATED_KEYS);
		for (int i = 0; i < objets.length; i++) {
			preparedStatement.setObject(i + 1, objets[i]);
		}
		return preparedStatement;
	}

	/*
	 * Simple méthode utilitaire permettant de faire la correspondance (le
	 * mapping) entre une ligne issue de la table des utilisateurs (un
	 * ResultSet) et un bean Utilisateur.
	 */
	public static ClientB map(ResultSet resultSet) throws SQLException {
		ClientB utilisateur = new ClientB();
		utilisateur.setId(resultSet.getLong("id"));
		utilisateur.setEmailClient(resultSet.getString("email"));
		utilisateur.setAdresseClient(resultSet.getString("adresse"));
		utilisateur.setNomClient(resultSet.getString("nom"));
		return utilisateur;
	}

	public static CommandeB map(ResultSet resultSetCommande, ResultSet resultSetClient) throws SQLException {
		ClientB utilisateur = new ClientB();
		utilisateur.setId(resultSetClient.getLong("id"));
		utilisateur.setEmailClient(resultSetClient.getString("email"));
		utilisateur.setAdresseClient(resultSetClient.getString("adresse"));
		utilisateur.setNomClient(resultSetClient.getString("nom"));

		CommandeB commande = new CommandeB();
		commande.setClient(utilisateur);
		commande.setId(resultSetCommande.getLong("id"));
		commande.setDateCommande(resultSetCommande.getString("date"));
		commande.setModeLivraisonCommande(resultSetCommande.getString("mode_livraison"));
		commande.setModePaiementCommande(resultSetCommande.getString("mode_paiement"));
		commande.setMontantCommande(resultSetCommande.getString("montant"));
		commande.setStatutLivraisonCommande(resultSetCommande.getString("statut_livraison"));
		commande.setStatutPaiementCommande(resultSetCommande.getString("statut_paiement"));

		return commande;
	}

	public static void fermetureSilencieuse(ResultSet resultSet) {
		if (resultSet != null) {
			try {
				resultSet.close();
			} catch (SQLException e) {
				System.out.println("Échec de la fermeture du ResultSet : " + e.getMessage());
			}
		}
	}

	/* Fermeture silencieuse du statement */
	public static void fermetureSilencieuse(Statement statement) {
		if (statement != null) {
			try {
				statement.close();
			} catch (SQLException e) {
				System.out.println("Échec de la fermeture du Statement : " + e.getMessage());
			}
		}
	}

	/* Fermeture silencieuse de la connexion */
	public static void fermetureSilencieuse(Connection connexion) {
		if (connexion != null) {
			try {
				connexion.close();
			} catch (SQLException e) {
				System.out.println("Échec de la fermeture de la connexion : " + e.getMessage());
			}
		}
	}

	/* Fermetures silencieuses du statement et de la connexion */
	public static void fermeturesSilencieuses(Statement statement, Connection connexion) {
		fermetureSilencieuse(statement);
		fermetureSilencieuse(connexion);
	}

	/* Fermetures silencieuses du resultset, du statement et de la connexion */
	public static void fermeturesSilencieuses(ResultSet resultSet, Statement statement, Connection connexion) {
		fermetureSilencieuse(resultSet);
		fermetureSilencieuse(statement);
		fermetureSilencieuse(connexion);
	}

}
