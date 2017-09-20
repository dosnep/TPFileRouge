package sevlets;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.CommandeB;
import dao.CommandeDao;
import dao.DaoFactory;

/**
 * Servlet implementation class SupprimerCommande
 */
public class SupprimerCommande extends HttpServlet {

	private static final String PATH_AFFICHAGE_LISTER_COMMANDE = "/WEB-INF/listerCommande.jsp";
	private static final String NAME_PARAM_ID_COMMANDE = "idCommande";
	private static final String ATTR_SESSION_LIST_COMMANDE = "listCommande";
	private static final String ATT_DAO_FACTORY = "daofactory";

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		Long idCommandeToDelete = Long.valueOf(request.getParameter(NAME_PARAM_ID_COMMANDE)).longValue();
		DaoFactory daoFactory = (DaoFactory) this.getServletContext().getAttribute(ATT_DAO_FACTORY);
		CommandeDao commandeDao = daoFactory.getCommandeDao();
		commandeDao.supprimer(idCommandeToDelete);
		HttpSession session = request.getSession();
		((HashMap<Long, CommandeB>) session.getAttribute(ATTR_SESSION_LIST_COMMANDE)).remove(idCommandeToDelete);
		this.getServletContext().getRequestDispatcher(PATH_AFFICHAGE_LISTER_COMMANDE).forward(request, response);

	}

}
