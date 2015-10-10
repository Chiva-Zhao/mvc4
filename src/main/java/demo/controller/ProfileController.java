package demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import demo.model.ProfileForm;

@Controller
public class ProfileController {
	@RequestMapping("/profile")
	public String displayProfile(ProfileForm profileForm) {
		return "profile/profilePage";
	}

	@RequestMapping(value = "/profile", method = RequestMethod.POST)
	public String saveProfile(ProfileForm profileForm) {
		System.out.println("save ok" + profileForm);
		return "redirect:/profile";
	}
}
