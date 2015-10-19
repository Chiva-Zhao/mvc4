package demo.api.search.cache;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;

import demo.model.LightTweet;
import demo.service.TwitterSearch;

@Service
@Profile("!async")
public class SearchCacheService implements TwitterSearch {
	private SearchCache searchCache;

	@Autowired
	public SearchCacheService(SearchCache searchCache) {
		this.searchCache = searchCache;
	}

	public List<LightTweet> search(String searchType, List<String> keywords) {
		return keywords.stream().flatMap(keyword -> searchCache.fetch(searchType, keyword).stream()).collect(Collectors.toList());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see demo.service.TwitterSearch#search1(java.lang.String, java.util.List)
	 */
	@Override
	public List<LightTweet> search1(String searchType, List<String> keywords) {
		List<LightTweet> results = new ArrayList<>();
		// List<SearchParameters> searches = keywords.stream().map(taste ->
		// createSearchParam(searchType, taste)).collect(Collectors.toList());
		// List<LightTweet> results = searches.stream().map(params ->
		// twitter.searchOperations().search(params))
		// .flatMap(searchResults ->
		// searchResults.getTweets().stream()).map(LightTweet::ofTweet).collect(Collectors.toList());
		LightTweet tweet = new LightTweet("hello,world");
		tweet.setDate(LocalDateTime.now());
		tweet.setLang("zh");
		tweet.setProfileImageUrl("http://download.easyicon.net/png/1189119/32/");
		tweet.setUser("chiva");
		results.add(tweet);
		return results;
	}

	// For websocket
	public ListenableFuture<List<LightTweet>> search(String searchType, String keyword) {
		return new AsyncResult<>(searchCache.fetch(searchType, keyword));
	}
}
