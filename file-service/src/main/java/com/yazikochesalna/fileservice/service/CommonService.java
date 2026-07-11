package com.yazikochesalna.fileservice.service;

import com.yazikochesalna.fileservice.advice.MinioFileNotFoundCustomException;
import com.yazikochesalna.fileservice.advice.MinioServerCustomException;
import com.yazikochesalna.fileservice.advice.NotAttachedException;
import com.yazikochesalna.fileservice.config.properties.MinioProperties;
import com.yazikochesalna.fileservice.dto.RequestDTO;

import io.minio.*;
import io.minio.errors.*;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommonService {

    private static final String NO_SUCH_KEY = "NoSuchKey";
    private static final String CHAT_ID = "chatId";
    private static final String USER_ID = "userId";
    private MinioClient minioClient;

    private final MinioProperties minioProperties;

    @SneakyThrows
    public void isCreatedBucket() {
        try {
            boolean found = minioClient.bucketExists(BucketExistsArgs.builder()
                    .bucket(minioProperties.bucket().name())
                    .build());
            if (!found) {
                minioClient.makeBucket(MakeBucketArgs.builder()
                        .bucket(minioProperties.bucket().name())
                        .build());
            }
        } catch (Exception e) {
            throw new MinioServerCustomException(MinioServerCustomException.MessageType.INIT_BUCKET_FAILED, e.getMessage());
        }
    }

    public String resolveFolderName(RequestDTO metadata) {
        if (metadata == null) {
            throw new NotAttachedException(NotAttachedException.MessageType.EMPTY_METADATA);
        }
        if (metadata.getChatID() != null) {
            return CHAT_ID + String.valueOf(metadata.getChatID()) + "/";
        } else if (metadata.getUserID() != null) {
            return USER_ID + String.valueOf(metadata.getUserID()) + "/";
        }
        throw new NotAttachedException(NotAttachedException.MessageType.EMPTY_FILE_OWNER);
    }

    public StatObjectResponse getFileStat(String objectPath)
    {
        try {
            return minioClient.statObject(
                    StatObjectArgs.builder()
                            .bucket(minioProperties.bucket().name())
                            .object(objectPath)
                            .build());
        } catch (ErrorResponseException e) {
            if (e.errorResponse().code().equals(NO_SUCH_KEY)) {
                throw new MinioFileNotFoundCustomException(objectPath);
            }
            throw new MinioServerCustomException(MinioServerCustomException.MessageType.GET_FILE_STATS_ERROR, e.getMessage());
        }
        catch (Exception e) {
            throw new MinioServerCustomException(MinioServerCustomException.MessageType.GET_FILE_STATS_INTERNAL_ERROR, e.getMessage());
        }
    }
}
