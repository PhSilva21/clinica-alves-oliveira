package com.bandeira.clinica_alves_oliveira.services;

import com.bandeira.clinica_alves_oliveira.models.User;
import com.bandeira.clinica_alves_oliveira.models.UserRole;
import com.bandeira.clinica_alves_oliveira.repositories.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;

@ExtendWith(MockitoExtension.class)
class AuthorizationServiceTest {

    @Mock
    UserRepository userRepository;

    @InjectMocks
    AuthorizationService authorizationService;

    User user = new User(
            "Marcos",
            "marcos33",
            UserRole.USER
    );

    @Nested
    class loadByUserName{

        @Test
        @DisplayName("Must return user successfully")
        void MustReturnUserSuccessfully() {
            doReturn(user)
                    .when(userRepository)
                    .findByUsername(user.getUsername());

            var response = authorizationService.loadUserByUsername(user.getUsername());

            assertNotNull(response);
            assertEquals(user.getUsername(), response.getUsername());
            assertEquals(user.getPassword(), response.getPassword());
        }

        @Test
        @DisplayName("Should throw exception when not finding user")
        void ShouldThrowExceptionWhenNotFindingUser() {
            doThrow(new UsernameNotFoundException(""))
                    .when(userRepository)
                    .findByUsername(user.getUsername());

            assertThrows(UsernameNotFoundException.class,
                    () -> authorizationService.loadUserByUsername(user.getUsername()));
        }
    }
}