package model;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.persistence.NoResultException;

import entities.Client;

@Stateless
@Local(ClientService.class)
public class ClientServiceEJB extends GenericCRUDServiceEJB<Client> implements ClientService {
	
	public Client login(String login, String password) {
		Client client = null;
		try {
			client = (Client) em
					.createQuery(
							"select c from Client c where c.login=:login and c.password=:password")
					.setParameter("login", login)
					.setParameter("password", password).getSingleResult();
		} catch (NoResultException e) {
		}
		return client;
	}
}