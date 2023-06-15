package com.yp.BloodBankApplication.Services;

import com.yp.BloodBankApplication.Entity.User;
import com.yp.BloodBankApplication.Exception.BloodBankAlreadyPresentException;
import com.yp.BloodBankApplication.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private BloodBankService bloodBankService;



    public String resetPassword(User user) {
        User userInfo = userRepository.findByUserName(user.getUserName()).orElse(null);
        if(userInfo != null){
            userInfo.setUserName(user.getUserName());
            userInfo.setUserPassword(passwordEncoder.encode(user.getUserPassword()));
            userRepository.save(userInfo);
            return "Password changed successfully";
        }
        throw new UsernameNotFoundException("User doesnot exists");
    }
}
