package dao;

import java.util.HashMap;

import beans.CommandeB;
import exception.DaoException;

public interface CommandeDao {

	public void creer(CommandeB commande) throws DaoException;

	public void supprimer(Long id) throws DaoException;

	public HashMap<Long, CommandeB> lister() throws DaoException;
}
