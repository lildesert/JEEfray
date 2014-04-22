package service;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

public class CookieService {

	public static String getCookieValue(String key) {
		String cookieValue = "";
		HttpServletRequest httpServletRequest = (HttpServletRequest) FacesContext
				.getCurrentInstance().getExternalContext().getRequest();
		Cookie[] cookies = httpServletRequest.getCookies();
		if (cookies != null) {
			for (int i = 0; i < cookies.length; i++) {
				if (cookies[i].getName().equalsIgnoreCase(key)) {
					cookieValue = cookies[i].getValue();
				}
			}
		}
		
		return cookieValue;
	}

	public static void addCookie(String key, String value, int maxAge) {
		HttpServletResponse httpServletResponse = (HttpServletResponse) FacesContext
				.getCurrentInstance().getExternalContext().getResponse();
		Cookie cookie = new Cookie(key, value);
		cookie.setMaxAge(maxAge);
		httpServletResponse.addCookie(cookie);
	}

	public static void removeCookie(String key) {
		addCookie(key, null, 0);
	}
}
