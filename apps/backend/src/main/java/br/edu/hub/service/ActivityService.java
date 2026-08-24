package br.edu.hub.service;

import br.edu.hub.dto.ActivityResponse;
import br.edu.hub.dto.ActivityUpdateRequest;
import br.edu.hub.dto.DashBoardMetrics;
import br.edu.hub.entity.Activity;
import br.edu.hub.entity.ActivityStatus;
import br.edu.hub.repository.ActivityRepository;
import br.edu.hub.repository.RegistrationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ActivityService {
    private final ActivityRepository activityRepository;
    private final RegistrationRepository registrationRepository; 

public ActivityService(ActivityRepository activityRepository, RegistrationRepository registrationRepository) {
        this.activityRepository = activityRepository;
        this.registrationRepository = registrationRepository;
    }

    @Transactional(readOnly = true)
    public List<ActivityResponse> list(String search) {
    List<Activity> activities;

    if (search == null || search.trim().isEmpty()) {
        activities = activityRepository.findAllByOrderByDateDesc();
    } else {
    activities = activityRepository.findByTitleContainingIgnoreCaseOrDescriptionContainingIgnoreCaseOrderByDateDesc(search, search); 
    }
    return activities.stream()
            .map(ActivityResponse::from)
            .toList();
}

    @Transactional(readOnly = true)
    public ActivityResponse findById(Long id) {
        return ActivityResponse.from(requireActivity(id));
    }

    @Transactional
    public ActivityResponse update(Long id, ActivityUpdateRequest request) {
        Activity activity = requireActivity(id);
        if (request.title() != null) activity.setTitle(request.title());
        if (request.description() != null) activity.setDescription(request.description());
        if (request.category() != null) activity.setCategory(request.category());
        if (request.status() != null) activity.setStatus(request.status());
        if (request.capacity() != null) activity.setCapacity(request.capacity());
        if (request.organizer() != null) activity.setOrganizer(request.organizer());
        if (request.location() != null) activity.setLocation(request.location());
        if (request.date() != null) activity.setDate(request.date());
        return ActivityResponse.from(activityRepository.save(activity));
    }

    public Activity requireActivity(Long id) {
        return activityRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Activity not found"));
    }

    @Transactional(readOnly = true)
    public DashBoardMetrics getDashboardMetrics() {
        long totalActivities = activityRepository.count();
        long totalRegistrations = registrationRepository.count();
        long openActivities = activityRepository.countByStatus(ActivityStatus.OPEN);
        long fullActivities = activityRepository.countByStatus(ActivityStatus.FULL);

        return new DashBoardMetrics(
                totalActivities, 
                totalRegistrations, 
                openActivities, 
                fullActivities
        );
    }
}