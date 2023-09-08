package com.example.ogani_be.Common.Response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
@Data
@Builder
public class ResponseData implements Serializable {
    private String status;
    private String message;
    private Object data;
}
