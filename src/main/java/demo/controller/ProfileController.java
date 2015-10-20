package demo.controller;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import demo.model.ProfileForm;
import demo.profile.UserProfileSession;
import demo.utils.USLocalDateFormatter;

@Controller
public class ProfileController {
	@Autowired
	private UserProfileSession userProfileSession;

	@ModelAttribute("dateFormat")
	public String localeFormat(Locale locale) {
		return USLocalDateFormatter.getPattern(locale);
	}

	@RequestMapping("/profile")
	public String displayProfile(ProfileForm profileForm) {
		return "profile/profilePage";
	}

	@RequestMapping(value = "/profile", method = RequestMethod.POST, params = "save")
	public String saveProfile(@Valid ProfileForm profileForm, BindingResult result) {
		if (result.hasErrors())
			return "profile/profilePage";
		System.out.println("save ok" + profileForm);
		// return "redirect:/profile";
		return "redirect:/search/mixed;keywords=" + String.join(",", userProfileSession.getTastes());
	}

	@RequestMapping(value = "/profile", params = { "addTaste" })
	public String addRow(ProfileForm profileForm, HttpServletRequest req) {
		profileForm.getTastes().add(req.getParameter("removeTaste"));
		return "profile/profilePage";
	}

	@RequestMapping(value = "/profile", params = { "removeTaste" })
	public String removeRow(ProfileForm profileForm, HttpServletRequest req) {
		Integer rowId = Integer.valueOf(req.getParameter("removeTaste"));
		profileForm.getTastes().remove(rowId.intValue());
		return "profile/profilePage";
	}

}
