package org.dimigo.action;

import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.dimigo.service.ScrapService;
import org.dimigo.vo.ScrapVO;

public class ScrapListAction implements IAction {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			HttpSession session = request.getSession();
			String nickname = (String) session.getAttribute("nickname");
			
			if (nickname == null || nickname.trim().equals("")) throw new Exception("로그인이 필요한 서비스입니다.");
			
			ScrapService service = new ScrapService();
			List<ScrapVO> result = service.searchScrap(nickname);
			
			request.setAttribute("result", result);
			
			RequestDispatcher rd = request.getRequestDispatcher("jsp/scrapList.jsp");
			rd.forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("error", e.getMessage());
			
			RequestDispatcher rd = request.getRequestDispatcher("jsp/main.jsp");
			rd.forward(request, response);
		}
	}

}
