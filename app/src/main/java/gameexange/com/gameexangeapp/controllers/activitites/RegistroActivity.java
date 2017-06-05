package gameexange.com.gameexangeapp.controllers.activitites;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import gameexange.com.gameexangeapp.R;
import gameexange.com.gameexangeapp.controllers.managers.RegistroCallback;
import gameexange.com.gameexangeapp.controllers.managers.RegistroManager;
import gameexange.com.gameexangeapp.models.User;


public class RegistroActivity extends AppCompatActivity implements RegistroCallback {
    private EditText etUsername;
    private EditText etPassword1;
    private EditText etPassword2;
    private EditText etEmail;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        etUsername = (EditText) findViewById(R.id.username);
        etPassword1 = (EditText) findViewById(R.id.pass);
        etPassword2 = (EditText) findViewById(R.id.pass2);
        etEmail = (EditText) findViewById(R.id.email);
        progressDialog = new ProgressDialog(this);

        final Button btnRegistro = (Button) findViewById(R.id.btnRegistro);

        btnRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Registro(view);
            }
        });
    }
    private void Registro (View v){
        progressDialog.show();
        progressDialog.setMessage("Creating account");
        progressDialog.setIndeterminate(true);

        etUsername.setError(null);
        etPassword1.setError(null);
        etPassword2.setError(null);


        String username = etUsername.getText().toString();
        String password1 = etPassword1.getText().toString();
        String password2 = etPassword2.getText().toString();
        String email = etEmail.getText().toString();


        boolean cancelar = false;
        View focusView = null;



        if (!TextUtils.isEmpty(password1) && !validarPassword(password2)) {
            etPassword1.setError(getString(R.string.error_invalid_password));
            focusView = etPassword1;
            cancelar = true;
        }


        if (!TextUtils.isEmpty(password2) && !validarPassword(password2)) {
            etPassword2.setError(getString(R.string.error_invalid_password));
            focusView = etPassword2;
            cancelar = true;
        }

        if(!compararPasswords(password1, password2)){
            etPassword2.setError(getString(R.string.error_confirm_password));
            focusView = etPassword2;
            cancelar = true;
        }

        if (TextUtils.isEmpty(username)) {
            etUsername.setError(getString(R.string.error_field_required));
            focusView = etUsername;
            cancelar = true;
        }

        if (email.equals("")) {
            etEmail.setError(getString(R.string.error_field_required));
            focusView = etEmail;
            cancelar = true;
        } else if (!validarEmail(email)) {
            etEmail.setError("No es una dirección válida");
            focusView = etEmail;
            cancelar = true;
        }

        if (cancelar) {
            progressDialog.dismiss();
            focusView.requestFocus();
        } else {
            User user = new User();
            user.setLogin(username);
            user.setEmail(email);
            user.setPassword(password1);
            RegistroManager.getInstance().Register(RegistroActivity.this,user);
        }

    }


    @Override
    public void onSuccessRegister() {
        Intent intent = new Intent(RegistroActivity.this, LoginActivity.class);
        intent.putExtra("registrado", true);
        startActivity(intent);
    }

    @Override
    public void onFailureRegister(Throwable t) {

    }

    private boolean validarPassword(String password) {
        return password.length() >= 4;
    }

    private boolean compararPasswords(String password, String confirmPassword) {
        if(confirmPassword.equals(password)){
            return true;
        }
        return false;
    }

    private boolean validarEmail(String email) {
        if(email.contains("@")){
            return true;
        }
        return false;
    }





}
