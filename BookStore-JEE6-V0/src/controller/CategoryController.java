package controller;

import java.io.Serializable;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import entities.Book;
import entities.Category;
import model.CategoryService;

@Named
@SessionScoped
public class CategoryController implements Serializable {
		
	@Inject
	private CategoryService categoryService;
	
	private Category selectedCategory;
	
	public Category getSelectedCategory() {
		return selectedCategory;
	}

	public void setSelectedCategory(Category selectedCategory) {
		this.selectedCategory = selectedCategory;
	}

	public List<Category> getCategoryList() {
		return categoryService.findAll();
	}
	
	public String selectCategory(Long id) {
		selectedCategory = (Category) categoryService.find(id);
		
		if (selectedCategory == null) {
			return null;
		}
		
		return "category";
	}
	
	public List<Book> getBookList()
	{
		return selectedCategory.getBooks();
	}
	
}
