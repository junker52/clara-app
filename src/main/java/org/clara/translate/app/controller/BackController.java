package org.clara.translate.app.controller;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.clara.translate.app.service.CombinationService;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.ModelAndView;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/back")
@AllArgsConstructor
public class BackController {
	
	private CombinationService combinationService;
	
	private LocaleResolver localeResolver;
	
	@GetMapping
	public ModelAndView getBack(HttpServletRequest request, HttpServletResponse response) {
		this.localeResolver.setLocale(request, response, new Locale("cat"));
		ModelAndView model = new ModelAndView("back"); 
		model.addObject("combinations", this.combinationService.getAllCombinations());
		return model;
	}

}
