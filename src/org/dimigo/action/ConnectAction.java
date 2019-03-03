package org.dimigo.action;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.dimigo.service.UserService;
import org.dimigo.util.KakaoApiHelper;
import org.dimigo.vo.UserVO;

public class ConnectAction implements IAction {
	
	private void validate(UserVO vo) throws Exception {
		Pattern numberPattern = Pattern.compile("^[0-9]*$");
		Matcher matcher = numberPattern.matcher(String.valueOf(vo.getAge()));
		
		if (!matcher.matches()) throw new Exception("입력 정보 형식이 올바르지 않습니다.");
	}

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			request.setCharacterEncoding("utf-8");
			
			String nickname = request.getParameter("nickname");
			int age = Integer.parseInt(request.getParameter("age"));
			String gender = request.getParameter("gender");
			String favorite = request.getParameter("favorite");
			
			HttpSession session = request.getSession();
			String access_token = (String) session.getAttribute("access_token");
						
			UserVO user = new UserVO(nickname, age, gender, favorite);
			
			validate(user);
			
			UserService service = new UserService();
			service.signup(user);
			
			Map<String, String> userInfo = new HashMap<>();
			
			userInfo.put("nickname", nickname);
			userInfo.put("age", String.valueOf(age));
			userInfo.put("gender", gender);
			userInfo.put("favorite", favorite);
			
			KakaoApiHelper apiHelper = new KakaoApiHelper();
			apiHelper.setAccessToken(access_token);
			apiHelper.signup(userInfo);
			
			request.setAttribute("result", "앱 연결이 완료되었습니다. 다시 로그인해주세요.");
						
			RequestDispatcher rd = request.getRequestDispatcher("jsp/main.jsp");
			rd.forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("error", "앱 연결 시도 중 오류가 발생했습니다");
			
			RequestDispatcher rd = request.getRequestDispatcher("jsp/connectApp.jsp");
			rd.forward(request, response);
		}
	}

}
