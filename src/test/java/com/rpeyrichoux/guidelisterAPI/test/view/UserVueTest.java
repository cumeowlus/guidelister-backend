package com.rpeyrichoux.guidelisterAPI.test.view;

import com.example.henritrip.model.User;
import com.example.henritrip.repository.UserRepository;
import com.example.henritrip.vue.UserVue;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.mockito.Mockito.*;

class UserVueTest {
    private final UserRepository repo = mock(UserRepository.class);
    private final UserVue vue = new UserVue(repo);

    @Test
    void getUsers_returnsAll() {
        when(repo.findAll()).thenReturn(List.of(new User("a@x.com", "pwd", false)));
        var res = vue.getUsers();
        assertEquals(1, res.size());
        verify(repo).findAll();
    }

    @Test
    void addUser_setsNotAdmin_andSave() {
        User u = new User("b@x.com", "pwd", true); // true will be overridden
        when(repo.save(any())).thenAnswer(inv -> inv.getArgument(0));
        var out = vue.addUser(u);
        assertFalse(out.isAdmin());
        verify(repo).save(any());
    }

    @Test
    void deleteUser_callsRepo() {
        doNothing().when(repo).deleteById(1L);
        vue.deleteUser(1L);
        verify(repo).deleteById(1L);
    }

    @Test
    void addAdmin_setsAdminTrue() {
        User u = new User("c@x.com", "pwd", false);
        when(repo.save(any())).thenAnswer(inv -> inv.getArgument(0));
        var out = vue.addAdmin(u);
        assertTrue(out.isAdmin());
        verify(repo).save(any());
    }
}
