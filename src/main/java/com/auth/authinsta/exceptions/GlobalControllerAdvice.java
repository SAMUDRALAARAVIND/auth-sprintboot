package com.auth.authinsta.exceptions;

import com.auth.authinsta.exceptions.ResponseExceptions.BadRequestException;
import com.auth.authinsta.exceptions.ResponseExceptions.ForbiddenException;
import com.auth.authinsta.exceptions.ResponseExceptions.InternalServerException;
import com.auth.authinsta.exceptions.ResponseExceptions.UnAuthorizedException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalControllerAdvice extends ResponseEntityExceptionHandler {
        @ExceptionHandler(BadRequestException.class)
        public ResponseEntity<GenericErrorResponse> handleBadRequest(BadRequestException exception) {
            return ResponseEntity.status(400).body(
                    GenericErrorResponse.builder()
                            .errorMessage(exception.getMessage())
                            .statusCode(400)
                            .build()
                    );
        }

        @ExceptionHandler(InternalServerException.class)
        public ResponseEntity<GenericErrorResponse> handleInternalServer(InternalServerException exception) {
                return ResponseEntity.status(500).body(
                        GenericErrorResponse.builder()
                        .errorMessage(exception.getMessage())
                        .statusCode(500)
                        .build()
                );
        }

        @ExceptionHandler(ForbiddenException.class)
        public ResponseEntity<GenericErrorResponse> handleForbiddenRequest(ForbiddenException exception){
                return ResponseEntity.status(403).body(
                        GenericErrorResponse.builder()
                                .errorMessage(exception.getMessage())
                                .statusCode(403)
                                .build()
                );
        }

        @ExceptionHandler(UnAuthorizedException.class)
        public ResponseEntity<GenericErrorResponse> handleUnAuthorizedRequest(UnAuthorizedException exception) {
                return ResponseEntity.status(401).body(
                        GenericErrorResponse.builder()
                                .errorMessage(exception.getMessage())
                                .statusCode(401)
                                .build()
                );
        }
}
