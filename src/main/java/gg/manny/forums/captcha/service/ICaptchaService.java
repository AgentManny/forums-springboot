package gg.manny.forums.captcha.service;

import gg.manny.forums.captcha.exeption.InvalidCaptchaException;

public interface ICaptchaService {

    void processResponse(String response) throws InvalidCaptchaException;

}
