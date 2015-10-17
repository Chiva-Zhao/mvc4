package demo;

import static org.assertj.core.api.Assertions.assertThat;
import static org.fluentlenium.core.filter.FilterConstructor.withName;

import org.fluentlenium.adapter.FluentTest;
import org.fluentlenium.core.annotation.Page;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriverService;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import demo.pages.LoginPage;
import demo.pages.ProfilePage;
import demo.pages.SearchResultPage;
import demo.search.StubTwitterSearchConfig;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = { DemoApplication.class, StubTwitterSearchConfig.class })
@WebIntegrationTest(randomPort = true)
public class FluentIntegrationTest extends FluentTest {
	@Value("${local.server.port}")
	private int serverPort;
	@Page
	private LoginPage loginPage;
	@Page
	private ProfilePage profilePage;
	@Page
	private SearchResultPage searchResultPage;

	@Override
	public WebDriver getDefaultDriver() {
		DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
		desiredCapabilities.setCapability(PhantomJSDriverService.PHANTOMJS_EXECUTABLE_PATH_PROPERTY,
				"C:/phantomjs-2.0.0/bin/phantomjs.exe");
		return new PhantomJSDriver(desiredCapabilities);
	}

	@Test
	public void hasPageTitle() {
		goTo("/");
		assertThat(findFirst("h2").getText()).isEqualTo("Login");
	}

	public String getDefaultBaseUrl() {
		return "http://localhost:" + serverPort;
	}

	@Test
	public void should_be_redirected_after_filling_form() {
		goTo("/");
		assertThat(findFirst("h2").getText()).isEqualTo("Login");
		find("button", withName("twitterSignin")).click();
		assertThat(title()).isEqualTo("Your profile");
		fill("#twitterHandle").with("geowarin");
		fill("#email").with("geowarin@mymail.com");
		fill("#birthDate").with("03/19/1987");
		find("button", withName("addTaste")).click();
		fill("#tastes0").with("spring");
		find("button", withName("save")).click();
		takeScreenShot();
		assertThat(findFirst("h2").getText()).isEqualTo("Tweet results for spring");
		assertThat(findFirst("ul.collection").find("li")).hasSize(2);
	}

	@Test
	public void should_be_redirected_after_filling_form2() {
		goTo("/");
		loginPage.isAt();
		loginPage.login();
		profilePage.isAt();
		profilePage.fillInfos("geowarin", "geowarin@mymail.com", "03/19/1987");
		profilePage.addTaste("spring");
		profilePage.saveProfile();
		takeScreenShot();
		searchResultPage.isAt();
		assertThat(searchResultPage.getNumberOfResults()).isEqualTo(2);
	}
}
