package com.aloha.shop.controller.users;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.aloha.shop.domain.users.User;
import com.aloha.shop.dto.users.AddressDto;
import com.aloha.shop.dto.users.UserJoinDto;
import com.aloha.shop.dto.users.UserUpdateDto;
import com.aloha.shop.security.CustomUser;
import com.aloha.shop.service.users.AddressService;
import com.aloha.shop.service.users.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

/**
 * 회원 - 가입/로그인/마이페이지(회원정보/배송지)
 */
@Controller
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final AddressService addressService;

    // 회원가입 화면
    @GetMapping("/join")
    public String joinForm(@ModelAttribute("joinDto") UserJoinDto joinDto) {
        return "users/join";
    }

    // 회원가입 처리
    @PostMapping("/join")
    public String join(@Valid @ModelAttribute("joinDto") UserJoinDto joinDto,
                       BindingResult bindingResult,
                       RedirectAttributes ra) {
        if (bindingResult.hasErrors()) {
            return "users/join";
        }
        if (!userService.isUsernameAvailable(joinDto.getUsername())) {
            bindingResult.rejectValue("username", "duplicate", "이미 사용 중인 아이디입니다.");
            return "users/join";
        }
        userService.join(joinDto);
        ra.addFlashAttribute("message", "회원가입이 완료되었습니다. 로그인해 주세요.");
        return "redirect:/users/login";
    }

    // 로그인 화면 (인증은 Security 필터가 처리)
    @GetMapping("/login")
    public String loginForm() {
        return "users/login";
    }

    // 마이페이지 대시보드
    @GetMapping("/mypage")
    public String mypage(@AuthenticationPrincipal CustomUser loginUser, Model model) {
        User user = userService.getByUsername(loginUser.getUsername());
        model.addAttribute("user", user);
        return "mypage/index";
    }

    // 회원정보 수정 화면
    @GetMapping("/mypage/info")
    public String infoForm(@AuthenticationPrincipal CustomUser loginUser, Model model) {
        User user = userService.getByUsername(loginUser.getUsername());
        UserUpdateDto dto = new UserUpdateDto();
        dto.setName(user.getName());
        dto.setEmail(user.getEmail());
        dto.setPhone(user.getPhone());
        model.addAttribute("updateDto", dto);
        model.addAttribute("user", user);
        return "mypage/info";
    }

    // 회원정보 수정 처리
    @PostMapping("/mypage/info")
    public String updateInfo(@AuthenticationPrincipal CustomUser loginUser,
                             @Valid @ModelAttribute("updateDto") UserUpdateDto updateDto,
                             BindingResult bindingResult,
                             Model model,
                             RedirectAttributes ra) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("user", userService.getByUsername(loginUser.getUsername()));
            return "mypage/info";
        }
        userService.updateInfo(loginUser.getUsername(), updateDto);
        ra.addFlashAttribute("message", "회원정보가 수정되었습니다.");
        return "redirect:/users/mypage/info";
    }

    // 배송지 관리 화면
    @GetMapping("/mypage/address")
    public String addressList(@AuthenticationPrincipal CustomUser loginUser, Model model) {
        model.addAttribute("addresses", addressService.list(loginUser.getNo()));
        model.addAttribute("addressDto", new AddressDto());
        return "mypage/address";
    }

    // 배송지 추가
    @PostMapping("/mypage/address")
    public String addAddress(@AuthenticationPrincipal CustomUser loginUser,
                             @Valid @ModelAttribute("addressDto") AddressDto addressDto,
                             BindingResult bindingResult,
                             Model model,
                             RedirectAttributes ra) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("addresses", addressService.list(loginUser.getNo()));
            return "mypage/address";
        }
        addressService.add(loginUser.getNo(), addressDto);
        ra.addFlashAttribute("message", "배송지가 추가되었습니다.");
        return "redirect:/users/mypage/address";
    }

    // 배송지 삭제
    @PostMapping("/mypage/address/{no}/delete")
    public String deleteAddress(@AuthenticationPrincipal CustomUser loginUser,
                                @PathVariable("no") Long no,
                                RedirectAttributes ra) {
        addressService.delete(loginUser.getNo(), no);
        ra.addFlashAttribute("message", "배송지가 삭제되었습니다.");
        return "redirect:/users/mypage/address";
    }
}
