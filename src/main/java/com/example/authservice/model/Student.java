package com.example.authservice.model;


import jakarta.persistence.*;
import lombok.Data;


@Data
@Entity
@DiscriminatorValue("STUDENT")
public class Student extends User {

}

