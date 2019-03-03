package org.dimigo.action;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.dimigo.util.KakaoApiHelper;

public class LogoutAction implements IAction {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			HttpSession session = request.getSession();
			String access_token = (String) session.getAttribute("access_token");
			
			System.out.println(access_token);
			
			KakaoApiHelper apiHelper = new KakaoApiHelper();
			apiHelper.setAccessToken(access_token);
			
			apiHelper.logout();
			session.invalidate();
			
			RequestDispatcher rd = request.getRequestDispatcher("jsp/main.jsp");
			rd.forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();

			RequestDispatcher rd = request.getRequestDispatcher("jsp/main.jsp");
			rd.forward(request, response);
		}
	}

}
