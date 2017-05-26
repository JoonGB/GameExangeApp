package gameexange.com.gameexangeapp.controllers.activitites;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import gameexange.com.gameexangeapp.R;
import gameexange.com.gameexangeapp.controllers.managers.RegistroManager;
import gameexange.com.gameexangeapp.models.UserExt;

public class RegistroActivity extends AppCompatActivity {
    private String coord;
    private UserExt userExt;
    private EditText etUsername;
    private EditText etPassword1;
    private EditText etPassword2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);


         etUsername = (EditText) findViewById(R.id.username);
         etPassword1 = (EditText) findViewById(R.id.pass);
         etPassword2 = (EditText) findViewById(R.id.pass2);

        final Button btnRegistro = (Button) findViewById(R.id.btnRegistro);


        Bundle extras = getIntent().getExtras();
        final String latlon = extras.getString("latlon");

        coord = latlon;

        btnRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Registro(view);
            }
        });
    }
    private void Registro (View v){
        etUsername.setError(null);
        etPassword1.setError(null);
        etPassword2.setError(null);

        userExt = new UserExt();

        String usernameDTO = etUsername.getText().toString().toLowerCase();
        System.out.println(usernameDTO);
        String passDTO = etPassword1.getText().toString();
        System.out.println(passDTO);
        String pass2DTO = etPassword2.getText().toString();
        System.out.println(pass2DTO);

        boolean cancelar = false;
        View focusView = null;

        if (TextUtils.isEmpty(usernameDTO)) {
            etUsername.setError(getString(R.string.error_field_required));
            focusView = etUsername;
            cancelar = true;
        }

        if(passDTO.length() < 4){
            etPassword1.setError("4 chars!");
            focusView = etPassword1;
            cancelar = true;
        }
        if (!passDTO.equals(pass2DTO)) {
            etPassword2.setError("Incorrect.");
            focusView = etPassword2;

            cancelar = true;
        }
/*
        userExt.setLogin(usernameDTO);
        userDTO.setPassword(passDTO);

        if (cancelar) {
            focusView.requestFocus();
        } else {
            showProgress(true);
            RegistroManager.getInstance(v.getContext()).registerAccount(RegistroActivity.this, userExt);
        }



*/

    }



}
