package com.vtalent.repository;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.vtalent.entity.DcEducationEntity;
@Repository
public interface DcEducationRepository extends JpaRepository<DcEducationEntity, Serializable>{
	public DcEducationEntity findByCaseNum(Long caseNumber);
}
