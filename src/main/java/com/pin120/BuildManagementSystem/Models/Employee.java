package com.pin120.BuildManagementSystem.Models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "employees")
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "build_object_id")
    BuildObject buildObject;
    @Column(nullable = false)
    String firstName;
    @Column(nullable = false)
    String lastName;
    String middleName;
    @Column(nullable = false)
    String phone;
    @Column(nullable = false)
    String status;
    @Column(nullable = false)
    String speciality;
    @Column(nullable = false)
    String post;
    @Column(nullable = false)
    String photo;
}
