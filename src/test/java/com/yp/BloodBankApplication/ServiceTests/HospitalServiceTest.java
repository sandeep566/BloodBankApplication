package com.yp.BloodBankApplication.ServiceTests;

import com.yp.BloodBankApplication.Entity.BloodBank;
import com.yp.BloodBankApplication.Entity.Hospital;
import com.yp.BloodBankApplication.Entity.User;
import com.yp.BloodBankApplication.Exception.BloodBankNotFoundException;
import com.yp.BloodBankApplication.Exception.HospitalNotFoundException;
import com.yp.BloodBankApplication.Repository.BloodBankRepository;
import com.yp.BloodBankApplication.Repository.HospitalRepository;
import com.yp.BloodBankApplication.Repository.UserRepository;
import com.yp.BloodBankApplication.Requests.BloodBankRequest;
import com.yp.BloodBankApplication.Requests.HospitalRequest;
import com.yp.BloodBankApplication.Services.BloodBankService;
import com.yp.BloodBankApplication.Services.HospitalService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;

public class HospitalServiceTest {

    @Mock
    private HospitalRepository hospitalRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private BloodBankRepository bloodBankRepository;

    @Mock
    private PasswordEncoder passwordEncoder;
    @InjectMocks
    private HospitalService hospitalService;

    @InjectMocks
    private BloodBankService bloodBankService;

    @BeforeEach
    public void setup() {
        openMocks(this);
    }

    @Test
    public void testAddHospital() {
        // Arrange
        HospitalRequest hospitalRequest = new HospitalRequest();
        hospitalRequest.setHospitalId(1);
        hospitalRequest.setHospitalName("Hospital 1");
        hospitalRequest.setAddress("123 Main St");
        hospitalRequest.setPhoneNo(1234567890);

        Hospital hospital = new Hospital();
        hospital.setHospitalId(hospitalRequest.getHospitalId());
        hospital.setHospitalName(hospitalRequest.getHospitalName());
        hospital.setAddress(hospitalRequest.getAddress());
        hospital.setPhoneNo(hospitalRequest.getPhoneNo());

        when(hospitalRepository.save(any(Hospital.class))).thenReturn(hospital);

        // Act
        Hospital result = hospitalService.addHospital(hospitalRequest);

        // Assert
        assertNotNull(result);
        assertEquals(hospitalRequest.getHospitalId(), result.getHospitalId());
        assertEquals(hospitalRequest.getHospitalName(), result.getHospitalName());
        assertEquals(hospitalRequest.getAddress(), result.getAddress());
        assertEquals(hospitalRequest.getPhoneNo(), result.getPhoneNo());
        verify(hospitalRepository, times(1)).save(any(Hospital.class));
    }

    @Test
    void testUpdateBloodBank_WithValidInputs_ShouldReturnUpdatedBloodBank() {
        // Arrange
        BloodBankRequest bloodBankRequest = new BloodBankRequest();
        bloodBankRequest.setBloodBankId(1);
        bloodBankRequest.setMailAddress("test@example.com");
        bloodBankRequest.setPassword("password123");

        BloodBank existingBank = new BloodBank();
        existingBank.setBloodBankId(1);

        User existingUser = new User();
        existingUser.setUserName("test@example.com");

        when(bloodBankRepository.findById(1)).thenReturn(Optional.of(existingBank));
        when(userRepository.findByUserName("test@example.com")).thenReturn(Optional.of(existingUser));
        when(passwordEncoder.encode("password123")).thenReturn("encodedPassword");
        when(userRepository.save(existingUser)).thenReturn(existingUser);
        when(bloodBankRepository.save(existingBank)).thenReturn(existingBank);

        // Act
        BloodBank updatedBank = bloodBankService.updateBloodBank(bloodBankRequest);

        // Assert
        assertNotNull(updatedBank);
        assertEquals(existingBank, updatedBank);
        assertEquals("encodedPassword", existingUser.getUserPassword());
        verify(bloodBankRepository).findById(1);
        verify(userRepository).findByUserName("test@example.com");
        verify(passwordEncoder).encode("password123");
        verify(userRepository).save(existingUser);
        verify(bloodBankRepository).save(existingBank);
    }

    @Test
    void testUpdateBloodBank_WithInvalidUserName_ShouldThrowException() {
        // Arrange
        BloodBankRequest bloodBankRequest = new BloodBankRequest();
        bloodBankRequest.setBloodBankId(1);
        bloodBankRequest.setMailAddress("test@example.com");
        bloodBankRequest.setPassword("password123");

        BloodBank existingBank = new BloodBank();
        existingBank.setBloodBankId(1);

        when(bloodBankRepository.findById(1)).thenReturn(Optional.of(existingBank));
        when(userRepository.findByUserName("test@example.com")).thenReturn(Optional.empty());

        // Act and Assert
        assertThrows(BloodBankNotFoundException.class, () -> bloodBankService.updateBloodBank(bloodBankRequest));
        verify(bloodBankRepository).findById(1);
        verify(userRepository).findByUserName("test@example.com");
        verify(passwordEncoder, never()).encode(anyString());
        verify(userRepository, never()).save(any(User.class));
        verify(bloodBankRepository, never()).save(any(BloodBank.class));
    }

