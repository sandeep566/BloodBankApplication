package com.yp.BloodBankApplication.ServiceTests;


import com.yp.BloodBankApplication.Entity.BloodBank;
import com.yp.BloodBankApplication.Entity.Donor;
import com.yp.BloodBankApplication.Enums.BloodGroup;
import com.yp.BloodBankApplication.Exception.BloodBankNotFoundException;
import com.yp.BloodBankApplication.Repository.BloodBankRepository;
import com.yp.BloodBankApplication.Requests.BloodBankRequest;
import com.yp.BloodBankApplication.Services.BloodBankService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BloodBankServiceTest {

    @Mock
    private BloodBankRepository bloodBankRepository;

    @InjectMocks
    private BloodBankService bloodBankService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testRegisterBloodBank() {
        // Create a BloodBankRequest object with the desired values
        BloodBankRequest bloodBankRequest = new BloodBankRequest();
        bloodBankRequest.setBloodBankId(1);
        bloodBankRequest.setBloodBankName("Test Blood Bank");
        bloodBankRequest.setAddress("123 Main Street");
        bloodBankRequest.setPhoneNo(123456789);
        bloodBankRequest.setMailAddress("test@test.com");

        // Create a mock BloodBank object
        BloodBank expectedBloodBank = new BloodBank();
        expectedBloodBank.setBloodBankId(1);
        expectedBloodBank.setBloodBankName("Test Blood Bank");
        expectedBloodBank.setAddress("123 Main Street");
        expectedBloodBank.setPhoneNumber(123456789);
        expectedBloodBank.setMailAddress("test@test.com");
        expectedBloodBank.setBloodGroups(new HashMap<>());

        // Configure the bloodBankRepository mock
        when(bloodBankRepository.save(any(BloodBank.class))).thenReturn(expectedBloodBank);

        // Call the registerBloodBank method
        BloodBank actualBloodBank = bloodBankService.registerBloodBank(bloodBankRequest);

        // Assert that the actualBloodBank matches the expectedBloodBank
        assertEquals(expectedBloodBank.getBloodBankId(), actualBloodBank.getBloodBankId());
        assertEquals(expectedBloodBank.getBloodBankName(), actualBloodBank.getBloodBankName());
        assertEquals(expectedBloodBank.getAddress(), actualBloodBank.getAddress());
        assertEquals(expectedBloodBank.getPhoneNumber(), actualBloodBank.getPhoneNumber());
        assertEquals(expectedBloodBank.getMailAddress(), actualBloodBank.getMailAddress());
        assertEquals(expectedBloodBank.getBloodGroups(), actualBloodBank.getBloodGroups());
    }

    @Test
    void testUpdateBloodBank() {
        // Prepare test data
        BloodBankRequest bloodBankRequest = new BloodBankRequest();
        bloodBankRequest.setBloodBankId(1);
        bloodBankRequest.setBloodBankName("Updated Blood Bank");
        bloodBankRequest.setAddress("Updated Address");
        bloodBankRequest.setPhoneNo(987654321);
        bloodBankRequest.setMailAddress("updated@test.com");

        // Mock repository behavior
        BloodBank existingBloodBank = new BloodBank();
        when(bloodBankRepository.findById(bloodBankRequest.getBloodBankId())).thenReturn(Optional.of(existingBloodBank));
        when(bloodBankRepository.save(any(BloodBank.class))).thenReturn(existingBloodBank);

        // Perform the update
        BloodBank updatedBloodBank = bloodBankService.updateBloodBank(bloodBankRequest);

        // Verify the repository methods were called with the correct arguments
        verify(bloodBankRepository).findById(bloodBankRequest.getBloodBankId());
        verify(bloodBankRepository).save(any(BloodBank.class));

        // Assert the result
        assertNotNull(updatedBloodBank);
        assertEquals(bloodBankRequest.getBloodBankName(), updatedBloodBank.getBloodBankName());
        assertEquals(bloodBankRequest.getAddress(), updatedBloodBank.getAddress());
        assertEquals(bloodBankRequest.getPhoneNo(), updatedBloodBank.getPhoneNumber());
        assertEquals(bloodBankRequest.getMailAddress(), updatedBloodBank.getMailAddress());
    }

    @Test
    void testUpdateBloodBank_ThrowsBloodBankNotFoundException() {
        // Prepare test data
        BloodBankRequest bloodBankRequest = new BloodBankRequest();
        bloodBankRequest.setBloodBankId(1);

        // Mock repository behavior
        when(bloodBankRepository.findById(bloodBankRequest.getBloodBankId())).thenReturn(Optional.empty());

        // Perform the update and assert that BloodBankNotFoundException is thrown
        assertThrows(BloodBankNotFoundException.class, () -> bloodBankService.updateBloodBank(bloodBankRequest));

        // Verify the repository method was called with the correct argument
        verify(bloodBankRepository).findById(bloodBankRequest.getBloodBankId());
    }

    @Test
    void testViewBloodBank_ValidId() {
        // Create a mock BloodBank object
        BloodBank bloodBank = new BloodBank();
        bloodBank.setBloodBankId(1);
        bloodBank.setBloodBankName("Test Blood Bank");
        bloodBank.setAddress("123 Main Street");
        bloodBank.setPhoneNumber(1234567890);
        bloodBank.setMailAddress("test@test.com");

        // Configure the bloodBankRepository mock
        when(bloodBankRepository.findById(1)).thenReturn(Optional.of(bloodBank));

        // Call the viewBloodBank method
        BloodBank result = bloodBankService.viewBloodBank(1);

        // Assert that the result is not null and matches the expected BloodBank
        assertNotNull(result);
        assertEquals(bloodBank.getBloodBankId(), result.getBloodBankId());
        assertEquals(bloodBank.getBloodBankName(), result.getBloodBankName());
        assertEquals(bloodBank.getAddress(), result.getAddress());
        assertEquals(bloodBank.getPhoneNumber(), result.getPhoneNumber());
        assertEquals(bloodBank.getMailAddress(), result.getMailAddress());
    }

    @Test
    void testViewBloodBank_InvalidId() {
        // Configure the bloodBankRepository mock to return an empty Optional
        when(bloodBankRepository.findById(2)).thenReturn(Optional.empty());

        // Call the viewBloodBank method and assert that it throws BloodBankNotFoundException
        assertThrows(BloodBankNotFoundException.class, () -> bloodBankService.viewBloodBank(2));
    }

    @Test
    void testViewAllBloodBanks() {
        // Create mock BloodBank objects
        BloodBank bloodBank1 = new BloodBank();
        bloodBank1.setBloodBankId(1);
        bloodBank1.setBloodBankName("Blood Bank 1");

        BloodBank bloodBank2 = new BloodBank();
        bloodBank2.setBloodBankId(2);
        bloodBank2.setBloodBankName("Blood Bank 2");

        List<BloodBank> expectedBloodBanks = Arrays.asList(bloodBank1, bloodBank2);

        // Configure the bloodBankRepository mock
        when(bloodBankRepository.findAll()).thenReturn(expectedBloodBanks);

        // Call the viewAllBloodBanks method
        List<BloodBank> result = bloodBankService.viewAllBloodBanks();

        // Assert that the result is not null and matches the expected BloodBanks
        assertEquals(expectedBloodBanks.size(), result.size());
        for (int i = 0; i < expectedBloodBanks.size(); i++) {
            BloodBank expectedBloodBank = expectedBloodBanks.get(i);
            BloodBank actualBloodBank = result.get(i);
            assertEquals(expectedBloodBank.getBloodBankId(), actualBloodBank.getBloodBankId());
            assertEquals(expectedBloodBank.getBloodBankName(), actualBloodBank.getBloodBankName());
        }
    }


    @Test
    void testDeleteBloodBank_ExistingBloodBank() {
        int bloodBankId = 1;

        // Create a mock BloodBank object
        BloodBank bloodBank = new BloodBank();
        bloodBank.setBloodBankId(bloodBankId);

        // Configure the bloodBankRepository mock
        when(bloodBankRepository.findById(bloodBankId)).thenReturn(Optional.of(bloodBank));

        // Call the deleteBloodBank method
        String result = bloodBankService.deleteBloodBank(bloodBankId);

        // Verify that bloodBankRepository.deleteById was called with the correct blood bank ID
        verify(bloodBankRepository, times(1)).deleteById(bloodBankId);

        // Assert the return value
        assertEquals("Blood Bank deleted", result);
    }

    @Test
    void testDeleteBloodBank_NonExistingBloodBank() {
        int bloodBankId = 1;

        // Configure the bloodBankRepository mock to return an empty Optional
        when(bloodBankRepository.findById(bloodBankId)).thenReturn(Optional.empty());

        // Call the deleteBloodBank method and assert that it throws a BloodBankNotFoundException
        assertThrows(BloodBankNotFoundException.class, () -> bloodBankService.deleteBloodBank(bloodBankId));

        // Verify that bloodBankRepository.deleteById was not called
        verify(bloodBankRepository, never()).deleteById(anyInt());
    }

    @Test
    public void testGetAllDonors_WithExistingBloodBankId_ReturnsListOfDonors() {
        // Arrange
        int bloodBankId = 1;
        List<Donor> expectedDonors = new ArrayList<>();
        expectedDonors.add(new Donor());
        expectedDonors.add(new Donor());
        BloodBank bloodBank = new BloodBank();
        bloodBank.setDonorList(expectedDonors);

        when(bloodBankRepository.findById(bloodBankId)).thenReturn(Optional.of(bloodBank));

        // Act
        List<Donor> result = bloodBankService.getAllDonors(bloodBankId);

        // Assert
        assertEquals(expectedDonors, result);
    }

    @Test
    public void testGetAllDonors_WithNonExistingBloodBankId_ThrowsBloodBankNotFoundException() {
        // Arrange
        int bloodBankId = 1;

        when(bloodBankRepository.findById(bloodBankId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(BloodBankNotFoundException.class, () -> bloodBankService.getAllDonors(bloodBankId));
    }


    @Test
    public void testViewBloodGroupAndQuantity_WithExistingBloodBankId_ReturnsBloodGroupAndQuantityMap() {
        // Arrange
        int bloodBankId = 1;
        Map<BloodGroup, Integer> expectedBloodGroups = new HashMap<>();
        expectedBloodGroups.put(BloodGroup.A_POSITIVE, 10);
        expectedBloodGroups.put(BloodGroup.B_POSITIVE, 5);
        BloodBank bloodBank = new BloodBank();
        bloodBank.setBloodGroups(expectedBloodGroups);

        when(bloodBankRepository.findById(bloodBankId)).thenReturn(Optional.of(bloodBank));

        // Act
        Map<BloodGroup, Integer> result = bloodBankService.viewBloodGroupAndQuantity(bloodBankId);

        // Assert
        assertEquals(expectedBloodGroups, result);
    }

    @Test
    public void testViewBloodGroupAndQuantity_WithNonExistingBloodBankId_ThrowsBloodBankNotFoundException() {
        // Arrange
        int bloodBankId = 1;

        when(bloodBankRepository.findById(bloodBankId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(BloodBankNotFoundException.class, () -> bloodBankService.viewBloodGroupAndQuantity(bloodBankId));
    }
}

