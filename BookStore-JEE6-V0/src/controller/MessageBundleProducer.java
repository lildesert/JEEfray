package controller;

import java.util.ResourceBundle;

import javax.enterprise.inject.Produces;
import javax.faces.context.FacesContext;

public class MessageBundleProducer {
	  @Produces @Messages 
	  public ResourceBundle getBundle() { 
	    return (ResourceBundle) FacesContext.getCurrentInstance().getApplication().getResourceBundle(FacesContext.getCurrentInstance(), "messages");
	  }
}