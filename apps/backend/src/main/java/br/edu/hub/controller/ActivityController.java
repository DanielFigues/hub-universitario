package br.edu.hub.controller;

import br.edu.hub.dto.ActivityResponse;
import br.edu.hub.dto.ActivityUpdateRequest;
import br.edu.hub.dto.DashBoardMetrics;
import br.edu.hub.service.ActivityService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/activities")
public class ActivityController {
    private final ActivityService activityService;

    public ActivityController(ActivityService activityService) {
        this.activityService = activityService;
    }

    @GetMapping("/dashboard/metrics")
    public DashBoardMetrics getDashboardMetrics() {
        return activityService.getDashboardMetrics();
    }

    @GetMapping
    public List<ActivityResponse> list(@RequestParam(required = false) String search) {
        return activityService.list(search);
    }

    @GetMapping("/{id}")
    public ActivityResponse findById(@PathVariable Long id) {
        return activityService.findById(id);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ActivityResponse> update(@PathVariable Long id,
                                                   @Valid @RequestBody ActivityUpdateRequest request) {
        return ResponseEntity.ok(activityService.update(id, request));
    }
}
