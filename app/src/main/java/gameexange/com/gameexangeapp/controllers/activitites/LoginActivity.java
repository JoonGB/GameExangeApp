package gameexange.com.gameexangeapp.controllers.activitites;


import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import gameexange.com.gameexangeapp.R;
import gameexange.com.gameexangeapp.controllers.managers.LoginCallback;
import gameexange.com.gameexangeapp.controllers.managers.LoginManager;
import gameexange.com.gameexangeapp.models.UserToken;

public class LoginActivity extends AppCompatActivity implements LoginCallback {

    private EditText etUsuario;
    private EditText etPassword;
    private Button btnLogin;
    private ProgressBar llProgresslogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etUsuario = (EditText) findViewById(R.id.user);
        etPassword = (EditText) findViewById(R.id.password);
        btnLogin = (Button) findViewById(R.id.loginBTN);
        llProgresslogin = (ProgressBar) findViewById(R.id.login_progress);


        // Si en el campo password clica Enter, hace el login
        etPassword.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.id.password || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });

        TextView btnRegistrar = (TextView) findViewById(R.id.registerBTN);
        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(LoginActivity.this, RegistroActivity.class);
               startActivity(i);
            }
        });



    }

    private void attemptLogin() {
        // Quitamos los errores de los TextFields
        etUsuario.setError(null);
        etPassword.setError(null);

        // Cogemos los datos introducidos
        String usuario = etUsuario.getText().toString();
        String password = etPassword.getText().toString();

        boolean cancel = false;
        View focusView = null;

        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            etPassword.setError(getString(R.string.error_invalid_password));
            focusView = etPassword;
            cancel = true;
        }

        if (TextUtils.isEmpty(usuario)) {
            etUsuario.setError(getString(R.string.error_field_required));
            focusView = etUsuario;
            cancel = true;
        }

        if (cancel) {
            focusView.requestFocus();
        } else {
            showProgress(true);
            LoginManager.getInstance().performLogin(usuario, password, LoginActivity.this);
        }
    }

    private boolean isPasswordValid(String password) {
        return password.length() > 4;
    }

    @Override
    public void onSuccess(UserToken userToken) {
        showProgress(false);
        Intent intent = new Intent(LoginActivity.this, ProductoListActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onFailure(Throwable t) {
        Log.e("LoginActivity->", "performLogin->onFailure ERROR " + t.getMessage());


        // TODO: Gestionar los diversos tipos de errores. Por ejemplo, no se ha podido conectar correctamente.
        showProgress(false);
        etPassword.setError(getString(R.string.error_incorrect_username_or_password));
        etPassword.requestFocus();
    }

    // Progreso del login
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        if (show) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            btnLogin.setEnabled(false);

            llProgresslogin.setVisibility(View.VISIBLE);
            llProgresslogin.animate().setDuration(shortAnimTime).alpha(1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    llProgresslogin.setVisibility(View.GONE);
                }
            });
        } else {
            llProgresslogin.setVisibility(View.GONE);
            btnLogin.setEnabled(true);
        }
    }
}