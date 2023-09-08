package com.example.ogani_be.Common.Response;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
@Data
@Builder
public class ResponseDataTotal implements Serializable {
    private String status;
    private String message;
    private Object data;
    private Long total;

}
