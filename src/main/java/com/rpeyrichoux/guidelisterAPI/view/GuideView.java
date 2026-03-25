package com.rpeyrichoux.guidelisterAPI.view;

import com.rpeyrichoux.guidelisterAPI.model.*;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class GuideView {
    private final GuideRepository guideRepo;
    private final ActivityRepository activityRepo;
    private final UserRepository userRepo;

    public GuideView(GuideRepository guideRepo, ActivityRepository activityRepo, UserRepository userRepo) {
        this.guideRepo = guideRepo;
        this.activityRepo = activityRepo;
        this.userRepo = userRepo;
    }

    public java.util.List<Guide> getGuides() {
        return guideRepo.findAll();
    }

    public void deleteGuide(Long id) {
        guideRepo.deleteById(id);
    }

    public Guide editGuide(Long id, Guide updated) {
        Optional<Guide> opt = guideRepo.findById(id);
        if (opt.isEmpty()) return null;
        Guide guide = opt.get();
        guide.setTitre(updated.getTitre());
        guide.setDescription(updated.getDescription());
        guide.setNbJour(updated.getNbJour());
        guide.setMobilite(updated.getMobilite());
        guide.setSaison(updated.getSaison());
        guide.setPublicCible(updated.getPublicCible());
        return guideRepo.save(guide);
    }

    public Activity addActivity(Long guideId, @org.jetbrains.annotations.UnknownNullability Activity activite) {
        Guide guide = guideRepo.findById(guideId).orElseThrow();
        activite.setGuide(guide);
        return activityRepo.save(activite);
    }

    public void deleteActivity(Long guideId, Long activiteId) {
        Activity activity = activityRepo.findById(activiteId).orElseThrow();
        if (activity.getGuide() != null && activity.getGuide().getId().equals(guideId)) {
            activityRepo.deleteById(activiteId);
        } else {
            throw new IllegalArgumentException("Activity not attached to guide");
        }
    }

    public Guide addUserToGuide(Long guideId, Long userId) {
        Guide guide = guideRepo.findById(guideId).orElseThrow();
        User user = userRepo.findById(userId).orElseThrow();
        guide.getAuthorizedUsers().add(user);
        return guideRepo.save(guide);
    }
}
