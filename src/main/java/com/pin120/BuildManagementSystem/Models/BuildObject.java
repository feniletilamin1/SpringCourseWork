package com.pin120.BuildManagementSystem.Models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "buildObjects")
public class BuildObject {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    Long id;
    @NotNull(message = "Введите начала работ")
    @Column(nullable = false)
    Date startDate;
    @NotNull(message = "Введите дату окончания работ")
    @Column(nullable = false)
    Date endDate;
    @Column(nullable = false)
    String status;
    @NotBlank(message = "Введите название")
    @Column(nullable = false)
    String objectName;
    @NotBlank(message = "Введите название")
    @Column(nullable = false)
    String objectCategory;
    @Column(nullable = false)
    BigDecimal price;
    @NotBlank(message = "Введите телефон")
    @Column(nullable = false)
    String phone;
    @NotBlank(message = "Введите индекс")
    @Column(nullable = false)
    String indexRegion;
    @NotBlank(message = "Введите регион")
    @Column(nullable = false)
    String region;
    @NotBlank(message = "Введите город")
    @Column(nullable = false)
    String city;
    @NotBlank(message = "Введите улицу")
    @Column(nullable = false)
    String street;
    @NotBlank(message = "Введите дом")
    @Column(nullable = false)
    String home;
    @NotNull(message = "Введите квартиру")
    Integer flat;
    Date finishDate;
    @OneToMany(mappedBy = "buildObject", cascade = CascadeType.ALL, orphanRemoval = true)
    List<Employee> employees;
    @OneToMany(mappedBy = "buildObject", cascade = CascadeType.ALL, orphanRemoval = true)
    List<ObjectPhoto> objectPhotos;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id")
    Client client;
}
