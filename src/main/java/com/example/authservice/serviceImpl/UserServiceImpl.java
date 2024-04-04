package com.example.authservice.serviceImpl;

import com.example.authservice.dto.PasswordDto;
import com.example.authservice.dto.UserDto;
import com.example.authservice.exception.BadRequestException;
import com.example.authservice.exception.ResourceNotFoundException;
import com.example.authservice.mapper.UserMapper;
import com.example.authservice.model.Admin;
import com.example.authservice.model.Student;
import com.example.authservice.model.Teacher;
import com.example.authservice.model.User;
import com.example.authservice.repository.UserRepository;
import com.example.authservice.response.UserCreatedResponse;
import com.example.authservice.service.UserService;
import jakarta.transaction.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService{

    final
    UserRepository userRepository;

    final
    PasswordEncoder passwordEncoder;

    final
    UserMapper userMapper;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.userMapper = userMapper;
    }

    @Override
    @Transactional
    public UserCreatedResponse RegisterStudent(UserDto userDto) {

        if(isEmailUsed(userDto.getEmail())){
            throw new BadRequestException("email already in use");
        }

        Student student = userMapper.userDtoToStudent(userDto);
        student.setPassword(passwordEncoder.encode(userDto.getPassword()));

        return userMapper.fromStudentToUserCreatedResponse(userRepository.save(student));
    }

    @Override
    @Transactional
    public UserCreatedResponse RegisterAdmin(UserDto userDto) {

        if(isEmailUsed(userDto.getEmail())){
            throw new BadRequestException("email already in use");
        }

        Admin admin = userMapper.userDtoToAdmin(userDto);
        admin.setPassword(passwordEncoder.encode(userDto.getPassword()));



        return userMapper.fromAdminToUserCreatedResponse(userRepository.save(admin));
    }

    @Override
    @Transactional
    public UserCreatedResponse RegisterTeacher(UserDto userDto) {

        if(isEmailUsed(userDto.getEmail())){
            throw new BadRequestException("email Already in use ");
        }

        Teacher teacher = userMapper.userDtoToTeacher(userDto);
        teacher.setPassword(passwordEncoder.encode(userDto.getPassword()));

        return userMapper.fromTeacherToUserCreatedResponse(userRepository.save(teacher));
    }

    @Override
    public Optional<User> findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public Boolean changePassword(PasswordDto passwordDto, String email) {

        User user = findUserByEmail(email).orElseThrow(
                () -> new ResourceNotFoundException("user not found with this emial" + email)
        );

        if (!passwordEncoder.matches(passwordDto.getOldPassword(),user.getPassword())){
            return false;
        }

        user.setPassword(passwordEncoder.encode(passwordDto.getNewPassword()));

        userRepository.save(user);
        return true;
    }

    Boolean isEmailUsed(String email){
        return findUserByEmail(email).isPresent();
    }


}