    @Test
    public void testUpdateHospital_HospitalNotFound() {
        // Arrange
        HospitalRequest hospitalRequest = new HospitalRequest();
        hospitalRequest.setHospitalId(1);
        hospitalRequest.setHospitalName("Updated Hospital");
        hospitalRequest.setAddress("456 Main St");
        hospitalRequest.setPhoneNo(987654321);

        when(hospitalRepository.findById(hospitalRequest.getHospitalId())).thenReturn(Optional.empty());

        // Act and Assert
        assertThrows(HospitalNotFoundException.class, () -> hospitalService.updateHospital(hospitalRequest));
        verify(hospitalRepository, times(1)).findById(hospitalRequest.getHospitalId());
        verify(hospitalRepository, never()).save(any(Hospital.class));
    }

    @Test
    public void testViewHospital() {
        // Arrange
        int hospitalId = 1;

        Hospital hospital = new Hospital();
        hospital.setHospitalId(hospitalId);
        hospital.setHospitalName("Hospital 1");
        hospital.setAddress("123 Main St");
        hospital.setPhoneNo(1234567890);

        when(hospitalRepository.findById(hospitalId)).thenReturn(Optional.of(hospital));

        // Act
        Hospital result = hospitalService.getHospital(hospitalId);

        // Assert
        assertNotNull(result);
        assertEquals(hospitalId, result.getHospitalId());
        assertEquals(hospital.getHospitalName(), result.getHospitalName());
        assertEquals(hospital.getAddress(), result.getAddress());
        assertEquals(hospital.getPhoneNo(), result.getPhoneNo());
        verify(hospitalRepository, times(1)).findById(hospitalId);
    }

    @Test
    public void testViewHospital_HospitalNotFound() {
        // Arrange
        int hospitalId = 1;

        when(hospitalRepository.findById(hospitalId)).thenReturn(Optional.empty());

        // Act and Assert
        assertThrows(HospitalNotFoundException.class, () -> hospitalService.getHospital(hospitalId));
        verify(hospitalRepository, times(1)).findById(hospitalId);
    }

    @Test
    public void testDeleteHospital() {
        // Arrange
        int hospitalId = 1;

        Hospital hospital = new Hospital();
        hospital.setHospitalId(hospitalId);
        hospital.setHospitalName("Hospital 1");
        hospital.setAddress("123 Main St");
        hospital.setPhoneNo(1234567890);

        when(hospitalRepository.findById(hospitalId)).thenReturn(Optional.of(hospital));

        // Act
        String result = hospitalService.deleteHospital(hospitalId);

        // Assert
        assertEquals("Hospital Deleted", result);
        verify(hospitalRepository, times(1)).findById(hospitalId);
        verify(hospitalRepository, times(1)).deleteById(hospitalId);
    }

    @Test
    public void testDeleteHospital_HospitalNotFound() {
        // Arrange
        int hospitalId = 1;

        when(hospitalRepository.findById(hospitalId)).thenReturn(Optional.empty());

        // Act and Assert
        assertThrows(HospitalNotFoundException.class, () -> hospitalService.deleteHospital(hospitalId));
        verify(hospitalRepository, times(1)).findById(hospitalId);
        verify(hospitalRepository, never()).deleteById(hospitalId);
    }

    @Test
    public void testViewAllHospitals() {
        // Arrange
        List<Hospital> hospitals = new ArrayList<>();
        hospitals.add(new Hospital(1, "Hospital 1", "123 Main St","hos1@gmail.com", 1234567890,new ArrayList<>()));
        hospitals.add(new Hospital(2, "Hospital 2", "456 Main St","hos2@gmail.com" ,987654321, new ArrayList<>()));

        when(hospitalRepository.findAll()).thenReturn(hospitals);

        // Act
        List<Hospital> result = hospitalService.viewAllHospitals();

        // Assert
        assertNotNull(result);
        assertEquals(hospitals.size(), result.size());
        assertEquals(hospitals.get(0).getHospitalId(), result.get(0).getHospitalId());
        assertEquals(hospitals.get(0).getHospitalName(), result.get(0).getHospitalName());
        assertEquals(hospitals.get(0).getAddress(), result.get(0).getAddress());
        assertEquals(hospitals.get(0).getPhoneNo(), result.get(0).getPhoneNo());
        assertEquals(hospitals.get(1).getHospitalId(), result.get(1).getHospitalId());
        assertEquals(hospitals.get(1).getHospitalName(), result.get(1).getHospitalName());
        assertEquals(hospitals.get(1).getAddress(), result.get(1).getAddress());
        assertEquals(hospitals.get(1).getPhoneNo(), result.get(1).getPhoneNo());
        verify(hospitalRepository, times(1)).findAll();
    }
}
