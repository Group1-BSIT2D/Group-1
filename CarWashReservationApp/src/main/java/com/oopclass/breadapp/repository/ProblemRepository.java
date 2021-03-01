package com.oopclass.breadapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.oopclass.breadapp.models.Problem;

/**
 * OOP Class 20-21
 * @author Gerald Villaran
 */

@Repository
public interface ProblemRepository extends JpaRepository<Problem, Long> {

	//Problem findByEmail(String email);
}
