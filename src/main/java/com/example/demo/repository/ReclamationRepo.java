package com.example.demo.repository;

import java.util.List;


import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.Reclamation;


public interface ReclamationRepo  extends JpaRepository<Reclamation, Long> {
List<Reclamation> findAllByOrderByUpdatedAtDesc();
List<Reclamation> findByUsername(String username);
}
