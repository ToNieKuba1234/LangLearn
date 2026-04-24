package com.jakkas.langlearn.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jakkas.langlearn.jobs.TranslateWordsJob;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;


@RestController
@RequestMapping("/api")
public class DictionaryController {
    private TranslateWordsJob translateWordsJob;

    public DictionaryController(TranslateWordsJob translateWordsJob) {
        this.translateWordsJob = translateWordsJob;
    }

    @PostMapping("/translate")
    public ResponseEntity<Void> manualTrigger() {
        translateWordsJob.translateWords();
    
        return ResponseEntity.ok().build();
    }
}
