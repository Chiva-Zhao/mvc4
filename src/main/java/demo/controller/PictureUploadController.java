package demo.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Path;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.WebUtils;

import demo.config.PicturesUploadProperties;

@Controller
@SessionAttributes("picturePath")
public class PictureUploadController {
	// public static final Resource PICTURES_DIR = new
	// FileSystemResource("./pictures");
	private final Resource picturesDir;
	private final Resource anonymousPicture;

	@Autowired
	public PictureUploadController(PicturesUploadProperties uploadProperties) {
		picturesDir = uploadProperties.getUploadPath();
		anonymousPicture = uploadProperties.getAnonymousPicture();
	}

	@RequestMapping("upload")
	public String uploadPage() {
		return "profile/uploadPage";
	}

	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	public String onUpload(MultipartFile file, RedirectAttributes redirectAttrs, Model model) throws IOException {
		if (file.isEmpty() || !isImage(file)) {
			throw new IOException("Incorrect file.Please upload a picture.");
		}
		//		copyFileToPictures(file);
		Resource picturePath = copyFileToPictures(file);
		model.addAttribute("picturePath", picturePath.getFile().toPath());
		return "profile/uploadPage";
	}

	@RequestMapping(value = "/uploadedPicture")
	public void getUploadedPicture(HttpServletResponse response, @ModelAttribute("picturePath") Path picturePath) throws IOException {
		//		ClassPathResource classPathResource = new ClassPathResource("/images/anonymous.jpg");
		//		response.setHeader("Content-Type", URLConnection.guessContentTypeFromName(classPathResource.getFilename()));
		//		IOUtils.copy(classPathResource.getInputStream(), response.getOutputStream());

		//		response.setHeader("Content-Type", URLConnection.guessContentTypeFromName(anonymousPicture.getFilename()));
		//		IOUtils.copy(anonymousPicture.getInputStream(), response.getOutputStream());
		response.setHeader("Content-Type", URLConnection.guessContentTypeFromName(picturePath.toString()));
		Files.copy(picturePath, response.getOutputStream());
	}

	@ModelAttribute("picturePath")
	public Path picturePath() throws IOException {
		return anonymousPicture.getFile().toPath();
	}

	@ExceptionHandler
	public ModelAndView handleIOException(IOException exception) {
		ModelAndView modelAndView = new ModelAndView("profile/uploadPage");
		modelAndView.addObject("error", exception.getMessage());
		return modelAndView;
	}

	@RequestMapping("/uploadError")
	public ModelAndView onUploadError(HttpServletRequest request) {
		ModelAndView modelAndView = new ModelAndView("uploadPage");
		modelAndView.addObject("error", request.getAttribute(WebUtils.ERROR_MESSAGE_ATTRIBUTE));
		return modelAndView;
	}

	private Resource copyFileToPictures(MultipartFile file) throws IOException {
		String fileExtension = getFileExtension(file.getOriginalFilename());
		//		File tempFile = File.createTempFile("pic", fileExtension, PICTURES_DIR.getFile());
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
