package demo.pages;

import static org.assertj.core.api.Assertions.assertThat;

import org.fluentlenium.core.FluentPage;
import org.fluentlenium.core.domain.FluentWebElement;
import org.openqa.selenium.support.FindBy;

public class ProfilePage extends FluentPage {
	@FindBy(name = "addTaste")
	FluentWebElement addTasteButton;
	@FindBy(name = "save")
	FluentWebElement saveButton;

	public String getUrl() {
		return "/profile";
	}

	public void isAt() {
		assertThat(title()).isEqualTo("Your profile");
	}

	public void fillInfos(String twitterHandle, String email, String birthDate) {
		fill("#twitterHandle").with(twitterHandle);
		fill("#email").with(email);
		fill("#birthDate").with(birthDate);
	}

	public void addTaste(String taste) {
		addTasteButton.click();
		fill("#tastes0").with(taste);
	}

	public void saveProfile() {
		saveButton.click();
	}
}
