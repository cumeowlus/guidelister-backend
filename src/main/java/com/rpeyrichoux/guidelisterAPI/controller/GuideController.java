package com.rpeyrichoux.guidelisterAPI.controller;

import com.rpeyrichoux.guidelisterAPI.model.Activity;
import com.rpeyrichoux.guidelisterAPI.model.Guide;
import com.rpeyrichoux.guidelisterAPI.model.User;
import com.rpeyrichoux.guidelisterAPI.view.GuideView;
import com.rpeyrichoux.guidelisterAPI.view.UserView;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/api/guides")
public class GuideController {
    private final GuideView guideView;
    private final UserView userView;

    public GuideController(GuideView guideView, UserView userView) {
        this.userView = userView;
        this.guideView = guideView;
    }

    @GetMapping(produces = "application/json")
    public List<Guide> getGuides() {
        return guideView.getGuides();
    }

    @GetMapping(path = "/{id}", produces = "application/json")
    public Optional<Guide> getGuideById(@PathVariable Long id) {
        return guideView.getGuideById(id);
    }

    @GetMapping(path = "/{guideId}/users", produces = "application/json")
    public List<User> getGuideUsers(@PathVariable Long guideId) {
        return userView.getGuideUsers(guideId);
    }

    @GetMapping(path = "/user/{id}", produces = "application/json")
    public List<Guide> getUserGuides(@PathVariable Long id) {
        return guideView.getUserGuides(id);
    }

    @PostMapping("/new")
    public ResponseEntity<Guide> addGuide(@RequestBody Guide newGuide){
        guideView.addGuide(newGuide);
        return ResponseEntity.ok(newGuide);
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

    @DeleteMapping("/{guideId}/users/{userId}")
    public ResponseEntity<Void> deleteUserFromGuide(@PathVariable Long guideId, @PathVariable Long userId) {
        guideView.deleteUserFromGuide(guideId, userId);
        return ResponseEntity.noContent().build();
    }

}
