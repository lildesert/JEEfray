package controller;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import entities.Book;
import form.SearchForm;
import model.BookService;

@Named
@SessionScoped
public class BookController implements Serializable {
		
	@Inject
	private BookService bookService;
	
	private Book selectedBook;
	
	private List<Book> selectedBooks;
	
	@Inject
	private SearchForm searchForm;
	
	private String text;
	
	public String getText() {
		return text;
	}
	
	public void setText(String t) {
		text = t;
	}
	
	public Book getSelectedBook() {
		return selectedBook;
	}
	
	public List<Book> getSelectedBooks() {
		return selectedBooks;
	}

	public void setSelectedBook(Book selectedBook) {
		this.selectedBook = selectedBook;
	}

	public String selectBook(Long id) {
		selectedBook = (Book) bookService.find(id);
		
		if (selectedBook == null) {
			return null;
		}
		
		return "book";
	}
	
	public StreamedContent getImage() {

		StreamedContent content = null;
		
	    if (selectedBook.getPhoto() != null) {
	        InputStream is = new ByteArrayInputStream(selectedBook.getPhoto());
	        content = new DefaultStreamedContent(is);
	    }

	    return content;
	}
	
	public String searchBook() {
		String text = "%" + searchForm.getText() + "%";
		
		if (text.isEmpty()) {
			selectedBooks = bookService.findAll();
		} else {		
			Map<String, Object> param = new HashMap<>();
			param.put("like", text);
			selectedBooks = bookService.findWithNamedQuery("Book.findLikeOnTitle", param);
		}
		return "search";
	}
	
	public List<String> complete(String query) {
		
		query = query + "%";
		Map<String, Object> param = new HashMap<>();
		List<String> results = new ArrayList<String>();
		
		param.put("like", query);
		selectedBooks = bookService.findWithNamedQuery("Book.findLikeOnTitle", param);
		
		Iterator it = selectedBooks.iterator();
		while(it.hasNext()) {
			results.add(((Book) it.next()).getTitle());
		}
		
		return results;
	}

}
