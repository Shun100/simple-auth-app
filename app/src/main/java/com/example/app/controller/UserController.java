package com.example.app.controller;

import com.example.app.dto.CreateUserDTO;
import com.example.app.entity.UserEntity;
import com.example.app.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Objects;

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
   * @param bindingResult - Validation結果を保持するオブジェクト
   * @param redirectAttributes - リダイレクトオブジェクト
   * @return redirectToUsers
   */
  @PostMapping // "/"なので省略可能
  public String create(
    @Validated CreateUserDTO dto,
    BindingResult bindingResult,
    RedirectAttributes redirectAttributes) {

    if (bindingResult.hasErrors()) {
      // 入力内容に不備があれば登録画面に戻ってエラー表示
      redirectAttributes.addFlashAttribute("errorMessage", getErrorMessage(bindingResult));
      redirectAttributes.addFlashAttribute("username", dto.username());
      redirectAttributes.addFlashAttribute("password", dto.password());
      return "redirect:users/creationForm";
    }

    try {
      // 登録成功したら一覧画面にリダイレクト
      userService.create(dto);
      return "redirect:/users";

    } catch (RuntimeException e) {
      // 登録失敗したら登録画面に戻ってエラー表示
      redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
      redirectAttributes.addFlashAttribute("username", dto.username());
      redirectAttributes.addFlashAttribute("password", dto.password());
      return "redirect:users/creationForm";
    }
  }

  /**
   * エラーメッセージ取得
   * @param bindingResult - Validation結果を保持するオブジェクト
   * @return errorMessage
   */
  private String getErrorMessage(BindingResult bindingResult) {
    return bindingResult.getFieldErrors()
      .stream()
      .map(DefaultMessageSourceResolvable::getDefaultMessage)
      .filter(Objects::nonNull)
      .findFirst()
      .orElse("入力内容に誤りがあります");
  }
}
