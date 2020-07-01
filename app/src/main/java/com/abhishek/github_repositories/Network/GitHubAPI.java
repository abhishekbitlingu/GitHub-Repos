package com.abhishek.github_repositories.Network;

import com.abhishek.github_repositories.DataModel.GitHubRepositoryModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

import static com.abhishek.github_repositories.AppConstants.API_ENDPOINT;
import static com.abhishek.github_repositories.AppConstants.QUERY_ORDER_KEY;
import static com.abhishek.github_repositories.AppConstants.QUERY_PAGE_KEY;
import static com.abhishek.github_repositories.AppConstants.QUERY_PARAM_KEY;
import static com.abhishek.github_repositories.AppConstants.QUERY_PER_PAGE_KEY;
import static com.abhishek.github_repositories.AppConstants.QUERY_SORT_KEY;

public interface GitHubAPI {

    @GET(API_ENDPOINT)
    Call<GitHubResponseModel> loadRepositories(@Query(QUERY_PARAM_KEY) String param,
                                                       @Query(QUERY_PER_PAGE_KEY) String perPage,
                                                       @Query(QUERY_SORT_KEY) String sort,
                                                       @Query(QUERY_ORDER_KEY) String order,
                                                       @Query(QUERY_PAGE_KEY) Long page);
}