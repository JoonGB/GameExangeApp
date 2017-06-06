package gameexange.com.gameexangeapp.controllers.activitites;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import gameexange.com.gameexangeapp.R;
import gameexange.com.gameexangeapp.controllers.managers.LoginCallback;
import gameexange.com.gameexangeapp.models.UserToken;

/**
 * Created by JonGarcia on 06/06/2017.
 */


public class PerfilActivity extends AppCompatActivity implements LoginCallback {
    LayoutInflater inflater;
    private Context context;
    LinearLayout linearLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        linearLayout = (LinearLayout) findViewById(R.id.content_layout);

    }

    @Override
    public void onSuccess(UserToken userToken) {
        inflater.inflate(R.layout.activity_perfil, linearLayout);


    }

    @Override
    public void onFailure(Throwable t) {

    }
}