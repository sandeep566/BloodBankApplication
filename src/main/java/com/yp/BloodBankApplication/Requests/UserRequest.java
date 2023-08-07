package com.yp.BloodBankApplication.Requests;

public class UserRequest {

    private String userName;

    private String userPassword;

    private String otp;


    public UserRequest(String userName, String userPassword, String otp) {
        this.userName = userName;
        this.userPassword = userPassword;
        this.otp = otp;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }
}
