package com.abhishek.github_repositories.custom.recyclerview;

import android.view.View;

import com.abhishek.github_repositories.DataModel.GitHubRepositoryModel;

public interface RecyclerLayoutClickListener {

    void redirectToDetailScreen(View imageView,
                                View titleView,
                                View revealView,
                                View languageView,
                                GitHubRepositoryModel githubEntity);

    void sharePost(GitHubRepositoryModel githubEntity);
}
