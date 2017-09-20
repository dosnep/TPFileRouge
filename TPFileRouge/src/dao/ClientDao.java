package dao;

import java.util.HashMap;

import beans.ClientB;
import exception.DaoException;

public interface ClientDao {

	public void creer(ClientB utilisateur) throws DaoException;

	public void supprimer(Long id) throws DaoException;

	public HashMap<Long, ClientB> lister() throws DaoException;

}