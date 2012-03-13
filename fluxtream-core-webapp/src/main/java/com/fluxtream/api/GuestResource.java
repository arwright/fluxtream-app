package com.fluxtream.api;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.fluxtream.Configuration;
import com.fluxtream.connectors.Connector;
import com.fluxtream.domain.ApiKey;
import com.fluxtream.domain.Guest;
import com.fluxtream.mvc.controllers.ControllerHelper;
import com.fluxtream.mvc.models.StatusModel;
import com.fluxtream.mvc.models.guest.GuestModel;
import com.fluxtream.mvc.models.guest.OAuthTokensModel;
import com.fluxtream.services.GuestService;
import com.google.gson.Gson;

@Path("/guest")
@Component("guestApi")
@Scope("request")
public class GuestResource {

	@Autowired
	GuestService guestService;

	Gson gson = new Gson();

	@Autowired
	Configuration env;
	
	@GET
	@Path("/")
	@Produces({ MediaType.APPLICATION_JSON })
	public String getCurrentGuest() throws InstantiationException,
			IllegalAccessException, ClassNotFoundException {
		long guestId = ControllerHelper.getGuestId();

		Guest guest = guestService.getGuestById(guestId);
		GuestModel guestModel = new GuestModel(guest);

//		NewRelic.setTransactionName(null, "/api/log/all/date");
		return gson.toJson(guestModel);
	}
	
	@GET
	@Path("/{connector}/oauthTokens")
	@Produces({ MediaType.APPLICATION_JSON })
	public String getOAuthTokens(@PathParam("connector") String connectorName) throws InstantiationException,
			IllegalAccessException, ClassNotFoundException {
		long guestId = ControllerHelper.getGuestId();

		ApiKey apiKey = guestService.getApiKey(guestId, Connector.getConnector(connectorName));
		if (apiKey!=null) {
			OAuthTokensModel oauthTokensModel = new OAuthTokensModel();
			oauthTokensModel.accessToken = apiKey.getAttributeValue("accessToken", env);
			oauthTokensModel.tokenSecret = apiKey.getAttributeValue("tokenSecret", env);
			
			return gson.toJson(oauthTokensModel);
		} else {
			StatusModel result = new StatusModel(false, "Guest does not have that connector: " + connectorName);
			return gson.toJson(result);
		}
	}

	
}