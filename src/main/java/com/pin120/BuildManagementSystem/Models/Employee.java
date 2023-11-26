package com.pin120.BuildManagementSystem.Models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

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
    @NotBlank(message = "Введите имя")
    @Column(nullable = false)
    String firstName;
    @NotBlank(message = "Введите фамилию")
    @Column(nullable = false)
    String lastName;
    String middleName;
    @NotBlank(message = "Введите телефон")
    @Column(nullable = false)
    String phone;
    @Column(nullable = false)
    String status;
    @NotBlank(message = "Введите специальность")
    @Column(nullable = false)
    String speciality;
    @NotBlank(message = "Выберите должность")
    @Column(nullable = false)
    String post;
    @Column(nullable = false)
    String photo;
    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL, orphanRemoval = true)
    List<Feedback> feedbacks;
    @OneToMany(mappedBy = "foremanHistory", cascade = CascadeType.ALL, orphanRemoval = true)
    List<BuildObject> buildObjectsHistory;
}
