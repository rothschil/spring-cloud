package xyz.wongs.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.stereotype.Service;
import xyz.wongs.domain.User;

@Service("tokenService")
public class TokenService {


    public String getToken(User user) {
        // 将 user id 保存到 token 里面
        // 以 password 作为 token 的密钥
        String token= JWT.create().withAudience(user.getId().toString()).sign(Algorithm.HMAC256(user.getName()));
        return token;
    }
}
