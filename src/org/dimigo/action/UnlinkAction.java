package org.dimigo.action;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.dimigo.service.ScrapService;
import org.dimigo.service.UserService;
import org.dimigo.util.KakaoApiHelper;

public class UnlinkAction implements IAction {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			HttpSession session = request.getSession();
			String nickname = (String) session.getAttribute("nickname");
			String access_token = (String) session.getAttribute("access_token");
			
			UserService uService = new UserService();
			ScrapService sService = new ScrapService();
			KakaoApiHelper apiHelper = new KakaoApiHelper();
			
			session.invalidate();
			
			apiHelper.setAccessToken(access_token);
			apiHelper.unlink();
			uService.removeUser(nickname);
			sService.removeScrapAll(nickname);
			
			request.setAttribute("result", "KakaoBook 탈퇴 처리가 완료되었습니다.");
			
			RequestDispatcher rd = request.getRequestDispatcher("jsp/main.jsp");
			rd.forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("error", "알 수 없는 오류가 발생했습니다.");
			RequestDispatcher rd = request.getRequestDispatcher("jsp/userInfo.jsp");
			rd.forward(request, response);
		}
	}

}
