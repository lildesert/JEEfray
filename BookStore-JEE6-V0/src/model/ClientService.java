package model;

import entities.Client;

public interface ClientService extends GenericCRUDService<Client> {
	public Client login(String login, String password);
}