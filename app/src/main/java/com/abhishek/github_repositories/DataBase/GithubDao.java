package com.abhishek.github_repositories.DataBase;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.abhishek.github_repositories.DataModel.GitHubRepositoryModel;

import java.util.List;


@Dao
public interface GithubDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertRepositories(List<GitHubRepositoryModel> githubEntities);

    @Query("SELECT * FROM `GitHubRepositoryModel` ORDER BY page ASC")
    LiveData<List<GitHubRepositoryModel>> getRepositoriesByPage();

    @Query("SELECT count(*) FROM `GitHubRepositoryModel`")
    Long getTotalCount();

    @Query("DELETE FROM `GitHubRepositoryModel`")
    void deleteAll();
}
