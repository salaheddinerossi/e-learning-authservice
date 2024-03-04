package com.example.authservice.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@DiscriminatorValue("TEACHER")
public class Teacher extends User {
    private String phoneNumber;
    private Boolean isActive;
}
