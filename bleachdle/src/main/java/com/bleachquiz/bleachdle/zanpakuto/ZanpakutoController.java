package com.bleachquiz.bleachdle.zanpakuto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/zanpakuto")
public class ZanpakutoController {
	
	@Autowired
	private final ZanpakutoRepository zanpakutoRepository;
	
	public ZanpakutoController(ZanpakutoRepository zanpakutoRepository) {
		this.zanpakutoRepository = zanpakutoRepository;
	}
	
	@PostMapping
	public ResponseEntity<Zanpakuto> addZanpakuto(@RequestBody Zanpakuto zanpakuto){
		return ResponseEntity.ok(zanpakutoRepository.save(zanpakuto));
	}
	
}
