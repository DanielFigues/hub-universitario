
package br.edu.hub;

import br.edu.hub.dto.ActivityResponse;
import br.edu.hub.dto.ActivityUpdateRequest;
import br.edu.hub.dto.DashBoardMetrics;
import br.edu.hub.entity.Activity;
import br.edu.hub.entity.ActivityCategory;
import br.edu.hub.entity.ActivityStatus;
import br.edu.hub.repository.ActivityRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import br.edu.hub.service.ActivityService;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class ActivityServiceTest {

    @Autowired
    private ActivityService activityService;

    @Autowired
    private ActivityRepository activityRepository;

    @Test
    void shouldListAllAndSearchActivities() {
        
        activityRepository.save(new Activity("Java Workshop", "Aprenda Java", 
                ActivityCategory.WORKSHOP, ActivityStatus.OPEN, 20, 0, "Org", "Lab 1", LocalDateTime.now().plusDays(1)));

        List<ActivityResponse> all = activityService.list(null);
        assertFalse(all.isEmpty());

        List<ActivityResponse> filtered = activityService.list("Java");
        assertEquals(1, filtered.size());
    }

    @Test
    void shouldUpdateActivitySuccessfully() {

        Activity act = activityRepository.save(new Activity("Titulo Antigo", "Desc", 
                ActivityCategory.COURSE, ActivityStatus.OPEN, 10, 0, "Org", "Sala 2", LocalDateTime.now().plusDays(2)));

        ActivityUpdateRequest updateRequest = new ActivityUpdateRequest("Novo Titulo", "Nova Desc", 
                ActivityCategory.EVENT, ActivityStatus.FULL, 15, "Novo Org", "Nova Sala", LocalDateTime.now().plusDays(5));

        ActivityResponse updated = activityService.update(act.getId(), updateRequest);

        assertEquals("Novo Titulo", updated.title());
        assertEquals(ActivityStatus.FULL, updated.status());
    }

    @Test
    void shouldReturnDashboardMetrics() {
        DashBoardMetrics metrics = activityService.getDashboardMetrics();
        assertNotNull(metrics);
    }
}