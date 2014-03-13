package form;

import java.io.Serializable;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

@Named
@RequestScoped
public class SearchForm {
	private String text;

	@Length(min = 0, max = 50)
	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
}
