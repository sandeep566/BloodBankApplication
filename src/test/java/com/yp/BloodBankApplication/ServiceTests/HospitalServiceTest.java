package com.yp.BloodBankApplication.ServiceTests;

import com.yp.BloodBankApplication.Entity.Hospital;
import com.yp.BloodBankApplication.Exception.HospitalNotFoundException;
import com.yp.BloodBankApplication.Repository.HospitalRepository;
import com.yp.BloodBankApplication.Requests.HospitalRequest;
import com.yp.BloodBankApplication.Services.HospitalService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;

public class HospitalServiceTest {

    @Mock
    private HospitalRepository hospitalRepository;

    @InjectMocks
    private HospitalService hospitalService;

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
    public void testUpdateHospital() {
        // Arrange
        HospitalRequest hospitalRequest = new HospitalRequest();
        hospitalRequest.setHospitalId(1);
        hospitalRequest.setHospitalName("Updated Hospital");
        hospitalRequest.setAddress("456 Main St");
        hospitalRequest.setPhoneNo(987654321);

        Hospital existingHospital = new Hospital();
        existingHospital.setHospitalId(hospitalRequest.getHospitalId());
        existingHospital.setHospitalName("Hospital 1");
        existingHospital.setAddress("123 Main St");
        existingHospital.setPhoneNo(1234567890);

        Hospital updatedHospital = new Hospital();
        updatedHospital.setHospitalId(hospitalRequest.getHospitalId());
        updatedHospital.setHospitalName(hospitalRequest.getHospitalName());
        updatedHospital.setAddress(hospitalRequest.getAddress());
        updatedHospital.setPhoneNo(hospitalRequest.getPhoneNo());

        when(hospitalRepository.findById(hospitalRequest.getHospitalId())).thenReturn(Optional.of(existingHospital));
        when(hospitalRepository.save(any(Hospital.class))).thenReturn(updatedHospital);

        // Act
        Hospital result = hospitalService.updateHospital(hospitalRequest);

        // Assert
        assertNotNull(result);
        assertEquals(hospitalRequest.getHospitalId(), result.getHospitalId());
        assertEquals(hospitalRequest.getHospitalName(), result.getHospitalName());
        assertEquals(hospitalRequest.getAddress(), result.getAddress());
        assertEquals(hospitalRequest.getPhoneNo(), result.getPhoneNo());
        verify(hospitalRepository, times(1)).findById(hospitalRequest.getHospitalId());
        verify(hospitalRepository, times(1)).save(any(Hospital.class));
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
        hospitals.add(new Hospital(1, "Hospital 1", "123 Main St", 1234567890,new ArrayList<>()));
        hospitals.add(new Hospital(2, "Hospital 2", "456 Main St", 987654321, new ArrayList<>()));

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
