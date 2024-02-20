package com.auth.authinsta.exceptions;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GenericErrorResponse {
    private String errorMessage;
    private Integer statusCode;
}
