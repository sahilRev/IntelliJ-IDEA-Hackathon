package com.assignmebt.techgig.Service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.assignmebt.techgig.Entity.User;
import com.assignmebt.techgig.Repository.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {UserServiceImpl.class})
@ExtendWith(SpringExtension.class)
public class UserServiceImplTest {
    @MockBean
    private UserRepository userRepository;

    @Autowired
    private UserServiceImpl userServiceImpl;

    @Test
    public void testGetAllUsers() {
        ArrayList<User> userList = new ArrayList<User>();
        when(this.userRepository.findAll()).thenReturn(userList);
        List<User> actualAllUsers = this.userServiceImpl.getAllUsers();
        assertSame(userList, actualAllUsers);
        assertTrue(actualAllUsers.isEmpty());
        verify(this.userRepository).findAll();
    }

    @Test
    public void testGetUserById() {
        User user = new User();
        user.setLastName("Doe");
        user.setId(123L);
        user.setFirstName("Jane");
        user.setPhone(1L);
        Optional<User> ofResult = Optional.<User>of(user);
        when(this.userRepository.findById((Long) any())).thenReturn(ofResult);
        User actualUserById = this.userServiceImpl.getUserById(123L);
        assertEquals("Jane", actualUserById.getFirstName());
        assertEquals(1L, actualUserById.getPhone().longValue());
        assertEquals("Doe", actualUserById.getLastName());
        assertEquals(123L, actualUserById.getId().longValue());
        verify(this.userRepository).findById((Long) any());
        assertTrue(this.userServiceImpl.getAllUsers().isEmpty());
    }

    @Test
    public void testGetUserById2() {
        when(this.userRepository.findById((Long) any())).thenReturn(Optional.<User>empty());
        assertThrows(RuntimeException.class, () -> this.userServiceImpl.getUserById(123L));
        verify(this.userRepository).findById((Long) any());
    }

    @Test
    public void testAddUser() {
        User user = new User();
        user.setLastName("Doe");
        user.setId(123L);
        user.setFirstName("Jane");
        user.setPhone(1L);
        when(this.userRepository.save((User) any())).thenReturn(user);

        User user1 = new User();
        user1.setLastName("Doe");
        user1.setId(123L);
        user1.setFirstName("Jane");
        user1.setPhone(1L);
        this.userServiceImpl.addUser(user1);
        verify(this.userRepository).save((User) any());
        assertTrue(this.userServiceImpl.getAllUsers().isEmpty());
    }

    @Test
    public void testDeleteUser() {
        doNothing().when(this.userRepository).deleteById((Long) any());
        this.userServiceImpl.deleteUser(123L);
        verify(this.userRepository).deleteById((Long) any());
        assertTrue(this.userServiceImpl.getAllUsers().isEmpty());
    }

    @Test
    public void testFindPaginated() {
        PageImpl<User> pageImpl = new PageImpl<User>(new ArrayList<User>());
        when(this.userRepository.findAll((org.springframework.data.domain.Pageable) any())).thenReturn(pageImpl);
        Page<User> actualFindPaginatedResult = this.userServiceImpl.findPaginated(1, 3, "Sort Field", "Sort Direction");
        assertSame(pageImpl, actualFindPaginatedResult);
        assertTrue(actualFindPaginatedResult.toList().isEmpty());
        verify(this.userRepository).findAll((org.springframework.data.domain.Pageable) any());
        assertTrue(this.userServiceImpl.getAllUsers().isEmpty());
    }
}

