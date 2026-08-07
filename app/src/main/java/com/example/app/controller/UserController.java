package com.example.app.controller;

import com.example.app.dto.CreateUserDTO;
import com.example.app.entity.UserEntity;
import com.example.app.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
  private final UserService userService;

  /**
   * ユーザ一覧画面表示
   * ユーザ一覧を取得し、ユーザ一覧画面を生成して返す
   * @param model - ControllerとThymeleaf間で受け渡しするデータオブジェクト
   * @return userListHtml
   */
  @GetMapping // "/"なので省略可能
  public String showList(Model model) {
    List<UserEntity> allUsers = userService.findAll();

    model.addAttribute("userList", allUsers);
    return "users/list"; // templatesからの相対パス list.html
  }

  /**
   * ユーザ作成画面表示
   * @return creationFormHtml
   */
  @GetMapping("/creationForm")
  public String showCreationForm() {
    return "users/creationForm"; // templateからの相対パス creationForm.html
  }

  /**
   * ユーザ登録
   * @param dto - 登録情報
   * @return redirectToUsers
   */
  @PostMapping // "/"なので省略可能
  public String create(CreateUserDTO dto, RedirectAttributes redirectAttributes) {
    try {
      userService.create(dto);
      return "redirect:/users"; // 登録成功したら一覧画面にリダイレクト
    } catch (RuntimeException e) {
      redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
      redirectAttributes.addFlashAttribute("username", dto.username());
      redirectAttributes.addFlashAttribute("password", dto.password());
      return "redirect:users/creationForm"; // 登録失敗したら登録画面に戻ってエラーメッセージ表示
    }
  }
}
