package org.dimigo.action;

import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.dimigo.service.ScrapService;
import org.dimigo.util.KakaoApiHelper;
import org.dimigo.vo.ScrapVO;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

public class IsStoryUserAction implements IAction {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			HttpSession session = request.getSession();
			String nickname = (String) session.getAttribute("nickname");
			String access_token = (String) session.getAttribute("access_token");
			
			if (nickname == null || nickname.trim().equals("")) throw new Exception("로그인이 필요한 서비스입니다.");
			
			KakaoApiHelper apiHelper = new KakaoApiHelper();
			apiHelper.setAccessToken(access_token);
			String result = apiHelper.isStoryUser();
			
			if (!isStoryUserParser(result)) throw new Exception("카카오스토리 등록이 필요한 서비스입니다.");
			
			ScrapService service = new ScrapService();
			List<ScrapVO> list = service.searchScrap(nickname);
			
			request.setAttribute("list", list);
			
			RequestDispatcher rd = request.getRequestDispatcher("jsp/kakaostory.jsp");
			rd.forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("error", e.getMessage());
			
			RequestDispatcher rd = request.getRequestDispatcher("jsp/main.jsp");
			rd.forward(request, response);
		}
	}
	
	private Boolean isStoryUserParser(String json) {
		Boolean result = false;
		
		try {
			JsonParser parser = new JsonParser();
			JsonElement element = parser.parse(json);
			
			result = element.getAsJsonObject().get("isStoryUser").getAsBoolean();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}

}
