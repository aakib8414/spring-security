package com.jwt.service;

import com.jwt.model.RefreshToken;
import com.jwt.model.User;
import com.jwt.repository.RefreshTokenRepo;
import com.jwt.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
public class RefreshTokenService {

    public long validity = 5 * 60 * 60 * 1000;
    @Autowired
    private RefreshTokenRepo tokenRepo;

    @Autowired
    private UserRepository userRepository;

    public RefreshToken createToken(String userName) {

        User user = userRepository.findUserByUserName(userName).get();
        RefreshToken token = user.getRefreshToken();

        if (token == null) {
            token = RefreshToken.builder().refreshToken(UUID.randomUUID().toString())
                    .expiry(Instant.now().plusMillis(validity))
                    .user(userRepository.findUserByUserName(userName).get())
                    .build();
            tokenRepo.save(token);
        } else {
            token.setExpiry(Instant.now().plusMillis(validity));
        }
        user.setRefreshToken(token);
        userRepository.save(user);
        return token;
    }

    public RefreshToken verifyRefreshToken(String token) throws Exception {
        RefreshToken refreshToken = tokenRepo.findByRefreshToken(token).orElseThrow(() -> new EntityNotFoundException("Token is not available in DB"));
        if (refreshToken.getExpiry().compareTo(Instant.now()) < 0) {
            tokenRepo.delete(refreshToken);
            throw new Exception("Refresh Token is Expired");
        }
        return refreshToken;
    }
}
