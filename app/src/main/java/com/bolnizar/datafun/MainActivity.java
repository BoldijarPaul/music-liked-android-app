package com.bolnizar.datafun;

import com.bolnizar.datafun.facebook.FacebookPresenter;
import com.bolnizar.datafun.facebook.FacebookView;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity implements FacebookCallback<LoginResult>, FacebookView {

    @BindView(R.id.login_button)
    LoginButton mLoginButton;
    @BindView(R.id.main_sync)
    View mSync;

    private CallbackManager mCallBackManager;

    private FacebookPresenter mFacebookPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle(R.string.fb_login);
        ButterKnife.bind(this);

        mSync.setVisibility(AccessToken.getCurrentAccessToken() == null ? View.INVISIBLE : View.VISIBLE);
        mCallBackManager = CallbackManager.Factory.create();
        mLoginButton.setReadPermissions("public_profile", "user_friends", "email", "user_actions.music", "user_likes");
        mLoginButton.registerCallback(mCallBackManager, this);
        mFacebookPresenter = new FacebookPresenter(this);
    }

    @Override
    public void onSuccess(LoginResult loginResult) {
        mSync.setVisibility(View.VISIBLE);
    }

    @Override
    public void onCancel() {
        Toast.makeText(this, R.string.login_canceled, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onError(FacebookException error) {
        Log.e(getClass().getName(), "Facebook Exception", error);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mCallBackManager.onActivityResult(requestCode, resultCode, data);
    }

    @OnClick(R.id.main_sync)
    void syncClick() {
        mFacebookPresenter.loadLikes();
    }

    @OnClick(R.id.main_music)
    void musicClick() {
        startActivity(new Intent(this, MusicActivity.class));
    }
}
