package com.example.demo.sample;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.auth.vo.LoginVO;

@Controller
public class HomeController {


	@RequestMapping("/")
	public String index() {
		return "/index";
	}
	
	
	@PostMapping("/login")
	public String login(LoginVO login) {
		System.out.println("username:" + login.getUsername());
		
		return "redirect:/";
	}

	@PostMapping("/logout")
	public String logout() {
		return "redirect:/loginForm";
	}

	
	
	
	@GetMapping("/loginForm")
	public String loginForm() {
		return "/loginForm";
	}

}
