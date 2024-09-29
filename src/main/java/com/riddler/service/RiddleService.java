package com.riddler.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.riddler.entity.Riddle;
import com.riddler.repository.RiddleRepository;

import java.util.List;
import java.util.Optional;

@Service
public class RiddleService {

    @Autowired
    private RiddleRepository riddleRepository;

    public List<Riddle> getAllRiddles() {
        return riddleRepository.findAll();
    }

    public Optional<Riddle> getRiddleById(Integer id) {
        return riddleRepository.findById(id);
    }

    public Riddle createRiddle(Riddle riddle) {
        return riddleRepository.save(riddle);
    }

    public void deleteRiddle(Integer id) {
        riddleRepository.deleteById(id);
    }

    public Riddle updateRiddle(Integer id, Riddle riddleDetails) {
        return riddleRepository.findById(id).map(riddle -> {
            riddle.setQuestion(riddleDetails.getQuestion());
            riddle.setAnswer(riddleDetails.getAnswer());
            return riddleRepository.save(riddle);
        }).orElseThrow(() -> new RuntimeException("Riddle not found with id " + id));
    }
}
