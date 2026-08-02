package com.example.app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {
  /**
   * FYI
   * ログインとログアウト処理を行う@PostMappingが付与されたメソッドはここには無い
   * Spring Securityがデフォルトのログイン/ログアウトメソッドを用意してくれているのでそれを利用している
   */

  @GetMapping("/home")
  public String index() { return "index"; } // view名はtemplatesからの相対パスで記述する

  @GetMapping("/login")
  public String showLoginForm() { return "login"; }

  @GetMapping("/logout")
  public String showLogoutForm() { return "logout"; }
}
