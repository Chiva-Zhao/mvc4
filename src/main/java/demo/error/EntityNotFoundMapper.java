package demo.error;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import demo.profile.UserProfileSession;

@ControllerAdvice
public class EntityNotFoundMapper {

	@Autowired
	private MessageSource messageSource;
	@Autowired
	private UserProfileSession userProfileSession;

	@ExceptionHandler(EntityNotFoundException.class)
	@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Not found your request resource")
	public void handleNotFound() {

	}

	// @ExceptionHandler(MultipartException.class)
	// public ModelAndView onUploadError1(Locale locale) {
	// ModelAndView modelAndView = new ModelAndView("profile/profilePage");
	// modelAndView.addObject("error",
	// messageSource.getMessage("upload.file.too.big", null, locale));
	// modelAndView.addObject("profileForm", userProfileSession.toForm());
	// return modelAndView;
	// }
}
