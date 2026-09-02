package com.example.helper.usecases;

import com.example.helper.domain.DialogAlgorithm;
import com.example.helper.repository.JSONRepository;

public class GetWelcomeDialogUseCase {
    private DialogAlgorithm dialogAlgo;
    private String mostUsageAppName;
    public GetWelcomeDialogUseCase(DialogAlgorithm dialogAlgo) {
        this.dialogAlgo = dialogAlgo;
    }
    public String[] execute() {
        return dialogAlgo.getWelcomeDialogText();
    }
}
