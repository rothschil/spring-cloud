package xyz.wongs.exception;

import com.auth0.jwt.exceptions.JWTVerificationException;

public class NoUserException extends JWTVerificationException {

    private static final long serialVersionUID = -5955607821816077172L;

    public NoUserException(String errorInfo) {
        super(errorInfo);
    }
}
