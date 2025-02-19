package com.PostMvc.PostMvc.controller;

import com.PostMvc.PostMvc.service.AuthService;
import com.PostMvc.PostMvc.service.UsersService;
import com.PostMvc.PostMvc.domain.UsersVo;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UsersService usersService;

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AuthService authService;

    //    회원 가입
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody UsersVo user) {
        try {


            boolean created = usersService.create(user);

            if (created) {
                log.info("회원가입 성공: {}", user.getUsername());
                return ResponseEntity.ok("회원가입 성공");
            } else {
                log.warn("회원가입 실패: {}", user.getUsername());
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("회원가입 실패");
            }
        } catch (Exception e) {
            log.error("회원가입 처리 중 오류 발생", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("회원가입 처리 중 오류 발생");
        }
    }

    //    로그인
    @PostMapping("/login")
    public String login(@RequestBody UsersVo user, HttpServletRequest request) {
        try {
            user = authService.login(user);

            if (user != null) {
                HttpSession session = request.getSession();
                session.setAttribute("userId", user.getUserId());
                session.setAttribute("isLoggedIn", true);

                return "로그인 성공";
            }
            return "로그인 실패";
        } catch (Exception e) {
            e.printStackTrace();
            HttpSession session = request.getSession();
            session.invalidate();
            return "오류 발생: " + e.getMessage();
        }
    }

    //    로그아웃
    @GetMapping("/logout")
    public String logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        return "로그아웃 완료";
    }

    //    프로필
    @PostMapping("/update-profile")
    public String updateProfile(@RequestBody UsersVo user, HttpServletRequest request) {
        boolean updated = usersService.update(user);
        if (updated) {
            return "완료";
        } else {
            return "실패";
        }
    }

//    비밀번호 수정
@PostMapping("/update-password")
public ResponseEntity<String> updatePassword(
        @RequestBody Map<String, String> passwordUpdate,
        HttpServletRequest request) {
    try {
        // 세션에서 userId 가져오기
        HttpSession session = request.getSession();
        String userId = (String) session.getAttribute("userId");

        if (userId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("로그인이 필요합니다.");
        }

        String currentPassword = passwordUpdate.get("password");
        String newPassword1 = passwordUpdate.get("newPassword1");
        String newPassword2 = passwordUpdate.get("newPassword2");

        // 새 비밀번호 일치 확인
        if (!newPassword1.equals(newPassword2)) {
            return ResponseEntity.badRequest()
                    .body("새 비밀번호가 일치하지 않습니다.");
        }

        // 현재 사용자 정보 조회
        UsersVo existUsersVo = new UsersVo();
        existUsersVo.setUserId(userId);
        UsersVo existUser = usersService.read(existUsersVo);

        // 현재 비밀번호 확인
        if (!passwordEncoder.matches(currentPassword, existUser.getPassword())) {
            return ResponseEntity.badRequest()
                    .body("현재 비밀번호가 일치하지 않습니다.");
        }

        // 새 비밀번호 암호화 및 업데이트
        UsersVo newPasswordUser = new UsersVo();
        newPasswordUser.setUserId(userId);
        newPasswordUser.setPassword(newPassword1);

        boolean updated = usersService.updatePassword(newPasswordUser);

        if (updated) {
            return ResponseEntity.ok("비밀번호가 성공적으로 변경되었습니다.");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("비밀번호 변경에 실패했습니다.");
        }
    } catch (Exception e) {
        log.error("비밀번호 변경 중 오류 발생", e);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("비밀번호 변경 중 오류가 발생했습니다.");
    }
}

// 아이디 찾기
    @PostMapping("/find-user-id")
    public ResponseEntity<String> findUserId(UsersVo usersVo , HttpServletRequest request) {
        UsersVo user = usersService.read(usersVo);

        if(user != null) {

            return ResponseEntity.status(HttpStatus.OK)
                    .body("아이디는 " + user.getUsername() + "입니다.");
        }else {

            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("아이디 찾지 못함");
        }

    }

//    비밀번호 초기화
    @PostMapping("/reset-password")
    public ResponseEntity<String> resetPassword(UsersVo usersVo,HttpServletRequest request) {
        UsersVo user = usersService.read(usersVo);

        if(user != null) {
            String rndPassword = authService.resetPassword(user);
            return ResponseEntity.status(HttpStatus.OK)
                    .body("초기화 비번: "+rndPassword);
        }
        else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("초기화 실패");
        }
    }

//    회원 탈퇴
    @PostMapping("/delete")
    public ResponseEntity<String> delete(@RequestBody Map<String, String> requestBody, HttpServletRequest request) {
        String userId = (String) request.getSession().getAttribute("userId");
        String password = requestBody.get("password");
        UsersVo userVo = new UsersVo();
        userVo.setUserId(userId);


        boolean deleted = usersService.delete(userVo, password);
        if (deleted) {
            return ResponseEntity.ok("삭제 완료");
        }else{
            return ResponseEntity.badRequest().body("비밀번호 틀림");
        }
    }

}