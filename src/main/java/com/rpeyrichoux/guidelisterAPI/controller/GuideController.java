package com.rpeyrichoux.guidelisterAPI.controller;

import com.rpeyrichoux.guidelisterAPI.model.Activity;
import com.rpeyrichoux.guidelisterAPI.model.Guide;
import com.rpeyrichoux.guidelisterAPI.view.GuideView;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/guides")
public class GuideController {
    private final GuideView guideView;

    public GuideController(GuideView guideView) {
        this.guideView = guideView;
    }

    @GetMapping(produces = "application/json")
    public List<Guide> getGuides() {
        return guideView.getGuides();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGuide(@PathVariable Long id) {
        guideView.deleteGuide(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Guide> editGuide(@PathVariable Long id, @RequestBody Guide updated) {
        Guide g = guideView.editGuide(id, updated);
        if (g == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(g);
    }

    @PostMapping("/{guideId}/activities")
    public ResponseEntity<Activity> addActivity(@PathVariable Long guideId, @RequestBody Activity activityToAdd) {
        Activity addedActivity = guideView.addActivity(guideId, activityToAdd);
        return ResponseEntity.ok(addedActivity);
    }

    @DeleteMapping("/{guideId}/activities/{ActivityId}")
    public ResponseEntity<Void> deleteActivity(@PathVariable Long guideId, @PathVariable Long ActivityId) {
        guideView.deleteActivity(guideId, ActivityId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{guideId}/users/{userId}")
    public ResponseEntity<Guide> addUserToGuide(@PathVariable Long guideId, @PathVariable Long userId) {
        Guide updatedGuide = guideView.addUserToGuide(guideId, userId);
        return ResponseEntity.ok(updatedGuide);
    }
}
