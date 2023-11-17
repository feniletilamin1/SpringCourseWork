package com.pin120.BuildManagementSystem.Models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Calendar;
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
    @Column(nullable = false)
    Calendar startDate;
    @Column(nullable = false)
    Calendar endDate;
    @Column(nullable = false)
    String status;
    @Column(nullable = false)
    String objectName;
    @Column(nullable = false)
    String objectCategory;
    @Column(nullable = false)
    BigDecimal price;
    @Column(nullable = false)
    String phone;
    @Column(nullable = false)
    int indexRegion;
    @Column(nullable = false)
    String region;
    @Column(nullable = false)
    String city;
    @Column(nullable = false)
    String street;
    @Column(nullable = false)
    String home;
    Integer flat;
    Calendar finishDate;
    @OneToMany(mappedBy = "buildObject", cascade = CascadeType.ALL, orphanRemoval = true)
    List<Employee> employees;
    @OneToMany(mappedBy = "buildObject", cascade = CascadeType.ALL, orphanRemoval = true)
    List<ObjectPhoto> objectPhotos;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id")
    Client client;
}
