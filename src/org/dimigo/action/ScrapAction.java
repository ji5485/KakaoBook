package org.dimigo.action;

import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.dimigo.service.ScrapService;
import org.dimigo.util.KakaoApiHelper;
import org.dimigo.vo.ScrapVO;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class ScrapAction implements IAction {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		response.setContentType("application/json;charset=utf-8");
		PrintWriter out = response.getWriter();
		String result = null;
		
		try {
			String title = request.getParameter("title").replaceAll("<(/)?([a-zA-Z]*)(\\s[a-zA-Z]*=[^>]*)?(\\s)*(/)?>", "");
			String blogger = request.getParameter("blogger");
			String link = request.getParameter("link");
			
			HttpSession session = request.getSession();
			String nickname = (String) session.getAttribute("nickname");
			String access_token = (String) session.getAttribute("access_token");
			
			if (nickname == null || nickname.trim().equals("")) throw new Exception("로그인이 필요한 서비스입니다.");
			
			Date date = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy년 MM월 dd일");
			String today = sdf.format(date);
			
			ScrapVO vo = new ScrapVO(nickname, title, blogger, today, link);
			
			ScrapService service = new ScrapService();
			service.scrap(vo);
			
			KakaoApiHelper apiHelper = new KakaoApiHelper();
			apiHelper.setAccessToken(access_token);

			Map<String, String> params = new HashMap<>();
			String template = getTemplate(vo);
			params.put("template_object", template);
			apiHelper.talkSend(params);
			
			result = "Success";
		} catch (Exception e) {
			result = "Failure";
			e.printStackTrace();
		} finally {
			Gson gson = new Gson();
			JsonObject object = new JsonObject();
			object.addProperty("result", result);
			String json = gson.toJson(object);
			out.write(json);
			
			out.close();
		}
	}
	
	private String getTemplate(ScrapVO vo) {
		String result = null;
		Gson gson = new Gson();
		
		try {
			JsonObject template_object = new JsonObject();
			JsonObject content = new JsonObject();
			JsonObject link = new JsonObject();
			JsonArray buttons = new JsonArray();
			JsonObject button = new JsonObject();
			
			template_object.addProperty("object_type", "feed");
			
			link.addProperty("web_url", vo.getLink());
			link.addProperty("mobile_web_url", vo.getLink());
			
			content.addProperty("title", vo.getTitle().replaceAll("<(/)?([a-zA-Z]*)(\\s[a-zA-Z]*=[^>]*)?(\\s)*(/)?>", ""));
			content.addProperty("description", vo.getDate() + " 스크랩 내역입니다.");
			content.addProperty("image_url", "http://k.kakaocdn.net/dn/rH8st/btqiKupNsef/8oxu7K0BSxSMnj9QKShisK/kakaolink40_original.png");
			content.addProperty("image_width", 800);
			content.addProperty("image_height", 800);
			content.add("link", gson.toJsonTree(link));
			template_object.add("content", gson.toJsonTree(content));
			
			button.addProperty("title", "웹으로 이동");
			button.add("link", gson.toJsonTree(link));
			buttons.add(button);
			template_object.add("buttons", gson.toJsonTree(buttons));
			
			result = gson.toJson(template_object);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}

}
