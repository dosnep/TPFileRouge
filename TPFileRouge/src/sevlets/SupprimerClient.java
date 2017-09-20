package sevlets;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.ClientB;
import dao.ClientDaoImpl;
import dao.DaoFactory;

/**
 * Servlet implementation class SupprimerClient
 */
public class SupprimerClient extends HttpServlet {

	private static final String PATH_AFFICHAGE_LISTER_CLIENT = "/WEB-INF/listerClient.jsp";
	private static final String PARAM_NAME_ID_CLIENT = "idClient";
	private static final String ATTR_SESSION_LIST_CLIENT = "listClient";
	private static final String ATT_DAO_FACTORY = "daofactory";

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Long idClientToDelete = Long.valueOf(request.getParameter(PARAM_NAME_ID_CLIENT)).longValue();
		HttpSession session = request.getSession();
		((HashMap<Long, ClientB>) session.getAttribute(ATTR_SESSION_LIST_CLIENT)).remove(idClientToDelete);

		DaoFactory daoFactory = (DaoFactory) this.getServletContext().getAttribute(ATT_DAO_FACTORY);
		ClientDaoImpl daoClient = (ClientDaoImpl) daoFactory.getUtilisateurDao();
		daoClient.supprimer(idClientToDelete);
		this.getServletContext().getRequestDispatcher(PATH_AFFICHAGE_LISTER_CLIENT).forward(request, response);
	}

}
