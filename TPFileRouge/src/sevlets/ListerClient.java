package sevlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class ListerClient
 */
public class ListerClient extends HttpServlet {
	private static final String PATH_AFFICHAGE_LISTER_CLIENT = "/WEB-INF/listerClient.jsp";

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.getServletContext().getRequestDispatcher(PATH_AFFICHAGE_LISTER_CLIENT).forward(request, response);
	}

}
