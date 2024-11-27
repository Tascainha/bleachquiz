package com.bleachquiz.bleachdle.race;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/race")
public class RaceController {

    @Autowired
    public RaceRepository raceRepository;

    @GetMapping
    public ResponseEntity<List<Race>> getAllRaces() {
        List<Race> races = raceRepository.findAll();
        return ResponseEntity.ok(races);
    }

    @PostMapping
    public ResponseEntity<List<Race>> addRaces(@RequestBody List<Race> races) {
        List<Race> savedRaces = raceRepository.saveAll(races);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedRaces);
    }

}
