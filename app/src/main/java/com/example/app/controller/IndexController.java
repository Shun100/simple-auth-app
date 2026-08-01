package com.example.app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {

  @GetMapping
  public String index() { return "index"; } // view名はtemplatesからの相対パスで記述する

  @GetMapping("/login")
  public String showLoginForm() { return "login"; }

}
