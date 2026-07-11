package com.yazikochesalna.fileservice.service;

import com.yazikochesalna.fileservice.advice.MinioServerCustomException;
import com.yazikochesalna.fileservice.config.properties.MinioProperties;
import io.minio.GetObjectArgs;
import io.minio.MinioClient;
import io.minio.StatObjectResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DownloadMinioService {

    private static final String ATTACHMENT = "attachment";
    private static final String ORIGINAL_FILENAME = "original-filename";
    private static final String CACHE_CONTROL = "no-cache, no-store, must-revalidate";
    private final MinioClient minioClient;

    private final MinioProperties minioProperties;

    public InputStream getFileStream(String objectPath)
    {
        try {
            return minioClient.getObject(
                    GetObjectArgs.builder()
                            .bucket(minioProperties.bucket().name())
                            .object(objectPath)
                            .build());
        } catch (Exception e) {
            throw new MinioServerCustomException(MinioServerCustomException.MessageType.GET_STREAM_ERROR, e.getMessage());
        }
    }

    public HttpHeaders createResponseHeaders(StatObjectResponse stat, String defaultFilename) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType(stat.contentType()));
        headers.setContentLength(stat.size());
        headers.setContentDispositionFormData(
                ATTACHMENT,
                Optional.ofNullable(stat.userMetadata())
                        .map(meta -> meta.get(ORIGINAL_FILENAME))
                        .orElse(defaultFilename)
        );
        headers.setCacheControl(CACHE_CONTROL);
        return headers;
    }

}
