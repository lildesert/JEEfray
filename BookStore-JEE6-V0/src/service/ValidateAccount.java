package service;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;

import entities.Client;
import model.ClientService;

@ApplicationPath("webresources")
@Stateless
@Path("/validateAccount")
public class ValidateAccount {

	@Inject
	private ClientService clientService;

	@GET
	@Path("/Validate")
	public String Validate(@QueryParam("id") String id) {
		Client c = clientService.find(id);
		c.setActive(true);
		clientService.update(c);
		return "validateAccount";
	}

}
