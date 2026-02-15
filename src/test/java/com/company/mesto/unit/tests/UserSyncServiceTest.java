package com.company.mesto.unit.tests;

import com.company.mesto.api.models.UserMe;
import com.company.mesto.unit.api.UsersApi;
import com.company.mesto.unit.repository.UserRepository;
import com.company.mesto.unit.service.UserSyncService;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@Tag("unit")
@ExtendWith(MockitoExtension.class)
class UserSyncServiceTest {

    @Mock
    UsersApi usersApi;

    @Mock
    UserRepository userRepository;

    @InjectMocks
    UserSyncService service;

    @Captor
    ArgumentCaptor<UserMe> userCaptor;

    @Test
    void shouldSaveUserReturnedByApi() {
        UserMe fake = new UserMe();
        fake.setName("Viacheslav");
        when(usersApi.getMe()).thenReturn(fake);

        service.syncMeToDb();

        verify(usersApi).getMe();
        verify(userRepository).save(userCaptor.capture());
        assertEquals("Viacheslav", userCaptor.getValue().getName());

    }

    @Test
    void shouldNotSaveUser_whenUserNull(){
        when(usersApi.getMe()).thenReturn(null);

        service.syncMeToDb();

        verify(usersApi).getMe();
        verifyNoInteractions(userRepository);

    }

    @Test
    void shouldNotSaveUser_whenThrowException(){
        when(usersApi.getMe()).thenThrow(new RuntimeException("boom"));

        assertThrows(RuntimeException.class, () -> service.syncMeToDb());

        verify(usersApi).getMe();
        verifyNoInteractions(userRepository);

    }

    @Test
    void shouldNotSaveUser_whenNameBlank(){
        UserMe fake = new UserMe();
        fake.setName("  ");
        when(usersApi.getMe()).thenReturn(fake);

        service.syncMeToDb();

        verify(usersApi).getMe();
        verify(userRepository, never()).save(any());

    }
}
