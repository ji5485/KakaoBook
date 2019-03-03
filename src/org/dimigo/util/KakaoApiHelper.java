package org.dimigo.util;

import javax.net.ssl.HttpsURLConnection;
import java.io.*;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import com.google.gson.Gson;

public class KakaoApiHelper {

    public enum HttpMethodType { POST, GET, DELETE }

    private static final String API_SERVER_HOST  = "https://kapi.kakao.com";

    // User Management Uri
    private static final String USER_SIGNUP_PATH = "/v1/user/signup"; // 앱 연결
    private static final String USER_UNLINK_PATH = "/v1/user/unlink"; // 앱 연결 해제
    private static final String USER_LOGOUT_PATH = "/v1/user/logout"; // 로그아웃
    private static final String USER_ME_PATH = "/v1/user/me"; // 사용자 정보 요청
    private static final String USER_UPDATE_PROFILE_PATH = "/v1/user/update_profile"; // 사용자 정보 저장
    private static final String USER_IDS_PATH = "/v1/user/ids"; // 사용자 ID 리스트 요청

    // KakaoStory Uri
    private static final String STORY_ISSTORYUSER_PATH = "/v1/api/story/isstoryuser"; // 사용자 확인
    private static final String STORY_PROFILE_PATH = "/v1/api/story/profile"; // 프로필 요청
    private static final String STORY_LINKINFO_PATH = "/v1/api/story/linkinfo"; // 링크 정보 얻기
    private static final String STORY_POST_LINK_PATH = "/v1/api/story/post/link"; // 링크 포스팅

    // KakaoTalk Uri
    private static final String TALK_PROFILE_PATH = "/v1/api/talk/profile"; // 프로필 요청
    private static final String TALK_SEND_PATH = "/v2/api/talk/memo/default/send"; // 나한테 보내기
    
    private static final Gson GSON = new Gson();
    private static final String PROPERTIES_PARAM_NAME = "properties";

    private static final List<String> adminApiPaths = new ArrayList<String>();

    static {
        adminApiPaths.add(USER_IDS_PATH);
    }

    private String accessToken;
    private static final String ADMIN_KEY = "07e9e334e5c2bfaae2c1ada547e52622";

    public void setAccessToken(final String accessToken) {
        this.accessToken = accessToken;
    }

    ///////////////////////////////////////////////////////////////
    // User Management
    ///////////////////////////////////////////////////////////////

    public String signup() throws Exception {
        return request(HttpMethodType.POST, USER_SIGNUP_PATH);
    }

    public String signup(final Map<String, String> params) throws Exception {
        return request(HttpMethodType.POST, USER_SIGNUP_PATH, PROPERTIES_PARAM_NAME + "=" + mapToJsonStr(params));
    }

    public String unlink() throws Exception {
        return request(HttpMethodType.POST, USER_UNLINK_PATH);
    }

    public String logout() throws Exception {
        return request(HttpMethodType.POST, USER_LOGOUT_PATH);
    }

    public String me() throws Exception {
        return request(USER_ME_PATH);
    }

    public String updateProfile(final Map<String, String> params) throws Exception {
        return request(HttpMethodType.POST, USER_UPDATE_PROFILE_PATH, PROPERTIES_PARAM_NAME + "=" + mapToJsonStr(params));
    }

    public String getUserIds() throws Exception {
        return request(USER_IDS_PATH);
    }

    public String getUserIds(final Map<String, String> params) throws Exception {
        return request(HttpMethodType.GET, USER_IDS_PATH, mapToParams(params));
    }

    ///////////////////////////////////////////////////////////////
    // Kakao Story
    ///////////////////////////////////////////////////////////////

    public String isStoryUser() throws Exception {
        return request(STORY_ISSTORYUSER_PATH);
    }

    public String storyProfile() throws Exception {
        return request(STORY_PROFILE_PATH);
    }

    public String postLink(final Map<String, String> params) throws Exception {
        return request(HttpMethodType.POST, STORY_POST_LINK_PATH, mapToParams(params));
    }

