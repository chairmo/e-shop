package com.chairmo.eshop.rest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@RequestMapping("/")
public class HomeController {

	public RedirectView swaggerui() {
		return new RedirectView("/swagger-ui.html");
	}
}
