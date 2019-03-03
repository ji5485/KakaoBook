package org.dimigo.servlet;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.dimigo.action.ConnectAction;
import org.dimigo.action.IAction;
import org.dimigo.action.IsStoryUserAction;
import org.dimigo.action.LinkShareAction;
import org.dimigo.action.LoginAction;
import org.dimigo.action.LogoutAction;
import org.dimigo.action.RemoveScrapAction;
import org.dimigo.action.ScrapAction;
import org.dimigo.action.ScrapListAction;
import org.dimigo.action.SearchAction;
import org.dimigo.action.UnlinkAction;

/**
 * Servlet implementation class ActionServlet
 */
@WebServlet("*.do")
public class ActionServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Map<String, IAction> actions = new HashMap<>();
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ActionServlet() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    @Override
    public void init() throws ServletException {
    	actions.put("login", new LoginAction());
    	actions.put("connect", new ConnectAction());
    	actions.put("logout", new LogoutAction());
    	actions.put("search", new SearchAction());
    	actions.put("scrap", new ScrapAction());
    	actions.put("scrapList", new ScrapListAction());
    	actions.put("removeScrap", new RemoveScrapAction());
    	actions.put("unlink", new UnlinkAction());
    	actions.put("isstoryuser", new IsStoryUserAction());
    	actions.put("linkShare", new LinkShareAction());
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		process(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		process(request, response);
	}

	private void process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			// 1. uri (/login.do)
			String uri = request.getRequestURI();

			String actionName = uri.substring(uri.lastIndexOf("/") + 1); // login.do
			actionName = actionName.substring(0, actionName.indexOf("."));
			
			// action 객체 가져오기
			IAction action = actions.get(actionName);
			
			if (action == null) {
				throw new Exception(actionName + "에 해당하는 요청이 존재하지 않습니다.");
			}
			
			// XXAction의 execute()
			
			action.execute(request, response);
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("error", e.getMessage());
			RequestDispatcher rd = request.getRequestDispatcher("jsp/error.jsp");
			rd.forward(request, response);
		}
	}

}
