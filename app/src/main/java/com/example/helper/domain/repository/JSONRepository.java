package com.example.helper.domain.repository;

public interface JSONRepository {
    public String getJsonString();
    public void includeNewApplication(String applicationCategory,String applicationName);
    public void deleteApplication(String applicationCategory,String applicationName);
}
