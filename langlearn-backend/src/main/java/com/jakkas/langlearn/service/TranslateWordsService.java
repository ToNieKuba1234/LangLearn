package com.jakkas.langlearn.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import com.jakkas.langlearn.dto.MyMemoryResponse;
import com.jakkas.langlearn.model.Word;
import com.jakkas.langlearn.repository.WordsRepository;

import jakarta.transaction.Transactional;

@Service
public class TranslateWordsService {
    private WordsRepository wordsRepository;
    private RestClient restClient;

    public TranslateWordsService(RestClient.Builder builder, WordsRepository wordsRepository) {
        this.restClient = builder.baseUrl("https://api.mymemory.translated.net").build();
        this.wordsRepository = wordsRepository;
    }

    @Transactional
    public void translateWords(String polishWord) {
        System.out.println("AAAAAAAAAAAAAAAAA DEBUG: invoked for : " + polishWord);
        
        Word wordEntity = wordsRepository.findAll()
                                         .stream()
                                         .filter(word -> word.getPolish_word().equals(polishWord))
                                         .findFirst()
                                         .get();
        
        try {
            var response = restClient.get()
                                     .uri(uriBuilder -> uriBuilder
                                         .path("/get")
                                         .queryParam("q", polishWord)
                                         .queryParam("langpair", "pl|de")
                                         .build())
                                     .retrieve()
                                     .body(MyMemoryResponse.class);

                System.out.println("BBBBBBBBBBB  Response : " + response);
                if (response != null && response.responseData() != null) {
                    System.out.println("CCCCCCCCCCCCCCCCC Resnot null" + response.responseData());
                    System.out.println(response.responseData().translatedText());

                    String translation = response.responseData().translatedText();
                    wordEntity.setGerman_word(translation);


                    wordsRepository.save(wordEntity);
                }
        } catch (Exception e) {
            System.out.println("API Error : " + e.getMessage());
        }

    }
}