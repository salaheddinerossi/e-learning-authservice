package com.example.authservice.model;


import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@DiscriminatorValue("ADMIN")
public class Admin extends User {

}
