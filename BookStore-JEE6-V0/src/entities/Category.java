package entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@Entity()
@XmlRootElement

public class Category extends Persistent {
  private String title = "";
  private List<Book> books = new ArrayList();;

  public boolean equals(Object other) {
    if (other != null && other instanceof Category)
      return getTitle().equals(((Category) other).getTitle());
    return false;
  }

  public int hashCode() {
    return getTitle().hashCode();
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

@XmlTransient
  @OneToMany(mappedBy = "category")
  public List<Book> getBooks() {
    return this.books;
  }

  public void setBooks(List<Book> books) {
    this.books = books;
  }

  public String toString() {
    return this.title;
  }

  public void addBook(Book book) {
    books.add(book);
  }
}
