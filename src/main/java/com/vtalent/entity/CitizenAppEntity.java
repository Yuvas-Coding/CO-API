package com.vtalent.entity;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.Data;

@Data
@Table(name = "CITIZEN_APPS")
@Entity
public class CitizenAppEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "Application_Id")
	private Integer appId;

	@Column(name = "Full_Name")
	private String fullName;

	@Column(name = "Email_Id")
	private String email;

	@Column(name = "Gender")
	private String gender;

	@Column(name = "State_Name")
	private String stateName;

	@Column(name = "created_By")
	private String createdBy;

	@Column(name = "updated_By")
	private String updatedBy;

	@Column(name = "Phone_Number")
	private Long phno;

	@Column(name = "SSN_Number")
	private Long ssn;

	@Column(name = "Created_Date")
	@CreationTimestamp
	private LocalDate createDate;

	@Column(name = "Updated_Date")
	@UpdateTimestamp
	private LocalDate updateDate;

	@Column(name = "Date_of_Birth")
	private LocalDate dob;

}
