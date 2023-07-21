package com.yp.BloodBankApplication.Repository;

import com.yp.BloodBankApplication.Entity.Hospital;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;



/**
 * Repository interface for accessing Hospital entities in the database.
 */
@Repository
public interface HospitalRepository extends JpaRepository<Hospital,Integer> {


    /**
     * Retrieves a hospital by its name.
     *
     * @param hospitalName the name of the hospital
     * @return an Optional containing the hospital if found, or an empty Optional if not found
     */
    Optional<Hospital> findByHospitalName(String hospitalName);

    Optional<Object> findByPhoneNo(long phoneNumber);

    Optional<Hospital> findByEmail(String userName);
}
