package com.fluxtream.services;

import java.util.List;

import com.fluxtream.connectors.Connector;
import com.fluxtream.domain.ConnectorInfo;

public interface SystemService {

	public List<ConnectorInfo> getConnectors();
	
	public Connector getApiFromGoogleScope(String scope);
	
}
