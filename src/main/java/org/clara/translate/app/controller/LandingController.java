package org.clara.translate.app.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping("/")
public class LandingController {

    @GetMapping
    public ModelAndView goToLandingPage() {
        return new ModelAndView("landing");
    }
}
