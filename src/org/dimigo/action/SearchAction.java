package org.dimigo.action;

import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.dimigo.util.NaverSearchHelper;
import org.dimigo.vo.ContentVO;

public class SearchAction implements IAction {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		final int pageGroupSize = 10;
		
		try {
			HttpSession session = request.getSession();
			
			if (session.getAttribute("nickname") == null) throw new Exception("로그인이 필요한 서비스입니다.");
			
			String query = request.getParameter("query");
			String sort = request.getParameter("sort");
			String pageNo = request.getParameter("pageNo");
						
			if (query == null || query.trim().equals("")) 
				query = (String) session.getAttribute("favorite");
			if (sort == null || sort.equals("")) sort = "sim";
			if (pageNo == null) pageNo = "1";
			int currentPage = Integer.parseInt(pageNo);
			if (currentPage > 1000) currentPage = 1000;
						
			NaverSearchHelper apiHelper = new NaverSearchHelper();
			String result = apiHelper.naverSearch(query, currentPage, sort);
			List<ContentVO> list = apiHelper.parseItem(result);
			
			int startPage = ((currentPage - 1) / 10) * 10 + 1;
			int endPage = startPage + pageGroupSize - 1;
			
			request.setAttribute("startPage", startPage);
			request.setAttribute("endPage", endPage);
			request.setAttribute("result", list);
			
			RequestDispatcher rd = request.getRequestDispatcher("jsp/searchPage.jsp");
			rd.forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("error", e.getMessage());
			
			RequestDispatcher rd = request.getRequestDispatcher("jsp/main.jsp");
			rd.forward(request, response);
		}
	}

}
