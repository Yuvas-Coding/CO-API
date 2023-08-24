package com.vtalent.repository;

import java.io.Serializable;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.vtalent.entity.DcCaseEntity;

public interface DcCaseRepository extends JpaRepository<DcCaseEntity, Serializable> {
		public DcCaseEntity findByAppId(Integer appId);
}
