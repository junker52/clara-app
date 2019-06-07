package org.clara.translate.app.security;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping("/login")
public class LoginController {
	
	@GetMapping
	public ModelAndView getLogin() {
		return new ModelAndView("login");
	}

}
