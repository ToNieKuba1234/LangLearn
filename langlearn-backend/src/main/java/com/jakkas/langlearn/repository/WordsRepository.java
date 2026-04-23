package com.jakkas.langlearn.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jakkas.langlearn.model.Word;

public interface WordsRepository extends JpaRepository<Word, Long> {}