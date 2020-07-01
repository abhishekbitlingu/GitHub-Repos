package com.abhishek.github_repositories.Network;

import com.abhishek.github_repositories.DataModel.GitHubRepositoryModel;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GitHubResponseModel {
    @SerializedName("total_count")
    private Long totalCount;

    @SerializedName("incomplete_results")
    private boolean inCompleteResults;

    @SerializedName("items")
    private List<GitHubRepositoryModel> repositories;

    public Long getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Long totalCount) {
        this.totalCount = totalCount;
    }

    public boolean isInCompleteResults() {
        return inCompleteResults;
    }

    public void setInCompleteResults(boolean inCompleteResults) {
        this.inCompleteResults = inCompleteResults;
    }

    public void setRepositories(List<GitHubRepositoryModel> repositories) {
        this.repositories = repositories;
    }

    public List<GitHubRepositoryModel> getRepositories() {
        return repositories;
    }
}
