package com.example.demo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Sujet;
@Repository
public interface SujetRepository extends JpaRepository<Sujet,Long>{
	Optional<Sujet> findById(Long id);
	List<Sujet> findAllByOrderByUpdatedAtDesc();
	Sujet findByTitreSujet(String titreSujet);
	List<Sujet> findByUsernameAndActiveTrue(String username);

}
