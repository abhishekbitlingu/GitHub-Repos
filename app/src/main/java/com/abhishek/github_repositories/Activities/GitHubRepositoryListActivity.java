package com.abhishek.github_repositories.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityOptionsCompat;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.work.Constraints;
import androidx.work.NetworkType;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;
import androidx.work.WorkRequest;
import androidx.work.Worker;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.abhishek.github_repositories.Adapters.GitHubRepositoryListAdapter;
import com.abhishek.github_repositories.DataBase.AppDatabase;
import com.abhishek.github_repositories.DataModel.GitHubRepositoryModel;
import com.abhishek.github_repositories.Network.WorkerManager.RepositoryDownloadWorker;
import com.abhishek.github_repositories.Utils.AppUtils;
import com.abhishek.github_repositories.Utils.NavigatorUtils;
import com.abhishek.github_repositories.Utils.ShareUtils;
import com.abhishek.github_repositories.custom.recyclerview.RecyclerLayoutClickListener;
import com.abhishek.github_repositories.custom.recyclerview.RecyclerViewPaginator;
import com.abhishek.github_repositories.databinding.ActivityGithubRepositoryListBinding;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class GitHubRepositoryListActivity extends AppCompatActivity implements RecyclerLayoutClickListener {
    private Long pageNumber = 0L;
    private ActivityGithubRepositoryListBinding binding;
    private GitHubRepositoryListAdapter githubListAdapter;
    private AppDatabase mDbInstance;
    private Long totalRepoCount = 0L;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Constraints constraints = new Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build();
        WorkRequest request = new PeriodicWorkRequest.Builder(RepositoryDownloadWorker.class,
                15, TimeUnit.MINUTES)
                .setConstraints(constraints)
                .build();
        WorkManager.getInstance(this)
                .enqueue(request);
        initialiseView();
    }

    private void initialiseView() {
        binding = ActivityGithubRepositoryListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.mainToolbar.toolbar);

        mDbInstance = AppDatabase.getInstance(this);
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
                Log.d(GitHubRepositoryListActivity.class.getSimpleName(), "loadMore: called");
                prepareDataAndRefreshView();
            }
        });
        prepareDataAndRefreshView();
    }

    public void prepareDataAndRefreshView() {
        displayLoader();
        mDbInstance.githubDao().getRepositoriesByPage().observe(this,
                repositoryModelList -> {
                    hideLoader();
                    githubListAdapter.setItems(repositoryModelList);
                });
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