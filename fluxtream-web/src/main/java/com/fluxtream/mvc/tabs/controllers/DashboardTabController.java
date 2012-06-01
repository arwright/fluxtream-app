package com.fluxtream.mvc.tabs.controllers;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fluxtream.Configuration;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.fluxtream.mvc.controllers.ControllerHelper;
import com.fluxtream.mvc.models.HomeModel;
import com.fluxtream.services.ApiDataService;

@Controller
public class DashboardTabController {

	@Autowired
	ApiDataService apiDataService;

    @Autowired
    Configuration env;


	@RequestMapping("/tabs/dashboard")
	public ModelAndView getUserWidgets(HttpServletRequest request) {
		HomeModel homeModel = ControllerHelper.getHomeModel(request);
		long guestId = ControllerHelper.getGuestId();
		ModelAndView mav = new ModelAndView();
		mav.setViewName("tabs/stats");
        mav.addObject("release", env.get("release"));
		return mav;
	}

	@RequestMapping("/tabs/dashboard.json")
	public void getDayStatsWidgetJson(HttpServletResponse response,
			HttpServletRequest request) throws IOException {
		HomeModel homeModel = ControllerHelper.getHomeModel(request);
		long guestId = ControllerHelper.getGuestId();
		JSONObject o = new JSONObject();
		response.setContentType("application/json");
		response.getWriter().write(o.toString());
	}

}
