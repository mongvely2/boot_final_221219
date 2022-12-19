package com.its.ex.controller;

import com.its.ex.dto.MemberDTO;
import com.its.ex.service.MemberService;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.io.IOException;

@Controller
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    @GetMapping("/save")
    public String saveForm() {
        return "memberPages/memberSave";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute MemberDTO memberDTO) throws IOException {
        memberService.save(memberDTO);
        return "memberPages/memberLogin";
    }

    @PostMapping("/dup-check")
    public ResponseEntity duplicateCheck(@RequestParam("inputEmail") String inputEmail) {
        String checkResult = memberService.duplicateCheck(inputEmail);
        if (checkResult != null) {
            return new ResponseEntity<>("사용 가능한 이메일 입니다", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("이미 사용중인 이메일 입니다", HttpStatus.CONFLICT);
        }
    }

    @GetMapping("/login")
    public String loginForm() {
        return "/memberPages/memberLogin";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute MemberDTO memberDTO,
                        HttpSession session) {
        MemberDTO memberLogin = memberService.login(memberDTO);
        session.setAttribute("loginSession", memberLogin);
        return "index";
    }

}
