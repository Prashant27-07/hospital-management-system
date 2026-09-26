package com.hms.department.service;

import com.hms.department.dto.DepartmentRequest;
import com.hms.department.dto.DepartmentResponse;
import com.hms.department.entity.Department;
import com.hms.department.exception.ResourceNotFoundException;
import com.hms.department.mapper.DepartmentMapper;
import com.hms.department.repository.DepartmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class DepartmentService {

    private final DepartmentRepository departmentRepository;
    private final DepartmentMapper departmentMapper;

    public DepartmentResponse create(DepartmentRequest request) {
        return departmentMapper.toResponse(departmentRepository.save(departmentMapper.toEntity(request)));
    }

    @Transactional(readOnly = true)
    public List<DepartmentResponse> findAll() {
        return departmentRepository.findAll().stream().map(departmentMapper::toResponse).toList();
    }

    @Transactional(readOnly = true)
    public DepartmentResponse findById(Long id) {
        return departmentMapper.toResponse(getOrThrow(id));
    }

    public DepartmentResponse update(Long id, DepartmentRequest request) {
        Department department = getOrThrow(id);
        departmentMapper.updateEntity(department, request);
        return departmentMapper.toResponse(departmentRepository.save(department));
    }

    public void delete(Long id) {
        departmentRepository.delete(getOrThrow(id));
    }

    private Department getOrThrow(Long id) {
        return departmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Department not found with id " + id));
    }
}
