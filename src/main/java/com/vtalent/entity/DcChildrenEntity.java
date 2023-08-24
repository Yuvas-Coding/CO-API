package com.vtalent.entity;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;


import lombok.Data;

@Entity
@Table(name = "DC_CHILDREN")
@Data
public class DcChildrenEntity {
	
	@Id
	@GeneratedValue
	private Integer childId;
	
	private String childName;
	
	private Integer childAge;
	
	private Long childSsn;
	
	private Long caseNum;

}
