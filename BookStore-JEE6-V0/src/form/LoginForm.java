package form;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

@Named
@RequestScoped
public class LoginForm {
  private String login;
  private String password;

  @NotNull
  @Length(min = 3, max = 25)
  public String getLogin() {
    return login;
  }
  public void setLogin(String login) {
    this.login = login;
  }
  @NotNull
  @Length(min = 3, max = 25)
  public String getPassword() {
    return password;
  }
  public void setPassword(String password) {
    this.password = password;
  }
}