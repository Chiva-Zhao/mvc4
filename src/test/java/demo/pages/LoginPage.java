package demo.pages;

import static org.assertj.core.api.Assertions.assertThat;

import org.fluentlenium.core.FluentPage;
import org.fluentlenium.core.domain.FluentWebElement;
import org.openqa.selenium.support.FindBy;

public class LoginPage extends FluentPage {
	@FindBy(name = "twitterSignin")
	FluentWebElement signinButton;

	public String getUrl() {
		return "/login";
	}

	public void isAt() {
		assertThat(findFirst("h2").getText()).isEqualTo("Login");
	}

	public void login() {
		signinButton.click();
	}
}
