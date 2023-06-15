
/**
 * The {@code UserInfoUserDetailsService} class implements the {@code UserDetailsService} interface
 * and provides the service for loading user details from the repository in the Blood Bank Application.
 */

package com.yp.BloodBankApplication.Configuration;


import com.yp.BloodBankApplication.Entity.User;
import com.yp.BloodBankApplication.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UserInfoUserDetailsService implements UserDetailsService {
    @Autowired
    private UserRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = repository.findByUserName(username);
        return user.map(UserInfoUserDetails::new)
                .orElseThrow(() -> new UsernameNotFoundException("user not found " + username));

    }
}
