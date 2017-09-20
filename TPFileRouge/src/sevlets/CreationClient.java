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
import forms.HandleFormClient;

public class CreationClient extends HttpServlet {

	private static final String ATTR_SESSION_LIST_CLIENT = "listClient";
	private static final String ATTR_CLIENT = "client";
	private static final String ATTR_FORM_CLIENT = "formClient";
	private static final String PATH_AFFICHAGE_CLIENT_GET = "/WEB-INF/client.jsp";
	private static final String PATH_AFFICHAGE_CLIENT_POST = "/WEB-INF/AffichageClient.jsp";
	private static final String SERVLET_NAME_CHEMIN = "chemin";
	private static final String PARAM_NAME_FILE = "fichier";
	private static final String ATT_DAO_FACTORY = "daofactory";

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.getServletContext().getRequestDispatcher(PATH_AFFICHAGE_CLIENT_GET).forward(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		ClientB client;
		HandleFormClient formClient = new HandleFormClient();
		DaoFactory daoFactory = (DaoFactory) this.getServletContext().getAttribute(ATT_DAO_FACTORY);
		ClientDaoImpl newClient = (ClientDaoImpl) daoFactory.getUtilisateurDao();
		client = formClient.inscrireClient(request, newClient,
				this.getServletConfig().getInitParameter(SERVLET_NAME_CHEMIN));

		// if no errors in form
		if (formClient.getErrors().isEmpty()) {
			HttpSession session = request.getSession();
			// if users list doesn't exist
			if (session.getAttribute(ATTR_SESSION_LIST_CLIENT) == null) {
				session.setAttribute(ATTR_SESSION_LIST_CLIENT, new HashMap<Long, ClientB>());
			}
			// Add user
			((HashMap<Long, ClientB>) session.getAttribute(ATTR_SESSION_LIST_CLIENT)).put(client.getId(), client);
		}

		request.setAttribute(ATTR_CLIENT, client);
		request.setAttribute(ATTR_FORM_CLIENT, formClient);
		this.getServletContext().getRequestDispatcher(PATH_AFFICHAGE_CLIENT_POST).forward(request, response);

	}

}
