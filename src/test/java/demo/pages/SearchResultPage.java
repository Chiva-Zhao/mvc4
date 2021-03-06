package demo.pages;

import static org.assertj.core.api.Assertions.assertThat;

import org.fluentlenium.core.FluentPage;
import org.fluentlenium.core.domain.FluentWebElement;
import org.openqa.selenium.support.FindBy;

import com.google.common.base.Joiner;

public class SearchResultPage extends FluentPage {
	@FindBy(css = "ul.collection")
	FluentWebElement resultList;

	public void isAt(String... keywords) {
		assertThat(findFirst("h2").getText()).isEqualTo("Tweet results for " + Joiner.on(",").join(keywords));
	}

	public int getNumberOfResults() {
		return resultList.find("li").size();
	}
}
