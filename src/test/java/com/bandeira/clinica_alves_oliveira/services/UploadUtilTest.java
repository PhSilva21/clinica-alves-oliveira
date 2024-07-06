package com.bandeira.clinica_alves_oliveira.services;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
class UploadUtilTest {

    @Mock
    UploadService uploadUtil;

    @Nested
    class uploadImage {

        @Test
        @DisplayName("Should download file successfully")
        void ShouldDownloadFileSuccessfully() throws IOException {
            File file = new File("C:\\Users\\pedro_jdm\\Downloads\\Files\\images.png");
            InputStream stream = new FileInputStream(file);
            MultipartFile multipartFileToSend = new MockMultipartFile("file", file.getName(),
                    MediaType.TEXT_HTML_VALUE, stream);

            doReturn(true)
                    .when(uploadUtil)
                    .uploadImage(multipartFileToSend);

            var response = uploadUtil.uploadImage(multipartFileToSend);

            assertTrue(response);
        }

        @Test
        @DisplayName("Should throw file download exception")
        void ShouldThrowFileDownloadException() throws IOException {
            File file = new File("C:\\Users\\pedro_jdm\\Downloads\\Files\\images.png");
            InputStream stream = new FileInputStream(file);
            MultipartFile multipartFileToSend = new MockMultipartFile("file", file.getName(),
                    MediaType.TEXT_HTML_VALUE, stream);

            doReturn(false)
                    .when(uploadUtil)
                    .uploadImage(multipartFileToSend);


            var response = uploadUtil.uploadImage(multipartFileToSend);

            assertFalse(response);
        }
    }
}