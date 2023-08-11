package com.jwt.controller;

import com.jwt.helper.JwtUtil;
import com.jwt.model.JwtRequest;
import com.jwt.model.JwtResponse;
import com.jwt.model.RefreshToken;
import com.jwt.model.User;
import com.jwt.reuest.RefreshTokenRequest;
import com.jwt.service.CustomUserDetailService;
import com.jwt.service.RefreshTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class JwtController {


//    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private CustomUserDetailService userDetailService;
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private RefreshTokenService tokenService;

    @Autowired
    public void set(AuthenticationManager manager){
        authenticationManager=manager;
    }

    @PostMapping("/token")
    public ResponseEntity<?> createToken(@RequestBody JwtRequest request) throws Exception {

        System.out.println("Request: " + request);
        try {
            this.authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Bad Request");
        }
        UserDetails userDetails = this.userDetailService.loadUserByUsername(request.getUsername());
        String token = this.jwtUtil.generateToken(userDetails);
        RefreshToken refreshToken = this.tokenService.createToken(userDetails.getUsername());
        return ResponseEntity.ok(new JwtResponse(token, userDetails.getUsername(), refreshToken.getRefreshToken()));
    }

    @PostMapping("/refresh")
    public JwtResponse refreshJwtToken(@RequestBody RefreshTokenRequest request) throws Exception {
        RefreshToken refreshToken = this.tokenService.verifyRefreshToken(request.getRefreshToken());
        User user = refreshToken.getUser();
        String jwtToken = this.jwtUtil.generateToken(this.userDetailService.loadUserByUsername(user.getUserName()));
        return JwtResponse.builder()
                .refreshToken(refreshToken.getRefreshToken())
                .userName(user.getUserName())
                .jwttoken(jwtToken)
                .build();
    }
}
