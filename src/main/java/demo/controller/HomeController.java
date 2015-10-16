package demo.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import demo.model.Tweet;
import demo.profile.UserProfileSession;

@Controller
public class HomeController {

	private UserProfileSession userProfileSession;

	@Autowired
	public HomeController(UserProfileSession userProfileSession) {
		this.userProfileSession = userProfileSession;
	}

	@RequestMapping("/")
	public String home() {
		List<String> tastes = userProfileSession.getTastes();
		if (tastes.isEmpty()) {
			return "redirect:/profile";
		}
		return "redirect:/search/mixed;keywords=" + String.join(",", tastes);
	}

	@RequestMapping(value = "/postSearch", method = RequestMethod.POST)
	public String postSearch(HttpServletRequest request, RedirectAttributes redirectAttributes) {
		String search = request.getParameter("search");
		if (search.toLowerCase().contains("struts")) {
			redirectAttributes.addFlashAttribute("error", "Try	using spring instead!");
			return "redirect:/";
		}
		redirectAttributes.addAttribute("search", search);
		return "redirect:result";
	}

	@RequestMapping("/result")
	public String hello(@RequestParam(defaultValue = "mvc", value = "search") String query, Model model) {
		List<Tweet> tweets = new ArrayList<>();
		Tweet t1 = new Tweet("chiva", "hello", "http://download.easyicon.net/png/1189119/32/");
		Tweet t2 = new Tweet("chiva1", "hello1", "http://download.easyicon.net/png/1189119/24/");
		Tweet t3 = new Tweet("chiva2", "hello2", "http://download.easyicon.net/png/1189119/16/");
		tweets.add(t1);
		tweets.add(t2);
		tweets.add(t3);
		model.addAttribute("tweets", tweets);
		model.addAttribute("search", query);
		return "resultPage";
	}
}
