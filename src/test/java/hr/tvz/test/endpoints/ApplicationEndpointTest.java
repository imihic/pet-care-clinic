package hr.tvz.test.endpoints;

import hr.tvz.application.dto.ApplicationDTO;
import hr.tvz.application.endpoint.ApplicationEndpoint;
import hr.tvz.application.services.ApplicationService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ApplicationEndpointTest {

    @Mock
    private ApplicationService applicationService;

    @InjectMocks
    private ApplicationEndpoint applicationEndpoint;

    @Test
    public void testSaveApplication() {
        ApplicationDTO applicationDTO = new ApplicationDTO();
        applicationDTO.setStatus("PENDING");
        when(applicationService.saveApplication(any(ApplicationDTO.class))).thenReturn(applicationDTO);

        ApplicationDTO result = applicationEndpoint.saveApplication(applicationDTO);

        assertNotNull(result);
        assertEquals("PENDING", result.getStatus());
        verify(applicationService, times(1)).saveApplication(any(ApplicationDTO.class));
    }

    @Test
    public void testGetAllApplications() {
        ApplicationDTO applicationDTO = new ApplicationDTO();
        when(applicationService.getAllApplications()).thenReturn(Collections.singletonList(applicationDTO));

        List<ApplicationDTO> result = applicationEndpoint.getAllApplications();

        assertEquals(1, result.size());
        verify(applicationService, times(1)).getAllApplications();
    }

    @Test
    public void testGetApplicationById() {
        ApplicationDTO applicationDTO = new ApplicationDTO();
        applicationDTO.setId(1L);
        when(applicationService.getApplicationById(1L)).thenReturn(Optional.of(applicationDTO));

        Optional<ApplicationDTO> result = applicationEndpoint.getApplicationById(1L);

        assertTrue(result.isPresent());
        assertEquals(1L, result.get().getId());
        verify(applicationService, times(1)).getApplicationById(1L);
    }

    @Test
    public void testGetApplicationsByUser() {
        ApplicationDTO applicationDTO = new ApplicationDTO();
        when(applicationService.getApplicationsByUser(1L)).thenReturn(Collections.singletonList(applicationDTO));

        List<ApplicationDTO> result = applicationEndpoint.getApplicationsByUser(1L);

        assertEquals(1, result.size());
        verify(applicationService, times(1)).getApplicationsByUser(1L);
    }

    @Test
    public void testApproveApplication() {
        doNothing().when(applicationService).approveApplication(1L);

        applicationEndpoint.approveApplication(1L);

        verify(applicationService, times(1)).approveApplication(1L);
    }

    @Test
    public void testRejectApplication() {
        doNothing().when(applicationService).rejectApplication(1L);

        applicationEndpoint.rejectApplication(1L);

        verify(applicationService, times(1)).rejectApplication(1L);
    }
}
