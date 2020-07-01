package com.abhishek.github_repositories.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityOptionsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

import com.abhishek.github_repositories.Adapters.GitHubRepositoryListAdapter;
import com.abhishek.github_repositories.DataModel.GitHubRepositoryModel;
import com.abhishek.github_repositories.Network.GitHubAPIController;
import com.abhishek.github_repositories.Network.GitHubAPIResponseListener;
import com.abhishek.github_repositories.Utils.AppUtils;
import com.abhishek.github_repositories.Utils.NavigatorUtils;
import com.abhishek.github_repositories.Utils.ShareUtils;
import com.abhishek.github_repositories.custom.recyclerview.RecyclerLayoutClickListener;
import com.abhishek.github_repositories.custom.recyclerview.RecyclerViewPaginator;
import com.abhishek.github_repositories.databinding.ActivityGithubRepositoryListBinding;

import java.util.List;

public class GitHubRepositoryListActivity extends AppCompatActivity implements RecyclerLayoutClickListener {
    private Long pageNumber = 0L;
    private ActivityGithubRepositoryListBinding binding;
    private GitHubRepositoryListAdapter githubListAdapter;
    private GitHubAPIController apiController;
    private Long totalRepoCount = 0L;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initialiseView();
    }

    private void initialiseView() {
        binding = ActivityGithubRepositoryListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.mainToolbar.toolbar);

        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        githubListAdapter = new GitHubRepositoryListAdapter(getApplicationContext(), this);
        binding.recyclerView.setAdapter(githubListAdapter);
        binding.recyclerView.addOnScrollListener(new RecyclerViewPaginator(binding.recyclerView) {
            @Override
            public boolean isLastPage() {
                return totalRepoCount <= githubListAdapter.getItemCount();
            }

            @Override
            public void loadMore() {
                displayLoader();
                apiController.loadRepositories(++pageNumber);
            }
        });

        /* This is to handle configuration changes:
         * during configuration change, when the activity
         * is recreated, we check if the viewModel
         * contains the list data. If so, there is no
         * need to call the api or load data from cache again */
        prepareDataAndRefreshView();
    }

    public void prepareDataAndRefreshView() {
        // TODO call API to get repositories and show loader
        displayLoader();
        apiController = new GitHubAPIController(new GitHubAPIResponseListener() {
            @Override
            public void onSuccessResponse(List<GitHubRepositoryModel> repositoryModelList, Long totalCount) {
                totalRepoCount = totalCount;
                if (!repositoryModelList.isEmpty()) {
                    hideEmptyView();
                    githubListAdapter.setItems(repositoryModelList);
                } else {
                    displayEmptyView();
                }
            }

            @Override
            public void onErrorResponse() {
                if (githubListAdapter.getItemCount() == 0) {
                    displayEmptyView();
                }
            }
        });
        apiController.loadRepositories(++pageNumber);
    }

    private void displayLoader() {
        binding.progressBar.setVisibility(View.VISIBLE);
    }

    private void hideLoader() {
        binding.progressBar.setVisibility(View.GONE);
    }

    private void hideEmptyView() {
        hideLoader();
        binding.viewEmpty.emptyContainer.setVisibility(View.GONE);
    }

    private void displayEmptyView() {
        hideLoader();
        binding.viewEmpty.emptyContainer.setVisibility(View.VISIBLE);
    }

    @Override
    public void redirectToDetailScreen(View imageView, View titleView, View revealView, View languageView, GitHubRepositoryModel githubEntity) {
        NavigatorUtils.redirectToDetailScreen(this, githubEntity,
                ActivityOptionsCompat.makeSceneTransitionAnimation(this, AppUtils.getTransitionElements(
                        getApplicationContext(), imageView, titleView, revealView, languageView
                )));
    }

    @Override
    public void sharePost(GitHubRepositoryModel githubEntity) {
        ShareUtils.shareUrl(this, githubEntity.getHtmlUrl());
    }


}