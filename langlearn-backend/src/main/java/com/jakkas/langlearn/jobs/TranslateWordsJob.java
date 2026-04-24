package com.jakkas.langlearn.jobs;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.jakkas.langlearn.model.Word;
import com.jakkas.langlearn.repository.WordsRepository;
import com.jakkas.langlearn.service.TranslateWordsService;

@Component
public class TranslateWordsJob {
    private TranslateWordsService translateWordsService;
    private WordsRepository wordsRepository;

    public TranslateWordsJob(TranslateWordsService translateWordsService, WordsRepository wordsRepository) {
        this.translateWordsService = translateWordsService;
        this.wordsRepository = wordsRepository;
    }

    @Scheduled(cron = "0 30 3 * * *")
    public void translateWords() {
        List<String> polishWordList = wordsRepository.findAll()
                                                     .stream()
                                                     .map(Word::getPolish_word)
                                                     .collect(Collectors.toList());
        
        for (String polishWord : polishWordList) {
            translateWordsService.translateWords(polishWord);
        }
    }
}
