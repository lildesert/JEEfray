package form;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import service.CookieService;

@Named
@RequestScoped
public class LoginForm {
	private String login;
	private String password;
	private boolean remember = true;

	@NotNull
	@Length(min = 3, max = 25)
	public String getLogin() {
		if(!CookieService.getCookieValue("userLogin").equals(""))
		{
			return CookieService.getCookieValue("userLogin");
		}
		else
		{
			return login;
		}
	}

	public void setLogin(String login) {
		this.login = login;
	}

	@NotNull
	@Length(min = 3, max = 25)
	public String getPassword() {
		System.out.println("Call method getPwd()");
		if(!CookieService.getCookieValue("userPwd").equals(""))
		{
			System.out.println(CookieService.getCookieValue("userPwd"));
			return CookieService.getCookieValue("userPwd");
		}
		else
		{
			return password;
		}
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isRemember() {
		return remember;
	}

	public void setRemember(boolean remember) {
		this.remember = remember;
	}
}