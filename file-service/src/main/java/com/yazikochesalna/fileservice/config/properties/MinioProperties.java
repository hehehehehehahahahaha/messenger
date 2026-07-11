package com.yazikochesalna.fileservice.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.Name;

@ConfigurationProperties(prefix = "minio")
public record MinioProperties(
    @Name("access-key") String accessKey,
    @Name("secret-key") String secretKey,
    String url,
    Bucket bucket
) {
    public record Bucket(
            String name
    ){}


}
