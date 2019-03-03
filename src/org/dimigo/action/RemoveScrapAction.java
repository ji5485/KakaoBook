package org.dimigo.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.dimigo.service.ScrapService;

public class RemoveScrapAction implements IAction {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			String link = request.getParameter("link");
			
			ScrapService service = new ScrapService();
			service.removeScrap(link);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
