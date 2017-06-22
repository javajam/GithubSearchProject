package com.wewow.githubsearchproject.home;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.wewow.githubsearchproject.R;
import com.wewow.githubsearchproject.detail.DetailActivity;
import com.wewow.githubsearchproject.injection.home.DaggerMainActivityComponent;
import com.wewow.githubsearchproject.source.remote.entries.Item;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.wewow.githubsearchproject.util.Constants.REPO_URL;


public class MainActivity extends AppCompatActivity implements MainActivityContract.View {

    private static final String TAG = "MainActivity";
    private Item user;

    @BindView(R.id.userTextView)
    TextView mTextView;

    @BindView(R.id.userText)
    EditText mEditText;

    @BindView(R.id.avatar)
    ImageView mAvatar;

    @Inject
    MainActivityPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        DaggerMainActivityComponent.create().inject(this);

        mPresenter.addView(this);
    }

    @Override
    public void updateView(Item user) {
        this.user = user;
        mTextView.setText(user.getLogin());
        Picasso.with(this)
                .load(user.getAvatarUrl())
                .into(mAvatar);
    }

    public void findUser(View view) {
        mPresenter.processUser(mEditText.getText().toString());
    }

    public void goToRepos(View view) {
        Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtra(REPO_URL, user.getReposUrl());
        startActivity(intent);
    }
}
