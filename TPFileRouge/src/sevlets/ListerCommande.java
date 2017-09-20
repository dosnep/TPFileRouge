package sevlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class ListerCommande
 */
public class ListerCommande extends HttpServlet {
	private static final String PATH_AFFICHAGE_LISTER_COMMANDE = "/WEB-INF/listerCommande.jsp";

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		this.getServletContext().getRequestDispatcher(PATH_AFFICHAGE_LISTER_COMMANDE).forward(request, response);
	}

}
