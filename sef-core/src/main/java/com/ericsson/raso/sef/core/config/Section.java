package com.ericsson.raso.sef.core.config;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement(name="section")
public class Section {
	
	private String id;  
	private String description;  
	private ArrayList<Property> property = new ArrayList<Property>() ; 
	private Router router;
	
	public Section() {}  
	
	public Section(String id, String description, ArrayList<Property> property) {  
	    super();  
	    this.id = id;  
	    this.description = description;  
	    this.property = property;    
	}  
	
	@XmlAttribute
	public String getId() {  
	    return id;  
	}  
	
	public void setId(String id) {  
	    this.id = id;  
	}
	
	@XmlAttribute
	public String getDescription() {  
	    return description;  
	}  
	
	public void setDescription(String description) {  
	    this.description = description;  
	}  
	
	
	@XmlElement(name="property")
	public ArrayList<Property> getProperty() {  
	    return property;  
	}  
	
	public void setProperty(ArrayList<Property> property) {  
	    this.property = property;  
	}

	@XmlElement(name="router")
	public Router getRouter() {
		return router;
	}
	
	public void setRouter(Router router) {
		this.router = router;
	}

	@Override
	public String toString() {
		return "Section [id=" + id + ", description=" + description + ", property=" + property + ", router=" + router + "]";
	}  

	
}
