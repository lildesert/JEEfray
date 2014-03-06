package controller;

import java.io.Serializable;
import java.util.ResourceBundle;

import javax.faces.application.FacesMessage;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@RequestScoped
public class MessageBean implements Serializable {

  @Inject @Messages
  private ResourceBundle bundle;
  
  public String getMessage(String key){
     return bundle.getString(key);
  }
  public void addMessage(String key){
    String summary = getMessage(key);
    FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, summary, summary);
    FacesContext.getCurrentInstance().addMessage(null, msg);
  }
}