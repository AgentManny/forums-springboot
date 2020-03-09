package gg.manny.forums.captcha.exeption;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class InvalidCaptchaException extends Exception {

    private String message;

    // todo add logging for invalid captchas

}
