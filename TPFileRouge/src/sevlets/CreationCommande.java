package sevlets;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.ClientB;
import beans.CommandeB;
import dao.ClientDao;
import dao.CommandeDao;
import dao.DaoFactory;
import forms.HandleFormClient;

public class CreationCommande extends HttpServlet {

	private static final String PARAM_NAME_USER_LIST = "usersList";
	private static final String PARAM_NAME_NEW_USER = "newUser";
	private static final String VALUE_OUI = "oui";
	private static final String VALUE_NON = "non";
	private static final String ATTR_SESSION_LIST_COMMANDE = "listCommande";
	private static final String ATTR_SESSION_LIST_CLIENT = "listClient";
	private static final String ATTR_COMMANDE = "commande";
	private static final String ATTR_FORM_COMMANDE = "form";
	private static final String PATH_AFFICHAGE_CLIENT_POST_COMMANDE = "/WEB-INF/AffichageCommande.jsp";
	private static final String PATH_AFFICHAGE_CLIENT_GET_COMMANDE = "/WEB-INF/commande.jsp";
	private static final String SERVLET_NAME_CHEMIN = "chemin";
	private static final String ATT_DAO_FACTORY = "daofactory";
	private static final String PARAM_NAME_ID_CLIENT = "idClient";

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		this.getServletContext().getRequestDispatcher(PATH_AFFICHAGE_CLIENT_GET_COMMANDE).forward(request, response);

	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		CommandeB commande = null;
		HandleFormClient form = new HandleFormClient();
		HttpSession session = request.getSession();

		DaoFactory daoFactory = (DaoFactory) this.getServletContext().getAttribute(ATT_DAO_FACTORY);
		CommandeDao commandeDao = daoFactory.getCommandeDao();

		if (VALUE_OUI.equals(request.getParameter(PARAM_NAME_NEW_USER))) {
			ClientDao clientDao = daoFactory.getUtilisateurDao();
			commande = form.enregistrerCommandeWithInitUser(request, commandeDao, clientDao,
					this.getServletConfig().getInitParameter(SERVLET_NAME_CHEMIN));

			if (session.getAttribute(ATTR_SESSION_LIST_CLIENT) == null) {
				session.setAttribute(ATTR_SESSION_LIST_CLIENT, new HashMap<Long, ClientB>());
			}
			// Add client
			((HashMap<Long, ClientB>) session.getAttribute(ATTR_SESSION_LIST_CLIENT)).put(commande.getClient().getId(),
					commande.getClient());
		}

		else if (VALUE_NON.equals(request.getParameter(PARAM_NAME_NEW_USER))) {
			Long idClientToDelete = Long.valueOf(request.getParameter(PARAM_NAME_ID_CLIENT)).longValue();
			commande = form.enregistrerCommandeFromExistingUser(request, commandeDao, idClientToDelete);

		}

		// if no errors in form
		if (form.getErrors().isEmpty()) {
			// if users list doesn't exist
			if (session.getAttribute(ATTR_SESSION_LIST_COMMANDE) == null) {
				session.setAttribute(ATTR_SESSION_LIST_COMMANDE, new HashMap<String, CommandeB>());
			}
			// Add commande
			((HashMap<Long, CommandeB>) session.getAttribute(ATTR_SESSION_LIST_COMMANDE)).put(commande.getId(),
					commande);
		}

		request.setAttribute(ATTR_COMMANDE, commande);
		request.setAttribute(ATTR_FORM_COMMANDE, form);

		this.getServletContext().getRequestDispatcher(PATH_AFFICHAGE_CLIENT_POST_COMMANDE).forward(request, response);

	}

}
