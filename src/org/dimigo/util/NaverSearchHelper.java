package org.dimigo.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.List;

import org.dimigo.vo.ContentVO;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

public class NaverSearchHelper {

	public String naverSearch(String query, int page, String sort) throws Exception {
		String clientId = "LF8hRh8cTC1y8xgq4f5C";//애플리케이션 클라이언트 아이디값";
        String clientSecret = "acJyHtJWjW";//애플리케이션 클라이언트 시크릿값";
        
        String result = null;
        
        try {
            String text = URLEncoder.encode(query, "UTF-8");
            String apiURL = "https://openapi.naver.com/v1/search/blog?query=" + text + "&display=21&start=" + page + "&sort=" + sort; // json 결과
            //String apiURL = "https://openapi.naver.com/v1/search/blog.xml?query="+ text; // xml 결과
            URL url = new URL(apiURL);
            HttpURLConnection con = (HttpURLConnection)url.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("X-Naver-Client-Id", clientId);
            con.setRequestProperty("X-Naver-Client-Secret", clientSecret);
            int responseCode = con.getResponseCode();
            BufferedReader br;
            if(responseCode==200) { // 정상 호출
                br = new BufferedReader(new InputStreamReader(con.getInputStream()));
            } else {  // 에러 발생
                br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
            }
            String inputLine;
            StringBuffer response = new StringBuffer();
            while ((inputLine = br.readLine()) != null) {
                response.append(inputLine);
            }
            br.close();
            System.out.println("검색어  : " + query + ", 페이지 : " + page + " 쿼리 처리 완료");
            System.out.println(response.toString());
            
            result = response.toString();
        } catch (Exception e) {
            throw new Exception("검색도중 오류가 발생했습니다.");
        }
        
        return result;
	}
	
	public List<ContentVO> parseItem(String json) {
		Gson gson = new Gson();
		List<ContentVO> result = null;
		
		try {
			JsonParser parser = new JsonParser();
			JsonElement element = parser.parse(json);
			
			JsonArray array = element.getAsJsonObject().get("items").getAsJsonArray();
			ContentVO[] list = gson.fromJson(array, ContentVO[].class);
			
			result = Arrays.asList(list);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("검색 결과 JSON 파싱 오류");
		}
		
		return result;
	}

}
