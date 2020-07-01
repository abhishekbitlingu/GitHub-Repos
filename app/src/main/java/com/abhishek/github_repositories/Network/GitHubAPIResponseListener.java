package com.abhishek.github_repositories.Network;

import com.abhishek.github_repositories.DataModel.GitHubRepositoryModel;

import java.util.List;

public interface GitHubAPIResponseListener {
     void onSuccessResponse(List<GitHubRepositoryModel> repositoryModelList, Long totalCount);

    void onErrorResponse();
}
