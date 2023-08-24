package com.vtalent.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vtalent.binding.CoResponse;
import com.vtalent.service.Coservice;

@RestController
public class CORestController {
	
	@Autowired
	private Coservice coservice;
	
	@GetMapping("/process")
	public CoResponse processTriggers() {
		return coservice.processPendingTriggers();
		
	}
	
}
