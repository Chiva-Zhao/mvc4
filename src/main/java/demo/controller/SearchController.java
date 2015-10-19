package demo.controller;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.twitter.api.Tweet;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.MatrixVariable;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import demo.model.LightTweet;
import demo.service.SearchService;

@Controller
public class SearchController {
	private SearchService searchService;

	@Autowired
	public SearchController(SearchService searchService) {
		this.searchService = searchService;
	}

	@RequestMapping("/search/{searchType}")
	public ModelAndView search(@PathVariable String searchType, @MatrixVariable List<String> keywords) {
		List<Tweet> tweets = searchService.search(searchType, keywords);
		ModelAndView modelAndView = new ModelAndView("resultPage");
		modelAndView.addObject("tweets", tweets);
		modelAndView.addObject("search", String.join(",", keywords));
		return modelAndView;
	}

	@RequestMapping("/search1/{searchType}")
	public ModelAndView search1(@PathVariable String searchType, @MatrixVariable List<String> keywords, HttpServletResponse response) {
		List<LightTweet> tweets = searchService.search1(searchType, keywords);
		ModelAndView modelAndView = new ModelAndView("resultPage");
		modelAndView.addObject("tweets", tweets);
		modelAndView.addObject("search", String.join(",", keywords));
//		response.setHeader("Cache-Control", "no-cache"); ETag Test needs this line
		return modelAndView;
	}
}
