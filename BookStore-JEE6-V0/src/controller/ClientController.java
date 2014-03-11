package controller;

import java.io.Serializable;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import model.BookService;
import model.ClientService;
import model.OrderService;
import entities.Book;
import entities.Client;
import entities.Order;
import entities.OrderItem;
import form.LoginForm;

import java.util.Date;

@Named
@SessionScoped
public class ClientController implements Serializable {

	@Inject
	private ClientService clientService;

	@Inject
	private BookService bookService;

	@Inject
	private OrderService orderService;

	@Inject
	private LoginForm loginForm;

	@Inject
	private MessageBean messageBean;

	private Client currentClient;

	private Order order;

	private Order selectedOrder;

	public String doLogin() {
		currentClient = clientService.login(loginForm.getLogin(),
				loginForm.getPassword());
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
		if (order == null) {
			order = new Order();
		}
		return this.order;
	}

	public Order getSelectedOrder() {
		return this.selectedOrder;
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

	public void addItemToCart(Long id) {
		Book book = (Book) bookService.find(id);
		this.getOrder().addOne(book);
	}

	public void removeItemFromCart(OrderItem o) {
		getOrder().removeOne(o.getBook());
	}

	public String validateCmd() {
		if (currentClient == null) {
			messageBean.addMessage("notconnected");
			return null;
		} else if (order.getItems().size() == 0) {
			messageBean.addMessage("cartEmpty");
			return null;
		} else {
			order.setDate(new Date());
			order.setClient(currentClient);
			this.orderService.create(this.order);
			currentClient.getCommandes().add(order);
			this.order = null;
			return "cmdResume";
		}
	}

	public String orderDetails(Long orderID) {
		selectedOrder = orderService.find(orderID);
		return "cmdDetails";
	}
}