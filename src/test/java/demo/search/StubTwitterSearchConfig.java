package demo.search;

import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import demo.model.LightTweet;
import demo.service.TwitterSearch;

@Configuration
public class StubTwitterSearchConfig {
	@Primary
	@Bean
	public TwitterSearch twitterSearch() {
		return (searchType, keywords) -> Arrays.asList(new LightTweet("tweetText"), new LightTweet("secondTweet"));
	}
}
