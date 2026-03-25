package com.rpeyrichoux.guidelisterAPI.test.view;

import com.rpeyrichoux.guidelisterAPI.controller.UserController;
import com.rpeyrichoux.guidelisterAPI.model.User;
import com.rpeyrichoux.guidelisterAPI.view.UserView;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class UserControllerTest {
    private final UserView vue = mock(UserView.class);
    private final UserController controller = new UserController(vue);

    @Test
    void getUsers_returnsList() {
        when(vue.getUsers()).thenReturn(List.of(new User("a@x", "p", false)));
        var res = controller.getUsers();
        assertEquals(1, res.size());
        verify(vue).getUsers();
    }

    @Test
    void addUser_callsVue() {
        User u = new User("b@x", "p", false);
        when(vue.addUser(u)).thenReturn(u);
        var resp = controller.addUser(u);
        assertEquals(u, resp.getBody());
        verify(vue).addUser(u);
    }

    @Test
    void deleteUser_callsVue() {
        doNothing().when(vue).deleteUser(1L);
        var resp = controller.deleteUser(1L);
        assertEquals(204, resp.getStatusCodeValue());
        verify(vue).deleteUser(1L);
    }
}
