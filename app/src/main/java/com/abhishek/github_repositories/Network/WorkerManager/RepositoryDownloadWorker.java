package com.abhishek.github_repositories.Network.WorkerManager;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.abhishek.github_repositories.DataBase.AppDatabase;
import com.abhishek.github_repositories.DataBase.GithubDao;
import com.abhishek.github_repositories.DataModel.GitHubRepositoryModel;
import com.abhishek.github_repositories.Network.GitHubAPIController;
import com.abhishek.github_repositories.Network.GitHubAPIResponseListener;

import java.util.List;

public class RepositoryDownloadWorker extends Worker {
    private GitHubAPIController controller;
    private Long pageNumber = 0L;

    public RepositoryDownloadWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        GithubDao githubDao = AppDatabase.getInstance(getApplicationContext()).githubDao();
        githubDao.deleteAll();
         controller = new GitHubAPIController(new GitHubAPIResponseListener() {
            @Override
            public void onSuccessResponse(List<GitHubRepositoryModel> repositoryModelList, Long totalCount) {
                for (GitHubRepositoryModel repository: repositoryModelList) {
                    repository.setPage(pageNumber);
                }
                new Thread(() -> {
                    githubDao.insertRepositories(repositoryModelList);
                    if(totalCount > githubDao.getTotalCount()) {
                        try {
                            Thread.sleep(2000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        controller.loadRepositories(++pageNumber);
                    }

                }).start();
            }

            @Override
            public void onErrorResponse() {

            }
        });
        controller.loadRepositories(++pageNumber);
        return Result.success();
    }
}
