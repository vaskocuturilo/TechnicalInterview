package com.example.demo;

import com.example.demo.storage.StorageService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.multipart.MultipartFile;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.then;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("dev")
public class FileUploadTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @MockBean
    private StorageService storageService;
    @LocalServerPort
    private int port;

    @Test
    public void testUploadCorrectFileSize() {
        ClassPathResource pathResource = new ClassPathResource("upload.json");
        MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
        map.add("file", pathResource);
        ResponseEntity<String> response = this.restTemplate.postForEntity("/api/v1/questions/upload", map, String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.FOUND);
        assertThat(response.getHeaders().getLocation().toString())
                .startsWith("http://localhost:" + this.port + "/");
        then(storageService).should().store(any(MultipartFile.class));
    }
}