package com.yp.BloodBankApplication.Repository;

import com.yp.BloodBankApplication.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User , String> {
    Optional<User> findByUserName(String username);

}