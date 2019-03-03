package org.dimigo.action;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.dimigo.util.KakaoApiHelper;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class LoginAction implements IAction {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {		
		response.setContentType("application/json;charset=utf-8");
		PrintWriter out = response.getWriter();
		
		HttpSession session = request.getSession();
		session.setAttribute("access_token", null);
		
		try {
			String access_token = request.getParameter("access_token");
			
			KakaoApiHelper apiHelper = new KakaoApiHelper();		
			apiHelper.setAccessToken(access_token);
			
			session.setAttribute("access_token", access_token);
																	
			String msg = this.jsonParser(apiHelper.me(), "msg");
									
			if (msg != null && msg.equals("NotRegisteredUserException")) throw new Exception("NotRegisteredUserException");
			
			String userInfo = apiHelper.me();
						
			Map<String, String> result = getUserInfo(userInfo);
									
			for (String key : result.keySet()) {
				session.setAttribute(key, result.get(key).toString());
			}
			
			Gson gson = new Gson();
			JsonObject object = new JsonObject();
			object.addProperty("uri", "jsp/main.jsp");
			String json = gson.toJson(object);
			out.write(json);
			
			out.close();
		} catch (Exception e) {			
			if (e.getMessage().equals("NotRegisteredUserException")) {
				Gson gson = new Gson();
				JsonObject object = new JsonObject();
				object.addProperty("uri", "jsp/connectApp.jsp");
				String json = gson.toJson(object);
				out.write(json);
				
				out.close();
			}
		}
	}
	
	private String jsonParser(String json, String parseKey) {
		String code = null;
		
		try {
			JsonParser parser = new JsonParser();
			JsonElement element = parser.parse(json);
			code = element.getAsJsonObject().get(parseKey).getAsString();
		} catch (NullPointerException e) {
			System.out.println("\nJsonElement에 " + parseKey + " 값 존재하지 않음");
		}
		
		return code;
	}
	
	private Map<String, String> getUserInfo(String json) throws Exception {
		Map<String, String> user = new HashMap<>();
		
		String profile_image = "", nickname = "", gender = "", age = "", favorite = "";
		
		try {
			JsonParser parser = new JsonParser();
			JsonElement element = parser.parse(json);
			
			JsonElement userInfo = element.getAsJsonObject().get("properties");
						
			if (!userInfo.getAsJsonObject().get("profile_image").isJsonNull())
				profile_image = userInfo.getAsJsonObject().get("profile_image").getAsString();
			if (!userInfo.getAsJsonObject().get("nickname").isJsonNull())
				nickname = userInfo.getAsJsonObject().get("nickname").getAsString();
			if (!userInfo.getAsJsonObject().get("gender").isJsonNull())
				gender = userInfo.getAsJsonObject().get("gender").getAsString();
			if (!userInfo.getAsJsonObject().get("age").isJsonNull())
				age = userInfo.getAsJsonObject().get("age").getAsString();
			if (!userInfo.getAsJsonObject().get("favorite").isJsonNull())
				favorite = userInfo.getAsJsonObject().get("favorite").getAsString();
			
			user.put("profile_image", profile_image);
			user.put("nickname", nickname);
			user.put("gender", gender);
			user.put("age", age);
			user.put("favorite", favorite);
		} catch (Exception e) {
			throw new Exception("사용자 정보 요청 중 오류가 발생했습니다.");
		}
		
		return user;
	}
}
