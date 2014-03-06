package model;

import entities.Book;
import entities.Category;

import javax.ejb.Local;
import javax.ejb.Stateless;

@Stateless
@Local(BookService.class)
public class BookServiceEJB extends GenericCRUDServiceEJB<Book> implements BookService {
	
}

