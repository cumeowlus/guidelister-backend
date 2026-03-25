package com.example.henritrip.test;

import com.example.henritrip.model.Activite;
import com.example.henritrip.model.Guide;
import com.example.henritrip.model.User;
import com.example.henritrip.repository.ActiviteRepository;
import com.example.henritrip.repository.GuideRepository;
import com.example.henritrip.repository.UserRepository;
import com.example.henritrip.vue.GuideVue;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

class GuideVueTest {
    private final GuideRepository guideRepo = mock(GuideRepository.class);
    private final ActiviteRepository actRepo = mock(ActiviteRepository.class);
    private final UserRepository userRepo = mock(UserRepository.class);
    private final GuideVue vue = new GuideVue(guideRepo, actRepo, userRepo);

    @Test
    void addActivity_attachesGuideAndSaves() {
        Guide g = new Guide("T", "D");
        g.setId(1L);
        when(guideRepo.findById(1L)).thenReturn(Optional.of(g));
        when(actRepo.save(any())).thenAnswer(inv -> inv.getArgument(0));
        Activite a = new Activite("Musee");
        var saved = vue.addActivity(1L, a);
        assertNotNull(saved.getGuide());
        assertEquals(1L, saved.getGuide().getId());
        verify(actRepo).save(a);
    }

    @Test
    void deleteActivity_validatesGuideThenDeletes() {
        Guide g = new Guide("T", "D");
        g.setId(2L);
        Activite a = new Activite("X");
        a.setId(5L);
        a.setGuide(g);
        when(actRepo.findById(5L)).thenReturn(Optional.of(a));
        doNothing().when(actRepo).deleteById(5L);
        vue.deleteActivity(2L, 5L);
        verify(actRepo).deleteById(5L);
    }

    @Test
    void editGuide_updatesFields() {
        Guide old = new Guide("Old", "old");
        old.setId(3L);
        when(guideRepo.findById(3L)).thenReturn(Optional.of(old));
        when(guideRepo.save(any())).thenAnswer(inv -> inv.getArgument(0));
        Guide upd = new Guide("New", "new");
        upd.setNbJour(4);
        var out = vue.editGuide(3L, upd);
        assertEquals("New", out.getTitre());
        assertEquals(4, out.getNbJour());
    }

    @Test
    void addUserToGuide_addsAndSaves() {
        Guide g = new Guide("G", "D");
        g.setId(7L);
        g.setAuthorizedUsers(new java.util.HashSet<>());
        User u = new User("u@x.com", "p", false);
        u.setId(9L);
        when(guideRepo.findById(7L)).thenReturn(Optional.of(g));
        when(userRepo.findById(9L)).thenReturn(Optional.of(u));
        when(guideRepo.save(any())).thenAnswer(inv -> inv.getArgument(0));
        var out = vue.addUserToGuide(7L, 9L);
        assertTrue(out.getAuthorizedUsers().contains(u));
        verify(guideRepo).save(g);
    }
}
