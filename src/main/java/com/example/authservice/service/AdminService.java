package com.example.authservice.service;

import com.example.authservice.dto.AdminDto;
import com.example.authservice.dto.ChangePasswordDto;
import com.example.authservice.model.Admin;

import java.util.List;
import java.util.Optional;

public interface AdminService {

    Admin getAdmin(Long id);

    Optional<Admin> findAdminByEmail(String email);

    void registerAdmin(AdminDto adminDto);

    List<Admin> getAllAdmins();

}
