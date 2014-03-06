package controller;


import java.io.Serializable;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import entities.Book;
import entities.Category;
import entities.Order;
import entities.OrderItem;
import model.OrderService;
import model.BookService;

@Named
@SessionScoped
public class OrderController implements Serializable {
	
	@Inject
	private BookService bookService;
	
	@Inject
	private OrderService orderService;
	
	private Order order;
	
	public Order getOrder() {
		if(order == null) {
			order = new Order();
		}
		return order;
	}
	
}