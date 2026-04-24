package com.jakkas.langlearn.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record MyMemoryResponse(ResponseData responseData) {}
