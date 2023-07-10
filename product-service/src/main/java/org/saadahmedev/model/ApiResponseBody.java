package org.saadahmedev.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ApiResponseBody {
    private boolean status;
    private String message;
}
