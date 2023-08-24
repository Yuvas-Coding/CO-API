package com.vtalent.repository;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.vtalent.entity.DcIncomeEntity;
@Repository
public interface DcIncomeRepository extends JpaRepository<DcIncomeEntity , Serializable>{
	public DcIncomeEntity findByCaseNum(Long caseNumber);
}
