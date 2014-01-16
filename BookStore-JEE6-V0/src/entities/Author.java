package entities;
import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import java.util.*;

@Entity()
@XmlRootElement

public class Author extends Persistent {
    
    private String firstName;
    private String lastName;
    private List<Book> books;
    
    public Author() {
    }

    public boolean equals(Object other){
      if(other != null && other instanceof Author)
          return getLastName().equals(((Author)other).getLastName()) && getFirstName().equals(((Author)other).getFirstName());
      return false;
    }
    public int hashCode(){
      return getFirstName().hashCode() + getLastName().hashCode();
    }

    public String getFirstName() {
        return this.firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public String getLastName() {
        return this.lastName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    @XmlTransient
    @ManyToMany(mappedBy="authors")
    public List<Book> getBooks() {
        return this.books;
    }
    
    public void setBooks(List<Book> books) {
        this.books = books;
    }
    
    public String toString(){
        return firstName + " " + lastName;
    }
    
}
