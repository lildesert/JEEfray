/**
 * 
 */
package entities;

import java.math.BigDecimal;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

/**
 * @author dga
 * 
 */
@Entity
public class OrderItem extends Persistent {
  private int quantity;
  private Book book;
  private BigDecimal total;
  private Order order;

  public OrderItem() {
  }

  public OrderItem(Book book, int quantity) {
    this.book = book;
    this.quantity = quantity;
  }

  /* On suppose que l'on ne compare des CommandeItem que pour une même commande */
  public boolean equals(Object other) {
    if (other != null && other instanceof OrderItem) {
      return book.equals(((OrderItem) other).getBook());
    }
    return false;
  }

  public int getQuantity() {
    return quantity;
  }

  public void setQuantity(int quantity) {
    this.quantity = quantity;
  }

  @ManyToOne
  public Book getBook() {
    return book;
  }

  public void setBook(Book book) {
    this.book = book;
  }

  public BigDecimal getTotal() {
    return total;
  }

  public void setTotal(BigDecimal total) {
    this.total = total;
  }

  public void computeTotal() {
    total = book.getPrice()
        .multiply(new BigDecimal(quantity));
  }

  @ManyToOne
  public Order getOrder() {
    return order;
  }

  public void setOrder(Order order) {
    this.order = order;
  }

  public void addOne() {
    quantity++;
  }

  public void removeOne() {
    quantity--;
  }
}
