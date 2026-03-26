package com.rpeyrichoux.guidelisterAPI.test;

import com.rpeyrichoux.guidelisterAPI.controller.GuideController;
import com.rpeyrichoux.guidelisterAPI.model.Activity;
import com.rpeyrichoux.guidelisterAPI.model.Guide;
import com.rpeyrichoux.guidelisterAPI.view.GuideView;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatusCode;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class GuideControllerTest {
    private final GuideView vue = mock(GuideView.class);
    private final GuideController controller = new GuideController(vue);

    @Test
    void getGuides_callsVue() {
        when(vue.getGuides()).thenReturn(List.of(new Guide("t", "d")));
        var res = controller.getGuides();
        assertEquals(1, res.size());
        verify(vue).getGuides();
    }

    @Test
    void addActivity_callsVue() {
        Activity a = new Activity("X");
        when(vue.addActivity(1L, a)).thenReturn(a);
        var resp = controller.addActivity(1L, a);
        assertEquals(a, resp.getBody());
        verify(vue).addActivity(1L, a);
    }

    @Test
    void deleteGuide_callsVue() {
        doNothing().when(vue).deleteGuide(2L);
        var r = controller.deleteGuide(2L);
        assertEquals(HttpStatusCode.valueOf(204), r.getStatusCode());
        verify(vue).deleteGuide(2L);
    }
}
