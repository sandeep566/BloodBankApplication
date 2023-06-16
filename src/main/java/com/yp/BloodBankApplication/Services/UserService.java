package com.yp.BloodBankApplication.Services;

import com.yp.BloodBankApplication.Entity.User;
import com.yp.BloodBankApplication.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;



/**
 * Service class for managing user-related operations.
 */
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private BloodBankService bloodBankService;


    /**
     * Resets the password for a user.
     *
     * @param user the User object containing the username and the new password
     * @return a message indicating the success of the password reset
     * @throws UsernameNotFoundException if the user with the specified username does not exist
     */

    public String resetPassword(User user) {
        User userInfo = userRepository.findByUserName(user.getUserName()).orElse(null);
        if(userInfo != null){
            userInfo.setUserName(user.getUserName());
            userInfo.setUserPassword(passwordEncoder.encode(user.getUserPassword()));
            userRepository.save(userInfo);
            return "Password changed successfully";
        }
        throw new UsernameNotFoundException("User doesn't exists");
    }
}
