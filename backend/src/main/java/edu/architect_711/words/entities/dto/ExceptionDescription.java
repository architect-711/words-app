package edu.architect_711.words.entities.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Data @NoArgsConstructor @AllArgsConstructor
public class ExceptionDescription {
    private HttpStatus status;
    private String code;
}
