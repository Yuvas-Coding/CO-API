package com.vtalent.repository;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vtalent.entity.EligibilityDetailsEntity;

public interface EligibilityDateilsRepository extends JpaRepository<EligibilityDetailsEntity, Serializable> {
	
	public EligibilityDetailsEntity findByCaseNum(Long caseNum);
}
