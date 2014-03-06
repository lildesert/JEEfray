package model;

import entities.Order;

import javax.ejb.Local;
import javax.ejb.Stateless;

@Stateless
@Local(OrderService.class)
public class OrderServiceEJB extends GenericCRUDServiceEJB<Order> implements OrderService {
	
}

