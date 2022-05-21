package com.example.demo.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;


import com.example.demo.model.Suggestion;

public interface SuggestionRepo extends JpaRepository<Suggestion,Long> {
     List<Suggestion> findAllByOrderByUpdatedAtDesc();
	List<Suggestion> findByUsernameAndActiveTrue(String username);
	void deleteByMessage(String message);
	
}
