package com.volare_automation.springwebshop.service;

public interface EmailServiceInterface {
    public void sendMail(String toEmail, String subject, String message);
}
