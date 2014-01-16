/*
 * DumpBookStore.java
 */

package test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.eclipse.persistence.config.PersistenceUnitProperties;

import entities.Author;
import entities.Book;
import entities.Category;

public class DumpBookStore {
  public static void main(String[] args) throws Exception {
    Map<String, String> properties = new HashMap<String, String>();
    /*
     * On utilise un autre fichier persistence.xml avec une persistence unit JDBC
     */
        properties.put(PersistenceUnitProperties.ECLIPSELINK_PERSISTENCE_XML, "META-INF/persistence-create.xml");

    EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("BookStore-JDBC", properties);
    EntityManager entityManager = entityManagerFactory.createEntityManager();
    entityManager.getTransaction().begin();

    List<Category> categories = entityManager.createQuery("select c from Category c").getResultList();
    for (Category category : categories) {
      System.out.println(category.getTitle());
      for(Book book : category.getBooks()) {
        System.out.println(book.getTitle());
        System.out.println(book.getPrice());
        System.out.println(book.getDate());
        for(Author author : book.getAuthors()) {
          System.out.println(author.getLastName());
        }
      }
    }
    entityManager.getTransaction().commit();
 }

}
