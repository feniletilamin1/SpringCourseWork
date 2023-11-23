package com.pin120.BuildManagementSystem.Models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "clients")
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    Long id;
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
    @NotBlank(message = "Введите email")
    @Email(message = "Введите корректный email")
    @Column(nullable = false)
    String email;
    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL, orphanRemoval = true)
    List<Order> orders;
    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL, orphanRemoval = true)
    List<Order> buildObjects;
}
