package com.example.ogani_be.Common.Response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.io.Serializable;
@Data
@NoArgsConstructor
@RequiredArgsConstructor
@ToString
public class Response<T> implements Serializable {
    @JsonProperty("status")
    @NonNull
    private Integer status;

    @JsonProperty("massage")
    @NonNull
    private String message;

    @JsonProperty("data")
    private T data;
    @JsonProperty("total")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Long total;

}
