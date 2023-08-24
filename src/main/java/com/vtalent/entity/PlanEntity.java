package com.vtalent.entity;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="Plan_Master")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PlanEntity {
	
	@Id
	@GeneratedValue
	@Column(name = "Plan_Id")
	private Integer planId;
	
	@Column(name = "Plan_Name")
	private String planName;
	
	@Column(name = "plan_Start_Date")
	private LocalDate planStartDate;
	
	@Column(name = "Plan_End_Date")
	private LocalDate planEndDate;
	
	@Column(name = "Plan_Category_Id")
	private Integer planCategoryId;
	
	@Column(name = "ActiveSwitch")
	private String activeSw;
	
	@Column(name = "Created_By")
	private String createdBy;
	
	@Column(name = "Updated_By")
	private String updatedBy;
	
	@Column(name = "Created_Date",updatable = false)
	@CreationTimestamp
	private LocalDate createDate;
	
	@Column(name = "Updated_Date",insertable = false)
	@UpdateTimestamp
	private LocalDate updateDate;
	
	

}
