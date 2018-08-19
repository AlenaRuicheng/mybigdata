package com.myself.mybigdata.web.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.myself.mybigdata.model.User;
import com.myself.mybigdata.service.UserService;

@Controller
public class UserController {
	@Resource
	UserService userService;
	
	@RequestMapping(value="/user/user_info", method=RequestMethod.GET)
	public String viewOne(Integer id, Model model) {
		User user = userService.getEntity(id);
		model.addAttribute("user", user);
		return "user_info";
	}

	@RequestMapping(value="/user/show_users", method=RequestMethod.GET)
	public String findAll(Model model) {
		List<User> list = userService.findAll();
		model.addAttribute("users", list);
		return "show_users";
	}

	@RequestMapping(value="/user/delete_one", method=RequestMethod.GET)
	public String deleteById(@RequestParam("id")Integer id) {
		userService.deleteById(id);
		return "redirect:/user/show_users";
	}

	@RequestMapping(value="/user/tosignup", method=RequestMethod.GET)
	public String toSignUp() {
		return "signup";
	}

	@RequestMapping(value="/user/signup", method=RequestMethod.POST)
	public String signUp(User user) {
		userService.saveEntity(user);
		return "redirect:/user/show_users";
	}

	@RequestMapping(value="/user/tologin", method=RequestMethod.GET)
	public String toLogin() {
		return "login";
	}

	@RequestMapping(value="/user/login", method=RequestMethod.POST)
	public String login(String name, String password, HttpSession session, Model model) {
		User user = userService.validateLoginInfo(name, password);
		if(user == null) {
			model.addAttribute("error", "Increct username or password!");
			return "login";
		}
		session.setAttribute("user", user);
		return "welcome";
	}
	
	@RequestMapping(value="/user/viewpv", method=RequestMethod.GET)
	public String viewPv(Model model) {
		List<String[]> pvItems = userService.queryTableBatch("pv");
		model.addAttribute("pvItems", pvItems);
		return "viewpv";
	}
	
}
