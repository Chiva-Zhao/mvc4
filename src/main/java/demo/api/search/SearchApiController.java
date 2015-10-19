package demo.api.search;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.twitter.api.Tweet;
import org.springframework.web.bind.annotation.MatrixVariable;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import demo.model.LightTweet;
import demo.service.SearchService;

@RestController
@RequestMapping("/api/search")
public class SearchApiController {

	private SearchService searchService;
	// private ParallelSearchService searchService;

	@Autowired
	public SearchApiController(SearchService searchService) {
		this.searchService = searchService;
	}

	@RequestMapping(value = "/{searchType}", method = RequestMethod.POST)
	public List<Tweet> search(@PathVariable String searchType, @MatrixVariable(value = "keywords") List<String> keywords) {
		return searchService.search(searchType, keywords);
	}

	@RequestMapping(value = "/{searchType}", method = RequestMethod.GET)
	public List<LightTweet> search1(@PathVariable String searchType, @MatrixVariable(value = "keywords") List<String> keywords) {
		return searchService.search1(searchType, keywords);
	}
}
