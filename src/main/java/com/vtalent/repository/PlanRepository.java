package com.vtalent.repository;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.vtalent.entity.PlanEntity;
@Repository
public interface PlanRepository extends JpaRepository<PlanEntity ,Serializable> {

}
