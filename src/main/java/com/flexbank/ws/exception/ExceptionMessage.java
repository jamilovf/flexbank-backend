package com.flexbank.ws.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class ExceptionMessage {
    private Date timeStamp;
    private String message;
}
