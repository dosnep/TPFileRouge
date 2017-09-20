package filtre;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.ClientB;
import beans.CommandeB;
import dao.ClientDao;
import dao.CommandeDao;
import dao.DaoFactory;

/**
 * Servlet Filter implementation class RecupererInfosFromDB
 */
public class RecupererInfosFromDB implements Filter {

	private static final String ATT_DAO_FACTORY = "daofactory";
	private static final String ATTR_SESSION_LIST_CLIENT = "listClient";
	private static final String ATTR_SESSION_LIST_COMMANDE = "listCommande";

	private ServletContext context;

	/**
	 * Default constructor.
	 */
	public RecupererInfosFromDB() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
			throws IOException, ServletException {
		// TODO Auto-generated method stub
		DaoFactory daoFactory = (DaoFactory) context.getAttribute(ATT_DAO_FACTORY);

		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;

		HttpSession session = request.getSession();

		if (session.getAttribute(ATTR_SESSION_LIST_CLIENT) == null) {
			System.out.println("hello");
			ClientDao clientDao = daoFactory.getUtilisateurDao();
			HashMap<Long, ClientB> listClient = clientDao.lister();
			session.setAttribute(ATTR_SESSION_LIST_CLIENT, listClient);
		}

		if (session.getAttribute(ATTR_SESSION_LIST_COMMANDE) == null) {

			CommandeDao commandeDao = daoFactory.getCommandeDao();
			HashMap<Long, CommandeB> listCommande = commandeDao.lister();
			session.setAttribute(ATTR_SESSION_LIST_COMMANDE, listCommande);
		}

		// pass the request along the filter chain
		chain.doFilter(request, response);
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
		this.context = fConfig.getServletContext();
	}

}
