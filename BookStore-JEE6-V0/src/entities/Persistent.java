package entities;

import javax.persistence.MappedSuperclass;
import java.io.Serializable;

import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;

@MappedSuperclass()
public class Persistent {
	private Long id;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	/*
	 * Il est déconseillé d'implémenter equals sur l'id qui est null tant que l'objet n'a pas été écrit dans la BD
	 * equals sera implémenté dans les classes dérivées sur des champs applicatifs
	 */
}
