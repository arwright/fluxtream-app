package com.fluxtream.connectors;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import com.fluxtream.domain.AbstractFacet;

public class ObjectType {
	
	private static Map<Connector,List<ObjectType>> connectorObjectTypes = new Hashtable<Connector,List<ObjectType>>();
	
	private static Map<Connector,Map<String,ObjectType>> connectorNamedObjectTypes = new Hashtable<Connector,Map<String,ObjectType>>();
	
	private static Map<String, ObjectType> customObjectTypes = new Hashtable<String, ObjectType>();
	
	/**
	 * "Custom" objectTypes are there to compute a value (hashCode())
	 * for special API calls. We use it for counting those calls so we
	 * don't confuse them with the usual API calls that retrieve data.
	 * @param name
	 */
	public static void registerCustomObjectType(String name) {
		name = name.toLowerCase();
		ObjectType customObjectType = new ObjectType();
		customObjectType.name = name;
		customObjectType.value = name.hashCode();
		customObjectTypes.put(name, customObjectType);
	}
	
	public static ObjectType getCustomObjectType(String name) {
		return customObjectTypes.get(name);
	}
	
	static void addObjectType(String name, Connector connector, ObjectType value) {
		if (!connectorObjectTypes.containsKey(connector))
			connectorObjectTypes.put(connector, new Vector<ObjectType>());
		connectorObjectTypes.get(connector).add(value);
		if (!connectorNamedObjectTypes.containsKey(connector))
			connectorNamedObjectTypes.put(connector, new Hashtable<String,ObjectType>());
		connectorNamedObjectTypes.get(connector).put(value.getName(), value);
	}
	
	public static ObjectType getObjectType(Connector connector, String name) {
		Map<String, ObjectType> connectorObjectTypes = connectorNamedObjectTypes.get(connector);
		ObjectType namedObjectType = connectorObjectTypes.get(name.toLowerCase());
		return namedObjectType;
	}
	
	String name;
	String prettyname;
	
	public static List<ObjectType> getObjectTypes(Connector connector, int objectTypes) {
		List<ObjectType> connectorTypes = connectorObjectTypes.get(connector);
		if (connectorTypes==null) return null;
		List<ObjectType> result = new ArrayList<ObjectType>();
		for (ObjectType objectType : connectorTypes) {
			if ((objectTypes & objectType.value())!=0)
				result.add(objectType);
		}
		return result;
	}
	
	public String toString() {
		return name;
	}
	
	public String getName() {
		return name;
	}
	
	int value;
	boolean isImageType;
	
	Class<? extends AbstractFacet> facetClass;

	ObjectType(){}
	
	public Class<? extends AbstractFacet> facetClass() {
		return facetClass;
	}
	
	public String name() {
		return name;
	}
	
	public int value() {
		return value;
	}
	
	public boolean isImageType() {
		return isImageType;
	}

	public String prettyname() {
		return prettyname;
	}
	
}