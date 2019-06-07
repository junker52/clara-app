package org.clara.translate.app.controller;

import java.util.Locale;

import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping("/back")
public class BackController {
	
	@GetMapping
	public ModelAndView getBack() {
		LocaleContextHolder.setDefaultLocale(new Locale("ca"));
		return new ModelAndView("back");
	}

}
