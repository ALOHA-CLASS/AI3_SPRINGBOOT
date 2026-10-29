package com.aloha.security_querydsl.service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aloha.security_querydsl.domain.UserAuth;
import com.aloha.security_querydsl.domain.Users;
import com.aloha.security_querydsl.repository.UserAuthRepository;
import com.aloha.security_querydsl.repository.UserRepository;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserAuthRepository userAuthRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    @Override
    public boolean login(Users user, HttpServletRequest request) throws Exception {
        // 💍 토큰 생성
        String username = user.getUsername();    // 아이디
        String password = user.getPassword();    // 암호화되지 않은 비밀번호
        UsernamePasswordAuthenticationToken token 
            = new UsernamePasswordAuthenticationToken(username, password);
        
        // 토큰을 이용하여 인증
        Authentication authentication = authenticationManager.authenticate(token);
        
        // 인증 여부 확인
        boolean result = authentication.isAuthenticated();

        // 인증이 성공하면 SecurityContext에 설정
        if (result) {
            SecurityContextHolder.getContext().setAuthentication(authentication);
            
            // 세션에 인증 정보 설정 (세션이 없으면 새로 생성)
            HttpSession session = request.getSession(true);  // 세션이 없으면 새로 생성
            session.setAttribute("SPRING_SECURITY_CONTEXT", SecurityContextHolder.getContext());
        }

        return result;
    }

    @Override
    public Users select(String username) throws Exception {
        return userRepository.findByUsernameWithAuth(username).orElse(null);
    }

    @Override
    @Transactional
    public boolean join(Users user) throws Exception {
        // 123456 -> 🔒 F123N905123890N3138932N4 (암호화)
        String encodedPassword = passwordEncoder.encode(user.getPassword());  // 🔒 비밀번호 암호화
        user.setPassword(encodedPassword);

        // 회원 등록
        Users saved = userRepository.save(user);

        // 회원 기본 권한 등록
        UserAuth userAuth = UserAuth.builder().auth("ROLE_USER").build();
        saved.addAuth(userAuth);
        userAuthRepository.save(userAuth);

        return saved.getNo() != null;
    }

    @Override
    @Transactional
    public boolean update(Users user) throws Exception {
        Users target = userRepository.findByUsernameWithAuth(user.getUsername())
                                    .orElseThrow(() -> new Exception("회원을 찾을 수 없습니다."));

        // 비밀번호 변경하는 경우 암호화 처리
        String password = user.getPassword();
        if( password != null && !password.isEmpty() ) {
          target.setPassword(passwordEncoder.encode(password));  // 🔒 비밀번호 암호화
        }
        if( user.getName() != null && !user.getName().isEmpty() ) {
          target.setName(user.getName());
        }
        if( user.getEmail() != null && !user.getEmail().isEmpty() ) {
          target.setEmail(user.getEmail());
        }
        userRepository.save(target);
        return true;
    }

}
