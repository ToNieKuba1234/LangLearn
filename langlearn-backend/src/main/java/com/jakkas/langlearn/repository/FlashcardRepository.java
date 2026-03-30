package com.jakkas.langlearn.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jakkas.langlearn.model.Flashcard;

public interface FlashcardRepository extends JpaRepository<Flashcard, Long> {}