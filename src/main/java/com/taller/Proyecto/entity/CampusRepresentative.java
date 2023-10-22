package com.taller.Proyecto.entity;

import java.util.List;

import com.taller.Proyecto.service.StateSpace;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "campus_representatives")
public class CampusRepresentative {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "campus_id")
    private Campus campus;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "status", nullable = false)
    private Integer status;

   
}
