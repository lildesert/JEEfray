package model;

import javax.ejb.Local;
import javax.ejb.Stateless;

import entities.Category;

@Stateless
@Local(CategoryService.class)
public class CategoryServiceEJB extends GenericCRUDServiceEJB<Category> implements CategoryService {
	
}