    public String getLinkInfo(String url) throws Exception {
        return request(HttpMethodType.GET, STORY_LINKINFO_PATH, "?url=" + url);
    }

    ///////////////////////////////////////////////////////////////
    // Kakao Talk
    ///////////////////////////////////////////////////////////////

    public String talkProfile() throws Exception {
        return request(TALK_PROFILE_PATH);
    }
    
    public String talkSend(final Map<String, String> params) throws Exception {
        return request(HttpMethodType.POST, TALK_SEND_PATH, "template_object=" + params.get("template_object"));
    }
    
	///////////////////////////////////////////////////////////////
	// Request Function
	///////////////////////////////////////////////////////////////

    public String request(final String apiPath) throws Exception {
        return request(HttpMethodType.GET, apiPath, null);
    }

    public String request(final HttpMethodType httpMethod, final String apiPath) throws Exception {
        return request(httpMethod, apiPath, null);
    }

    public String request(HttpMethodType httpMethod, final String apiPath, final String params) throws Exception {

        String requestUrl = API_SERVER_HOST + apiPath;
        if (httpMethod == null) {
            httpMethod = HttpMethodType.GET;
        }
        if (params != null && params.length() > 0
                && (httpMethod == HttpMethodType.GET || httpMethod == HttpMethodType.DELETE)) {
            requestUrl += params;
        }

        HttpsURLConnection conn;
        OutputStreamWriter writer = null;
        BufferedReader reader = null;
        InputStreamReader isr = null;

        try {
            final URL url = new URL(requestUrl);
            conn = (HttpsURLConnection) url.openConnection();
            conn.setRequestMethod(httpMethod.toString());

            if (adminApiPaths.contains(apiPath)) {
                conn.setRequestProperty("Authorization", "KakaoAK " + ADMIN_KEY);
            } else {
                conn.setRequestProperty("Authorization", "Bearer " + this.accessToken);
            }

            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.setRequestProperty("charset", "utf-8");

            if (params != null && params.length() > 0 && httpMethod == HttpMethodType.POST) {
                conn.setDoOutput(true);
                writer = new OutputStreamWriter(conn.getOutputStream());
                writer.write(params);
                writer.flush();
            }

            final int responseCode = conn.getResponseCode();
            System.out.println(String.format("\nSending '%s' request to URL : %s", httpMethod, requestUrl));
            System.out.println("Response Code : " + responseCode);
            if (responseCode == 200)
                isr = new InputStreamReader(conn.getInputStream());
            else
                isr = new InputStreamReader(conn.getErrorStream());

            reader = new BufferedReader(isr);
            final StringBuffer buffer = new StringBuffer();
            String line;
            while ((line = reader.readLine()) != null) {
                buffer.append(line);
            }
            System.out.println(buffer.toString());
            return buffer.toString();

        } catch (IOException e) {
        	e.printStackTrace();
            throw new Exception("요청 처리 중 오류가 발생했습니다.");
        } finally {
            if (writer != null) try { writer.close(); } catch (Exception ignore) { }
            if (reader != null) try { reader.close(); } catch (Exception ignore) { }
            if (isr != null) try { isr.close(); } catch (Exception ignore) { }
        }
    }

    public String urlEncodeUTF8(String s) {
        try {
            return URLEncoder.encode(s, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new UnsupportedOperationException(e);
        }
    }

    public String mapToParams(Map<String, String> map) {
        StringBuilder paramBuilder = new StringBuilder();
        for (String key : map.keySet()) {
            paramBuilder.append(paramBuilder.length() > 0 ? "&" : "");
            paramBuilder.append(String.format("%s=%s", urlEncodeUTF8(key),
                    urlEncodeUTF8(map.get(key).toString())));
        }
        System.out.println(paramBuilder.toString());
        return paramBuilder.toString();
    }

    public String mapToJsonStr(Map<String, String> map) {
        return GSON.toJson(map);
    }
    
}