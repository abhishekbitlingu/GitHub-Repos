package com.abhishek.github_repositories.Utils;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;

import androidx.core.app.ActivityCompat;
import androidx.core.app.ActivityOptionsCompat;

import com.abhishek.github_repositories.Activities.GithubRepositoryDetailActivity;
import com.abhishek.github_repositories.AppConstants;
import com.abhishek.github_repositories.DataModel.GitHubRepositoryModel;

public class NavigatorUtils {

    public static void redirectToDetailScreen(Activity activity,
                                              GitHubRepositoryModel gitHubRepositoryModel,
                                              ActivityOptionsCompat options) {

        Intent intent = new Intent(activity, GithubRepositoryDetailActivity.class);
        intent.putExtra(AppConstants.INTENT_POST, gitHubRepositoryModel);
        ActivityCompat.startActivity(activity, intent, options.toBundle());
    }

    public static void openBrowser(Activity activity,
                                   String url) {
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        activity.startActivity(i);
    }
}
