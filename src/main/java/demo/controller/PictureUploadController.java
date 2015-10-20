package demo.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLConnection;
import java.nio.file.Path;
import java.util.Locale;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import demo.config.PicturesUploadProperties;
import demo.profile.UserProfileSession;

@Controller
@SessionAttributes("picturePath")
public class PictureUploadController {
	// public static final Resource PICTURES_DIR = new
	// FileSystemResource("./pictures");
	private final Resource picturesDir;
	private final Resource anonymousPicture;

	private final MessageSource messageSource;
	private final UserProfileSession userProfileSession;

	@Autowired
	public PictureUploadController(PicturesUploadProperties uploadProperties, MessageSource messageSource,
			UserProfileSession userProfileSession) {
		picturesDir = uploadProperties.getUploadPath();
		anonymousPicture = uploadProperties.getAnonymousPicture();
		this.messageSource = messageSource;
		this.userProfileSession = userProfileSession;
	}

	@RequestMapping("upload")
	public String uploadPage() {
		return "profile/uploadPage";
	}

	@RequestMapping(value = "/profile", params = "upload", method = RequestMethod.POST)
	public String onUpload(MultipartFile file, RedirectAttributes redirectAttrs, Model model) throws IOException {
		if (file.isEmpty() || !isImage(file)) {
			// throw new IOException("Incorrect file.Please upload a picture.");
			redirectAttrs.addFlashAttribute("error", "Incorrect file.Please upload a picture.");
			return "redirect:/profile";
		}
		// copyFileToPictures(file);
		Resource picturePath = copyFileToPictures(file);
		// model.addAttribute("picturePath", picturePath.getFile().toPath());
		userProfileSession.setPicturePath(picturePath);
		return "redirect:/profile";
	}

	@RequestMapping(value = "/uploadedPicture")
	public void getUploadedPicture(HttpServletResponse response) throws IOException {
		// ClassPathResource classPathResource = new
		// ClassPathResource("/images/anonymous.jpg");
		// response.setHeader("Content-Type",
		// URLConnection.guessContentTypeFromName(classPathResource.getFilename()));
		// IOUtils.copy(classPathResource.getInputStream(),
		// response.getOutputStream());

		// response.setHeader("Content-Type",
		// URLConnection.guessContentTypeFromName(anonymousPicture.getFilename()));
		// IOUtils.copy(anonymousPicture.getInputStream(),
		// response.getOutputStream());
		Resource picturePath = userProfileSession.getPicturePath();
		if (picturePath == null) {
			picturePath = anonymousPicture;
		}
		response.setHeader("Content-Type", URLConnection.guessContentTypeFromName(picturePath.toString()));
		IOUtils.copy(picturePath.getInputStream(), response.getOutputStream());
	}

	@ModelAttribute("picturePath")
	public Path picturePath() throws IOException {
		return anonymousPicture.getFile().toPath();
	}

	@ExceptionHandler(IOException.class)
	public ModelAndView handleIOException(Locale locale) {
		ModelAndView modelAndView = new ModelAndView("profile/profilePage");
		modelAndView.addObject("error", messageSource.getMessage("upload.io.exception", null, locale));
		modelAndView.addObject("profileForm", userProfileSession.toForm());
		return modelAndView;
	}

	// TODO:cannot process MultipartException
	@RequestMapping("/uploadError")
	public ModelAndView onUploadError(Locale locale) {
		ModelAndView modelAndView = new ModelAndView("profile/profilePage");
		modelAndView.addObject("error", messageSource.getMessage("upload.file.too.big", null, locale));
		modelAndView.addObject("profileForm", userProfileSession.toForm());
		return modelAndView;
	}

	// TODO:cannot process MultipartException
	@ExceptionHandler(MultipartException.class)
	public ModelAndView onUploadError1(Locale locale) {
		ModelAndView modelAndView = new ModelAndView("profile/profilePage");
		modelAndView.addObject("error", messageSource.getMessage("upload.file.too.big", null, locale));
		modelAndView.addObject("profileForm", userProfileSession.toForm());
		return modelAndView;
	}

	private Resource copyFileToPictures(MultipartFile file) throws IOException {
		String fileExtension = getFileExtension(file.getOriginalFilename());
		// File tempFile = File.createTempFile("pic", fileExtension,
		// PICTURES_DIR.getFile());
		File tempFile = File.createTempFile("pic", fileExtension, picturesDir.getFile());
		try (InputStream in = file.getInputStream(); OutputStream out = new FileOutputStream(tempFile)) {
			IOUtils.copy(in, out);
		}
		return new FileSystemResource(tempFile);

	}

	private boolean isImage(MultipartFile file) {
		return file.getContentType().startsWith("image");
	}

	private static String getFileExtension(String name) {
		return name.substring(name.lastIndexOf("."));
	}
}
