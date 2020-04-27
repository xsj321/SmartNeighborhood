package com.example.smartagriculture.View.login;

import android.app.Activity;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.smartagriculture.View.Home.HomePage;
import com.example.smartagriculture.Service.LoginAndRegisterLoader;
import com.example.smartagriculture.R;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<String> {

    private LoginViewModel loginViewModel;
    private boolean nowMode = true;
    private String Account;
    private String Password;
    private String UserName;
    private String IP = "101.37.86.133";
    private int PORT = 8123;
    private EditText usernameEditText;
    private EditText passwordEditText;
    private EditText theusernameEditText;
    private TextView registerButton;
    private TextView boxtitle;
    private int count =0 ;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        loginViewModel = ViewModelProviders.of(this, new LoginViewModelFactory())
                .get(LoginViewModel.class);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        usernameEditText = findViewById(R.id.username);
        passwordEditText = findViewById(R.id.password);
        theusernameEditText = findViewById(R.id.theusername);
        registerButton = findViewById(R.id.register);
        boxtitle = findViewById(R.id.box_title);
        final ProgressBar loadingProgressBar = findViewById(R.id.loading);
        final TextView toolBarButton = findViewById(R.id.toolbar_button);
        setAndroidNativeLightStatusBar(this,true);
        loginViewModel.getLoginFormState().observe(this, new Observer<LoginFormState>() {
            @Override
            public void onChanged(@Nullable LoginFormState loginFormState) {
                if (loginFormState == null) {
                    return;
                }
                registerButton.setEnabled(loginFormState.isDataValid());
                if (loginFormState.getUsernameError() != null) {
                    usernameEditText.setError(getString(loginFormState.getUsernameError()));
                }
                if (loginFormState.getPasswordError() != null) {
                    passwordEditText.setError(getString(loginFormState.getPasswordError()));
                }
            }
        });

        loginViewModel.getLoginResult().observe(this, new Observer<LoginResult>() {
            @Override
            public void onChanged(@Nullable LoginResult loginResult) {
                if (loginResult == null) {
                    return;
                }
                loadingProgressBar.setVisibility(View.GONE);
                if (loginResult.getError() != null) {
                    showLoginFailed(loginResult.getError());
                }
                if (loginResult.getSuccess() != null) {
                    updateUiWithUser(loginResult.getSuccess());
                }
                setResult(Activity.RESULT_OK);

                //Complete and destroy login activity once successful
                finish();
            }
        });

        TextWatcher afterTextChangedListener = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // ignore
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // ignore
            }

            @Override
            public void afterTextChanged(Editable s) {
                loginViewModel.loginDataChanged(usernameEditText.getText().toString(),
                        passwordEditText.getText().toString());
            }
        };
        usernameEditText.addTextChangedListener(afterTextChangedListener);
        passwordEditText.addTextChangedListener(afterTextChangedListener);
        passwordEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    loginViewModel.login(usernameEditText.getText().toString(),
                            passwordEditText.getText().toString());
                }
                return false;
            }
        });

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(theusernameEditText.getVisibility() == View.GONE)
                {

                    registerButton.setText(R.string.back_login);
                    boxtitle.setText(R.string.action_sign_in);
                    toolBarButton.setText(R.string.action_sign_in);
                    TranslateAnimation showAnim = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0.0f,
                            Animation.RELATIVE_TO_SELF, 0.0f,
                            Animation.RELATIVE_TO_SELF, 1.0f,
                            Animation.RELATIVE_TO_SELF, 0.0f);
                    showAnim.setDuration(500);
                    theusernameEditText.startAnimation(showAnim);
                    theusernameEditText.setVisibility(View.VISIBLE);
                    nowMode = false;
                }
                else
                {

                    registerButton.setText(R.string.action_sign_in);
                    boxtitle.setText(R.string.login);
                    toolBarButton.setText(R.string.login);
                    theusernameEditText.setVisibility(View.GONE);
                    nowMode = true;
                }
            }
        });
        toolBarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportLoaderManager().initLoader(++count, null, LoginActivity.this).forceLoad();
            }
        });
    }

    private void updateUiWithUser(LoggedInUserView model) {
        String welcome = getString(R.string.welcome) + model.getDisplayName();
        // TODO : initiate successful logged in experience
        Toast.makeText(getApplicationContext(), welcome, Toast.LENGTH_LONG).show();
        Intent intent = new Intent(this,HomePage.class);
        startActivity(intent);
        finish();
    }

    private void showLoginFailed(@StringRes Integer errorString) {
        Toast.makeText(getApplicationContext(), errorString, Toast.LENGTH_SHORT).show();
    }


    @NonNull
    @Override
    public Loader<String> onCreateLoader(int i, @Nullable Bundle bundle) {
        if(nowMode == true){
            Log.d("读取的字符串为：",usernameEditText.getText().toString()+"  "+passwordEditText.getText().toString());
            return new LoginAndRegisterLoader(
                    this,
                    IP,
                    PORT,
                    usernameEditText.getText().toString(),
                    passwordEditText.getText().toString(),
                    nowMode
            );
        }
        else {
            Log.d("读取的字符串为：",usernameEditText.getText().toString()+"  "+passwordEditText.getText().toString());
            return new LoginAndRegisterLoader(
                    this,
                    IP,
                    PORT,
                    usernameEditText.getText().toString(),
                    passwordEditText.getText().toString(),
                    theusernameEditText.getText().toString(),
                    nowMode
            );
        }
    }

    @Override
    public void onLoadFinished(@NonNull Loader<String> loader, String s) {
        final String LoginSucceed ="login_succeed";
        final String LoginError = "login_error";
        final String RegError = "reg_error";
        final String RegSucceed = "reg_succeed";
        String result = LoginError;
        String userName = "未知";
        try {
            JSONObject jsonObject = new JSONObject(s);
            result = jsonObject.getString("result");
            userName = jsonObject.getString("user_name");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        //登录成功
        if (result.equals(LoginSucceed)){
            String text = getString(R.string.login_success);
            Toast.makeText(getApplicationContext(), text, Toast.LENGTH_LONG).show();
            startMain(userName);
            loader.abandon();
        }
        //登录失败
        if (result.equals(LoginError)){
            String text = getString(R.string.login_failed);
            Toast.makeText(getApplicationContext(), text, Toast.LENGTH_LONG).show();
            loader.abandon();
        }
        //注册成功
        if (result.equals(RegSucceed)){
            String text = getString(R.string.registered_success);
            Toast.makeText(getApplicationContext(), text, Toast.LENGTH_LONG).show();
            startMain(userName);
            loader.abandon();
        }
        //注册失败
        if (result.equals(RegError)){
            String text = getString(R.string.registered_failed);
            Toast.makeText(getApplicationContext(), text, Toast.LENGTH_LONG).show();
            loader.abandon();
        }
    }

    @Override
    public void onLoaderReset(@NonNull Loader<String> loader) {

    }


    /***
     * 启动主界面并传递用户名
     * @param UserName
     */
    void startMain(String UserName){
        Log.d("登录的用户为",UserName);
        Intent intent = new Intent(this, HomePage.class);
        intent.putExtra("user_name",UserName);
        startActivity(intent);
        finish();
    }

    private static void setAndroidNativeLightStatusBar(Activity activity, boolean dark) {
        View decor = activity.getWindow().getDecorView();
        if (dark) {
            decor.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        } else {
            decor.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
        }
    }
}
