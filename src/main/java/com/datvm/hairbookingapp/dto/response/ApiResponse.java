package com.datvm.hairbookingapp.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL) //Annotation xac dinh json tra ve khong chua field co gia tri null
public class ApiResponse<T> {
    private int code = 200;
    private String message;
    private T result;

}
