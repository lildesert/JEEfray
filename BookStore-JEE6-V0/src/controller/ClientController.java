package controller;

import java.io.Serializable;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import model.ClientService;
import entities.Client;
import entities.Order;
import form.LoginForm;

@Named
@SessionScoped
public class ClientController implements Serializable {
	
	private Order order = new Order();
	
	@Inject
	private ClientService clientService;
	
	@Inject
	private LoginForm loginForm;
	
	@Inject
	private  MessageBean messageBean;
	
	private Client currentClient;

	public String doLogin() {
		currentClient = clientService.login(loginForm.getLogin(),loginForm.getPassword());
		System.out.println("pwd : " + loginForm.getPassword());
		
		if (currentClient == null) {
			messageBean.addMessage("clientNotFound");
			return null;
		}
		return "welcome";
	}

	public List getClientList() {
		return clientService.findWithNamedQuery("Client.findAll");
	}

	public void setOrder(Order o) {
		this.order = o;
	}

	public Order getOrder() {
		return this.order;
	}

	public Client getCurrentClient() {
		return currentClient;
	}

	public void setCurrentClient(Client client) {
		this.currentClient = client;
	}

	public boolean isLoggedIn() {
		return currentClient != null;
	}

	public String doLogout() {
		currentClient = null;
		return "login"; // Une règle de navigation
	}
}