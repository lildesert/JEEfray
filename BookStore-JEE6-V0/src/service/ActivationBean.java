package service;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.inject.Inject;

import model.ClientService;
import entities.Client;

@ManagedBean
@RequestScoped
public class ActivationBean {

	@ManagedProperty(value = "#{param.id}")
	private String id;
	private boolean valid;

	@Inject
	private ClientService clientService;

	@PostConstruct
	public void init() {
		Long idL = Long.valueOf(id).longValue();
		Client c = clientService.find(idL);
		c.setActive(true);
		clientService.update(c);
		valid = true;
	}

	public void setValid(boolean valid) {
		this.valid = valid;
	}

	public boolean isValid() {
		return valid;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}
}