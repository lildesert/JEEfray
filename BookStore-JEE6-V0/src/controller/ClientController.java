package controller;

import java.util.List;

import javax.faces.bean.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import model.ClientService;
import entities.Client;
import entities.Order;
import form.LoginForm;

@Named
@SessionScoped
public class ClientController {

	@Inject
	private ClientService clientService;
	@Inject
	private LoginForm loginForm;

	private Client currentClient;
	private Order order;

	public String doLogin() {
		currentClient = clientService.login(loginForm.getLogin(),
				loginForm.getPassword());
		if (currentClient == null) {
			return null;
		}
		return "success";
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