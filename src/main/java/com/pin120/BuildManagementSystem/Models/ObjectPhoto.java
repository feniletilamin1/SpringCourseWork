package com.pin120.BuildManagementSystem.Models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Calendar;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "object_photos")
public class ObjectPhoto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    Long id;
    @Column(nullable = false)
    String photoPath;
    @Column(nullable = false)
    Calendar addDate;
    String description;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "build_object_id")
    BuildObject buildObject;
}
