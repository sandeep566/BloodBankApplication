package com.yp.BloodBankApplication.ServiceTests;

import com.yp.BloodBankApplication.Entity.BloodRequest;
import com.yp.BloodBankApplication.Entity.Hospital;
import com.yp.BloodBankApplication.Enums.BloodGroup;
import com.yp.BloodBankApplication.Enums.IsSupplied;
import com.yp.BloodBankApplication.Enums.Priority;
import com.yp.BloodBankApplication.Exception.BloodBankNotFoundException;
import com.yp.BloodBankApplication.Exception.BloodRequestNotFoundException;
import com.yp.BloodBankApplication.Exception.HospitalNotFoundException;
import com.yp.BloodBankApplication.Repository.BloodRequestRepository;
import com.yp.BloodBankApplication.Repository.HospitalRepository;
import com.yp.BloodBankApplication.Requests.BloodReqRequest;
import com.yp.BloodBankApplication.Services.BloodRequestService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class BloodRequestServiceTest {

    @Mock
    private BloodRequestRepository bloodRequestRepository;

    @Mock
    private HospitalRepository hospitalRepository;

    @InjectMocks
    private BloodRequestService bloodRequestService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddBloodRequest() {
        // Arrange
        BloodReqRequest bloodRequest = new BloodReqRequest();
        bloodRequest.setId(1);
        bloodRequest.setName("John Doe");
        bloodRequest.setAge(25);
        bloodRequest.setQuantity(2);

        int hospitalId = 1;
        BloodGroup bloodGroup = BloodGroup.AB_POSITIVE;
        Priority priority = Priority.HIGH;

        Hospital hospital = new Hospital();
        hospital.setHospitalId(hospitalId);
        hospital.setHospitalName("Hospital 1");

        when(hospitalRepository.findById(hospitalId)).thenReturn(Optional.of(hospital));
        when(bloodRequestRepository.save(any(BloodRequest.class))).thenReturn(new BloodRequest());

        // Act
        BloodRequest result = bloodRequestService.addBloodRequest(bloodRequest, hospitalId, bloodGroup, priority);

        // Assert
        assertNotNull(result);
        verify(hospitalRepository, times(1)).findById(hospitalId);
        verify(bloodRequestRepository, times(1)).save(any(BloodRequest.class));
    }

    @Test
    void testAddBloodRequest_HospitalNotFound() {
        // Arrange
        BloodReqRequest bloodRequest = new BloodReqRequest();
        bloodRequest.setId(1);
        bloodRequest.setName("John Doe");
        bloodRequest.setAge(25);
        bloodRequest.setQuantity(2);

        int hospitalId = 1;
        BloodGroup bloodGroup = BloodGroup.AB_POSITIVE;
        Priority priority = Priority.HIGH;

        when(hospitalRepository.findById(hospitalId)).thenReturn(Optional.empty());

        // Act and Assert
        assertThrows(HospitalNotFoundException.class, () ->
                bloodRequestService.addBloodRequest(bloodRequest, hospitalId, bloodGroup, priority));

        verify(hospitalRepository, times(1)).findById(hospitalId);
        verify(bloodRequestRepository, never()).save(any(BloodRequest.class));
    }

    @Test
    public void testUpdateBloodRequest_Success() {
        // Arrange
        int requestId = 1;

        BloodReqRequest bloodReqRequest = new BloodReqRequest();
        bloodReqRequest.setId(requestId);
        bloodReqRequest.setName("John Doe");
        bloodReqRequest.setAge(25);
        bloodReqRequest.setQuantity(2);

        BloodGroup bloodGroup = BloodGroup.AB_POSITIVE;
        Priority priority = Priority.HIGH;
        IsSupplied isSupplied = IsSupplied.YES;

        BloodRequest bloodRequest = new BloodRequest();
        bloodRequest.setBloodRequestId(requestId);
        bloodRequest.setPatientName("John Smith");
        bloodRequest.setAge(30);
        bloodRequest.setBloodGroup(BloodGroup.AB_NEGATIVE);
        bloodRequest.setPriority(Priority.LOW);
        bloodRequest.setQuantity(1);
        bloodRequest.setIsSupplied(IsSupplied.NO);

        when(bloodRequestRepository.findById(requestId)).thenReturn(Optional.of(bloodRequest));
        when(bloodRequestRepository.save(any(BloodRequest.class))).thenReturn(bloodRequest);

        // Act
        BloodRequest result = bloodRequestService.updateBloodRequest(bloodReqRequest, bloodGroup, priority, isSupplied);

        // Assert
        assertNotNull(result);
        assertEquals(bloodReqRequest.getName(), result.getPatientName());
        assertEquals(bloodReqRequest.getAge(), result.getAge());
        assertEquals(bloodGroup, result.getBloodGroup());
        assertEquals(priority, result.getPriority());
        assertEquals(bloodReqRequest.getQuantity(), result.getQuantity());
        assertEquals(isSupplied, result.getIsSupplied());

        verify(bloodRequestRepository, times(1)).findById(requestId);
        verify(bloodRequestRepository, times(1)).save(any(BloodRequest.class));
    }


    @Test
    void testUpdateBloodRequest_BloodRequestNotFound() {
        // Arrange
        BloodReqRequest bloodReqRequest = new BloodReqRequest();
        bloodReqRequest.setId(1);
        bloodReqRequest.setName("John Doe");
        bloodReqRequest.setAge(25);
        bloodReqRequest.setQuantity(2);

        BloodGroup bloodGroup = BloodGroup.AB_POSITIVE;
        Priority priority = Priority.HIGH;
        IsSupplied isSupplied = IsSupplied.YES;

        when(bloodRequestRepository.findById(bloodReqRequest.getId())).thenReturn(Optional.empty());

        // Act and Assert
        assertThrows(BloodRequestNotFoundException.class, () ->
                bloodRequestService.updateBloodRequest(bloodReqRequest, bloodGroup, priority, isSupplied));

        verify(bloodRequestRepository, times(1)).findById(bloodReqRequest.getId());
        verify(bloodRequestRepository, never()).save(any(BloodRequest.class));
    }

    @Test
    void testDeleteBloodRequest() {
        // Arrange
        int id = 1;

        BloodRequest bloodRequest = new BloodRequest();
        bloodRequest.setBloodRequestId(id);

        when(bloodRequestRepository.findById(id)).thenReturn(Optional.of(bloodRequest));

        // Act
        String result = bloodRequestService.deleteBloodRequest(id);

        // Assert
        assertEquals("Blood Request Deleted", result);
        verify(bloodRequestRepository, times(1)).findById(id);
        verify(bloodRequestRepository, times(1)).deleteById(id);
    }

    @Test
    void testDeleteBloodRequest_BloodRequestNotFound() {
        // Arrange
        int id = 1;

        when(bloodRequestRepository.findById(id)).thenReturn(Optional.empty());

        // Act and Assert
        assertThrows(BloodRequestNotFoundException.class, () ->
                bloodRequestService.deleteBloodRequest(id));

        verify(bloodRequestRepository, times(1)).findById(id);
        verify(bloodRequestRepository, never()).deleteById(id);
    }

    @Test
    void testGetAllBloodRequests() {
        // Arrange
        List<BloodRequest> bloodRequests = new ArrayList<>();
        bloodRequests.add(new BloodRequest());
        bloodRequests.add(new BloodRequest());

        when(bloodRequestRepository.findAll()).thenReturn(bloodRequests);

        // Act
        List<BloodRequest> result = bloodRequestService.getAllBloodRequests();

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        verify(bloodRequestRepository, times(1)).findAll();
    }

    @Test
    void testViewBloodRequest() {
        // Arrange
        int requestId = 1;

        BloodRequest bloodRequest = new BloodRequest();
        bloodRequest.setBloodRequestId(requestId);

        when(bloodRequestRepository.findById(requestId)).thenReturn(Optional.of(bloodRequest));

        // Act
        BloodRequest result = bloodRequestService.viewBloodRequest(requestId);

        // Assert
        assertNotNull(result);
        assertEquals(requestId, result.getBloodRequestId());
        verify(bloodRequestRepository, times(1)).findById(requestId);
    }

    @Test
    void testViewBloodRequest_BloodRequestNotFound() {
        // Arrange
        int requestId = 1;

        when(bloodRequestRepository.findById(requestId)).thenReturn(Optional.empty());

        // Act and Assert
        assertThrows(BloodBankNotFoundException.class, () ->
                bloodRequestService.viewBloodRequest(requestId));

        verify(bloodRequestRepository, times(1)).findById(requestId);
    }

    @Test
    void testViewBloodRequestByHospitalId() {
        // Arrange
        int hospitalId = 1;

        Hospital hospital = new Hospital();
        hospital.setHospitalId(hospitalId);
        hospital.setHospitalName("Hospital 1");

        List<BloodRequest> bloodRequests = new ArrayList<>();
        bloodRequests.add(new BloodRequest());
        bloodRequests.add(new BloodRequest());

        hospital.setBloodRequests(bloodRequests);

        when(hospitalRepository.findById(hospitalId)).thenReturn(Optional.of(hospital));

        // Act
        List<BloodRequest> result = bloodRequestService.viewBloodRequestByHospitalId(hospitalId);

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        verify(hospitalRepository, times(1)).findById(hospitalId);
    }

    @Test
    void testViewBloodRequestByHospitalId_HospitalNotFound() {
        // Arrange
        int hospitalId = 1;

        when(hospitalRepository.findById(hospitalId)).thenReturn(Optional.empty());

        // Act and Assert
        assertThrows(HospitalNotFoundException.class, () ->
                bloodRequestService.viewBloodRequestByHospitalId(hospitalId));

        verify(hospitalRepository, times(1)).findById(hospitalId);
    }
}
