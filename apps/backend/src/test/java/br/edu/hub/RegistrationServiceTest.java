package br.edu.hub;

import br.edu.hub.entity.Activity;
import br.edu.hub.entity.Registration;
import br.edu.hub.repository.ActivityRepository;
import br.edu.hub.repository.RegistrationRepository;
import br.edu.hub.service.RegistrationService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class RegistrationServiceTest {

    @Mock
    private RegistrationRepository registrationRepository;

    @Mock
    private ActivityRepository activityRepository;

    @InjectMocks
    private RegistrationService registrationService;

    @Test
    @DisplayName("Deve cancelar a inscrição e devolver a vaga com sucesso")
    void cancelRegistration_Success() {
        Long activityId = 1L;
        String studentEmail = "aluno@teste.com";
        
        Activity activity = Mockito.mock(Activity.class);
        Registration registration = new Registration(activity, "Aluno", studentEmail);
        
        Mockito.when(registrationRepository.findByActivityIdAndStudentEmail(activityId, studentEmail))
                .thenReturn(Optional.of(registration));

        registrationService.cancelRegistration(activityId, studentEmail);

        Mockito.verify(activityRepository).save(activity);
        Mockito.verify(registrationRepository).delete(registration);
    }
}