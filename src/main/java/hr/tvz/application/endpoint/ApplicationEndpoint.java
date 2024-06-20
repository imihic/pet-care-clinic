package hr.tvz.application.endpoint;

import com.vaadin.hilla.Endpoint;
import hr.tvz.application.dto.ApplicationDTO;
import hr.tvz.application.services.ApplicationService;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

@Endpoint
@AnonymousAllowed
public class ApplicationEndpoint {

    private final ApplicationService applicationService;

    @Autowired
    public ApplicationEndpoint(ApplicationService applicationService) {
        this.applicationService = applicationService;
    }

    public ApplicationDTO saveApplication(ApplicationDTO applicationDTO) {
        return applicationService.saveApplication(applicationDTO);
    }

    public List<ApplicationDTO> getAllApplications() {
        return applicationService.getAllApplications();
    }

    public Optional<ApplicationDTO> getApplicationById(Long id) {
        return applicationService.getApplicationById(id);
    }

    public List<ApplicationDTO> getApplicationsByUser(Long userId) {
        return applicationService.getApplicationsByUser(userId);
    }

    public void approveApplication(Long id) {
        applicationService.approveApplication(id);
    }

    public void rejectApplication(Long id) {
        applicationService.rejectApplication(id);
    }
}
