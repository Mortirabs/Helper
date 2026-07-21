package com.example.helper.usecases;

import com.example.helper.data.JSONRepositoryImpl;
import com.example.helper.repository.JSONRepository;

import jakarta.inject.Inject;

public class GetJSONApplicationCategory {
    private JSONRepository jsonRep;
    @Inject
    public GetJSONApplicationCategory(JSONRepository jsonRep) {
        this.jsonRep = jsonRep;
    }
    public String execute() {
        return jsonRep.getJsonString();
    }
}
