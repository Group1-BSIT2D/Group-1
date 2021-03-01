package com.oopclass.breadapp.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.oopclass.breadapp.models.Problem;
import com.oopclass.breadapp.repository.ProblemRepository;
import com.oopclass.breadapp.services.IProblemService;

/**
 * OOP Class 20-21
 * @author Gerald Villaran
 */

@Service
public class ProblemService implements IProblemService {
	
	@Autowired
	private ProblemRepository problemRepository;
	
	@Override
	public Problem save(Problem entity) {
		return problemRepository.save(entity);
	}

	@Override
	public Problem update(Problem entity) {
		return problemRepository.save(entity);
	}

	@Override
	public void delete(Problem entity) {
		problemRepository.delete(entity);
	}

	@Override
	public void delete(Long id) {
		problemRepository.deleteById(id);
	}

	@Override
	public Problem find(Long id) {
		return problemRepository.findById(id).orElse(null);
	}

	@Override
	public List<Problem> findAll() {
		return problemRepository.findAll();
	}

	@Override
	public void deleteInBatch(List<Problem> problems) {
		problemRepository.deleteInBatch(problems);
	}
	
}
