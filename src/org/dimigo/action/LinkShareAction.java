package org.dimigo.action;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.dimigo.util.KakaoApiHelper;

public class LinkShareAction implements IAction {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("utf-8");
		
		try {
			HttpSession session = request.getSession();
			String nickname = (String) session.getAttribute("nickname");
			String access_token = (String) session.getAttribute("access_token");
			
			if (nickname == null || nickname.trim().equals("")) throw new Exception("로그인이 필요한 서비스입니다.");
			
			String link = request.getParameter("post");
			String content = request.getParameter("content");
			
			if (content == null || content.trim().equals("")) content = "";
			if (link == null || link.trim().equals("")) throw new Exception("선택된 스크랩이 없습니다.");
		
			KakaoApiHelper apiHelper = new KakaoApiHelper();
			apiHelper.setAccessToken(access_token);
			
			String result = apiHelper.getLinkInfo(link);
			
			Map<String, String> params = new HashMap<>();
			params.put("link_info", result);
			params.put("content", content);
			
			apiHelper.postLink(params);
			
			request.setAttribute("result", "success");
			
			RequestDispatcher rd = request.getRequestDispatcher("jsp/main.jsp");
			rd.forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("error", e.getMessage());
			RequestDispatcher rd = request.getRequestDispatcher("jsp/kakaostory.jsp");
			rd.forward(request, response);
		}
	}

}
