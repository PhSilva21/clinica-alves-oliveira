package com.bandeira.clinica_alves_oliveira.services;

import com.bandeira.clinica_alves_oliveira.models.Address;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;

@ExtendWith(MockitoExtension.class)
class ViaCepServiceTest {

    @Mock
    ViaCepService viaCepService;

        Address address = new Address(
                "03823135",
                "Rua Marcia Mendes",
                "Bloco B",
                "Parque Cisper",
                "São Paulo",
                "SP",
                "sfs",
                "add",
                "011",
                "dddw"
        );

    @Nested
    class getAddress {

        @Test
        @DisplayName("Must return address successfully")
        void MustReturnAddressSuccessfully() throws IOException {

            String cep = "03823135";

            doReturn(address)
                    .when(viaCepService)
                    .getAddress(cep);

            var response = viaCepService.getAddress(cep);

            assertNotNull(response);

            assertEquals(response.getLogradouro(), address.getLogradouro());
            assertEquals(response.getBairro(), address.getBairro());
            assertEquals(response.getLocalidade(), address.getLocalidade());
            assertEquals(response.getUf(), address.getUf());
        }

        @Test
        @DisplayName("Should throw exception when not finding the address")
        void ShouldThrowExceptionWhenNotFindingAddress() throws IOException {

            String cep = "12345";

            doThrow(new IOException())
                    .when(viaCepService)
                    .getAddress(cep);

            assertThrows(IOException.class,
                    () -> viaCepService.getAddress(cep));
        }
    }
}