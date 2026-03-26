package com.rpeyrichoux.guidelisterAPI.test;

import com.rpeyrichoux.guidelisterAPI.model.User;
import com.rpeyrichoux.guidelisterAPI.repositories.UserRepository;
import com.rpeyrichoux.guidelisterAPI.view.UserView;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class UserViewTest {
    private final UserRepository repo = mock(UserRepository.class);
    private final UserView vue = new UserView(repo);

    @Test
    void getUsers_returnsAll() {
        when(repo.findAll()).thenReturn(List.of(new User("a@x.com", "pwd", false)));
        var res = vue.getUsers();
        assertEquals(1, res.size());
        verify(repo).findAll();
    }

    @Test
    void deleteUser_callsRepo() {
        doNothing().when(repo).deleteById(1L);
        vue.deleteUser(1L);
        verify(repo).deleteById(1L);
    }
}
