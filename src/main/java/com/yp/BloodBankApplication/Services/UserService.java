package com.yp.BloodBankApplication.Services;

import com.yp.BloodBankApplication.Entity.User;
import com.yp.BloodBankApplication.Exception.OtpMissMatchException;
import com.yp.BloodBankApplication.Repository.UserRepository;
import com.yp.BloodBankApplication.Requests.UserRequest;
import com.yp.BloodBankApplication.Response.OtpResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Random;


/**
 * Service class for managing user-related operations.
 */
@Service
public class UserService {



    private static final String OTP_CHARACTERS = "0123456789";

    private String otp = "";

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private BloodBankService bloodBankService;





    public OtpResponse checkuserName(String username){
        User userInfo = userRepository.findByUserName(username).orElse(null);
        if(userInfo != null){
            otp = generateOTP(4);
//            System.out.println(otp);
            OtpResponse otpResponse = new OtpResponse(username,otp);
            return otpResponse;
        }
        throw new UsernameNotFoundException("user not found");
    }

    /**
     * Resets the password for a user.
     *
     * @param UserRequest the User object containing the username and the new password
     * @return a message indicating the success of the password reset
     * @throws UsernameNotFoundException if the user with the specified username does not exist
     */
    public String resetPassword(UserRequest user) {
        User userInfo = userRepository.findByUserName(user.getUserName()).orElse(null);
        if(userInfo != null){
            if(user.getOtp().equals(otp)){
                userInfo.setUserPassword(passwordEncoder.encode(user.getUserPassword()));
                userRepository.save(userInfo);
                return "Password changed successfully";
            }
//            userInfo.setUserName(user.getUserName());
            throw new OtpMissMatchException("Otp not match");
        }
        throw new UsernameNotFoundException("User doesn't exists");
    }

    private static String generateOTP(int length) {
        Random random = new Random();
        StringBuilder otpBuilder = new StringBuilder();

        for (int i = 0; i < length; i++) {
            int index = random.nextInt(OTP_CHARACTERS.length());
            char otpChar = OTP_CHARACTERS.charAt(index);
            otpBuilder.append(otpChar);
        }
        return otpBuilder.toString();
    }
}
