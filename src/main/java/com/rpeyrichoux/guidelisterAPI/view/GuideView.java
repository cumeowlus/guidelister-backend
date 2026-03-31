package com.rpeyrichoux.guidelisterAPI.view;

import com.rpeyrichoux.guidelisterAPI.model.*;
import com.rpeyrichoux.guidelisterAPI.repositories.ActivityRepository;
import com.rpeyrichoux.guidelisterAPI.repositories.GuideRepository;
import com.rpeyrichoux.guidelisterAPI.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GuideView {

    @Autowired
    private final GuideRepository guideRepo;
    @Autowired
    private final ActivityRepository activityRepo;
    @Autowired
    private final UserRepository userRepo;

    public GuideView(GuideRepository guideRepo, ActivityRepository activityRepo, UserRepository userRepo) {
        this.guideRepo = guideRepo;
        this.activityRepo = activityRepo;
        this.userRepo = userRepo;
    }

    public List<Guide> getGuides() {
        return guideRepo.findAll();
    }

    public Optional<Guide> getGuideById(Long id) {
        return guideRepo.findById(id);
    }

    public List<Guide> getUserGuides(Long id) {
        return guideRepo.findByAuthorizedUsers_Id(id);
    }

    public Guide addGuide(Guide newGuide) {
        return guideRepo.save(newGuide);
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

    public Activity addActivity(Long guideId, Activity activite) {
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

    public void deleteUserFromGuide(Long guideId, Long userId) {
        Guide guide = guideRepo.findById(guideId).orElseThrow();
        User user = userRepo.findById(userId).orElseThrow();
        guide.getAuthorizedUsers().remove(user);
        guideRepo.save(guide);
    }

    public Guide addUserToGuide(Long guideId, Long userId) {
        Guide guide = guideRepo.findById(guideId).orElseThrow();
        User user = userRepo.findById(userId).orElseThrow();
        guide.getAuthorizedUsers().add(user);
        return guideRepo.save(guide);
    }
}
