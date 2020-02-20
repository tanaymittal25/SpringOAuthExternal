package com.howtodoinjava.demo.resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

@Controller
public class LoginController {
	
	@RequestMapping("/login")
	public String login() {
		String redirectUrl = "http://localhost:8081/login";
		return "redirect:" + redirectUrl;
	}

}
