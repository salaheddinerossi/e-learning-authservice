package com.example.authservice.serviceImpl;

import com.example.authservice.dto.AdminDto;
import com.example.authservice.exception.AdminNotFoundException;
import com.example.authservice.exception.EmailAlreadyUsedException;
import com.example.authservice.model.Admin;
import com.example.authservice.repository.AdminRepository;
import com.example.authservice.service.AdminService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;


@Service
public class AdminServiceImpl implements AdminService {

    final
    AdminRepository adminRepository;

    private final PasswordEncoder passwordEncoder;


    public AdminServiceImpl(AdminRepository adminRepository, PasswordEncoder passwordEncoder) {
        this.adminRepository = adminRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Admin getAdmin(Long id) {
        return adminRepository.findById(id).orElseThrow(
            AdminNotFoundException::new
        );
    }

    @Override
    public Optional<Admin> findAdminByEmail(String email) {
        return adminRepository.findByEmail(email);
    }


    @Override
    public void registerAdmin(AdminDto adminDto) {

        Admin admin = new Admin();

        admin.setFirstName(adminDto.getFirstName());
        admin.setLastName(adminDto.getLastName());
        admin.setEmail(adminDto.getEmail());

        if (isEmailAlreadyUser(adminDto.getEmail())){
            throw new EmailAlreadyUsedException();
        }

        admin.setPassword(passwordEncoder.encode(adminDto.getPassword()));
        adminRepository.save(admin);

    }

    @Override
    public List<Admin> getAllAdmins() {
        return adminRepository.findAll();
    }


    public Boolean isEmailAlreadyUser(String email){

        List<Admin> admins = getAllAdmins();

        for (Admin admin:admins){
            if(Objects.equals(admin.getEmail(), email)){
                return true;
            }
        }

        return false;
    }
}
