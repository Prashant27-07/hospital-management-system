package com.hms.department.mapper;

import com.hms.department.dto.DepartmentRequest;
import com.hms.department.dto.DepartmentResponse;
import com.hms.department.entity.Department;
import org.springframework.stereotype.Component;

@Component
public class DepartmentMapper {

    public Department toEntity(DepartmentRequest request) {
        return Department.builder()
                .departmentName(request.getDepartmentName())
                .description(request.getDescription())
                .build();
    }

    public void updateEntity(Department department, DepartmentRequest request) {
        department.setDepartmentName(request.getDepartmentName());
        department.setDescription(request.getDescription());
    }

    public DepartmentResponse toResponse(Department department) {
        return DepartmentResponse.builder()
                .departmentId(department.getDepartmentId())
                .departmentName(department.getDepartmentName())
                .description(department.getDescription())
                .build();
    }
}
