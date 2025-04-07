package edu.architect_711.words.controller.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import edu.architect_711.words.entities.dto.LanguageDto;

public interface LanguageService {
    ResponseEntity<List<LanguageDto>> findAll(); 
}