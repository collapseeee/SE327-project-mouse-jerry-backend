package com.mousejerry.backend.config;

import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class S3ConfigTest {

    @Test
    void s3ClientAndPresigner_WithoutCredentials_CreatesSuccessfully() {
        S3Config config = new S3Config();
        ReflectionTestUtils.setField(config, "endpoint", "https://djorohnniuxhnrjztdfq.storage.supabase.co/storage/v1/s3");
        ReflectionTestUtils.setField(config, "region", "ap-northeast-2");
        ReflectionTestUtils.setField(config, "accessKey", "");
        ReflectionTestUtils.setField(config, "secretKey", "");

        S3Client client = config.s3Client();
        assertNotNull(client);
        client.close();

        S3Presigner presigner = config.s3Presigner();
        assertNotNull(presigner);
        presigner.close();
    }

    @Test
    void s3ClientAndPresigner_WithValidCredentials_CreatesSuccessfully() {
        S3Config config = new S3Config();
        ReflectionTestUtils.setField(config, "endpoint", "https://djorohnniuxhnrjztdfq.storage.supabase.co/storage/v1/s3");
        ReflectionTestUtils.setField(config, "region", "ap-northeast-2");
        ReflectionTestUtils.setField(config, "accessKey", "myAccessKey");
        ReflectionTestUtils.setField(config, "secretKey", "mySecretKey123");

        S3Client client = config.s3Client();
        assertNotNull(client);
        client.close();

        S3Presigner presigner = config.s3Presigner();
        assertNotNull(presigner);
        presigner.close();
    }
}
