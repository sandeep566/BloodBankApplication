package com.yp.BloodBankApplication.Response;

public class OtpResponse {

    private String userName;

    private String otp;

    public OtpResponse(String userName, String otp) {
        this.userName = userName;
        this.otp = otp;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }
}
