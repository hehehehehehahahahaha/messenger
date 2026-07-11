package com.yazikochesalna.messagingservice.dto.deserializer;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum DTOField {
    TIMESTAMP ("timestamp"),
    PAYLOAD ("payload"),
    TYPE ("type"),
    MESSAGE_ID ("messageId"),
    REQUEST_ID ("requestId")
    ;
    private final String name;

}
