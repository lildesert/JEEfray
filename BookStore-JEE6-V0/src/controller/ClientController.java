package controller;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;

import service.CookieService;
import service.SendMail;
import model.BookService;
import model.ClientService;
import model.OrderService;
import entities.Book;
import entities.Client;
import entities.Order;
import entities.OrderItem;
import form.LoginForm;
import form.SubscriptionForm;

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
	private SubscriptionForm subForm;

	@Inject
	private MessageBean messageBean;

	private Client currentClient;

	private UploadedFile file;

	private Order order;

	private Order selectedOrder;

	public String doLogin() {
		currentClient = clientService.login(loginForm.getLogin(),
				loginForm.getPassword());
		
		if (currentClient == null) {
			messageBean.addMessage("clientNotFound");
			return null;
		}
		else if(currentClient.getActive() == false)
		{
			messageBean.addMessage("accountNotActivated");
			currentClient = null;
			return null;
		}
		if (loginForm.isRemember()) {
	        CookieService.addCookie("userLogin", currentClient.getLogin(), 2592000);
	        CookieService.addCookie("userPwd", currentClient.getPassword(), 2592000);
	    } else {
	        CookieService.removeCookie("userLogin");
	        CookieService.removeCookie("userPwd");
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

	public UploadedFile getFile() {
		return file;
	}

	public void setFile(UploadedFile file) {
		this.file = file;
	}

	public void setCurrentClient(Client client) {
		this.currentClient = client;
	}

	public boolean isLoggedIn() {
		return currentClient != null;
	}

	public String doLogout() {
		currentClient = null;
		return "login"; // Une r√®gle de navigation
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

			// Envoi du mail de confirmation
			String newLine = System.getProperty("line.separator");

			String text = "Information sur la commande n£"
					+ order.getId().toString() + " : " + newLine + newLine;
			for (OrderItem o : order.getItems()) {
				text += o.getBook().getTitle() + " " + o.getTotal() + newLine;
			}
			text += "Montant total de la commande : "
					+ order.getTotal().toString() + newLine;
			text += "Merci d'avoir pass£ commande via notre boutique !";

			SendMail.send(currentClient.getMail(), "admin@JEEFray.fr",
					"Confirmation de la commande", text);

			this.order = null;
			return "cmdResume";
		}
	}

	public String orderDetails(Long orderID) {
		selectedOrder = orderService.find(orderID);
		System.out.println("here");
		return "cmdDetails";
	}
	
	public void getBill(Long orderID) {
		selectedOrder = orderService.find(orderID);
		PDFCreator pc = new PDFCreator(selectedOrder);
		pc.createPDF();
	}
	
	public String subscribe() {
		Client c = new Client();
		c.setLogin(subForm.getLogin());
		c.setPassword(subForm.getPassword());
		c.setMail(subForm.getMail());
		c.setActive(false);
		clientService.create(c);

		// Envoi du mail d'activation
		String newLine = System.getProperty("line.separator");
		String text = "Cliquez sur le lien pour activer votre compte : "
				+ newLine;
		text += "<a href='http://localhost:8080/BookStore-JEE6-V0/validateAccount.xhtml?id="
				+ c.getId() + "'>Valider votre compte</a>";

		SendMail.send(c.getMail(), "admin@JEEFray.fr",
				"Validation de l'inscription £ JEEFray", text);

		return "subscription";
	}

	public void handleFileUpload(FileUploadEvent event) {
		try {
			ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
			String s = context.getRealPath("resources/imagesAccount/"); 
			File targetFolder = new File(s);
			
			for (File f : targetFolder.listFiles()) {
				if (f.getName().contains(currentClient.getId().toString() + "£")) {
					f.delete();
				}
			}
			
			InputStream inputStream = event.getFile().getInputstream();
			OutputStream out = new FileOutputStream(new File(targetFolder,
					currentClient.getId().toString() + "£"
							+ event.getFile().getFileName()));
			int read = 0;
			byte[] bytes = new byte[1024];
			
			while ((read = inputStream.read(bytes)) != -1) {
				out.write(bytes, 0, read);
			}
			inputStream.close();
			out.flush();
			out.close();
			context.redirect(context.getRequestContextPath()+"/account.xhtml");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public String getImage() {
		String imageClient = "resources/imagesAccount/";
		
		ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
		String s = context.getRealPath("resources/imagesAccount/"); 
		File fDir = new File(s);
		if(fDir.listFiles() != null)
		{
			for (File f : fDir.listFiles()) {
				if (f.getName().contains(currentClient.getId().toString() + "£")) {
					imageClient += f.getName();
				}
			}
		}
		
		return imageClient;
	}

}