package controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;

@Named
@SessionScoped
public class LocaleBean implements Serializable {
	private List<Locale> locales;
	private Locale locale;

	public LocaleBean() {
		locale = FacesContext.getCurrentInstance().getViewRoot().getLocale();
		if (locale == null)
			FacesContext.getCurrentInstance().getApplication()
					.getDefaultLocale();
		Iterator<Locale> itl = FacesContext.getCurrentInstance()
				.getApplication().getSupportedLocales();
		locales = new ArrayList<Locale>();
		while (itl.hasNext()) {
			locales.add(itl.next());
		}
	}

	public List<Locale> getLocales() {
		return locales;
	}

	public void setLocales(List<Locale> locales) {
		this.locales = locales;
	}

	public Locale getLocale() {
		return locale;
	}

	public void setLocale(Locale locale) {
		this.locale = locale;
	}

	public String changeLocale(Locale locale) {
		this.locale = locale;
		FacesContext.getCurrentInstance().getViewRoot().setLocale(locale);
		return null;
	}
}