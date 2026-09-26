package com.hms.department.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DepartmentRequest {

    @NotBlank(message = "departmentName is required")
    @Size(max = 100)
    private String departmentName;

    @Size(max = 255)
    private String description;
}
