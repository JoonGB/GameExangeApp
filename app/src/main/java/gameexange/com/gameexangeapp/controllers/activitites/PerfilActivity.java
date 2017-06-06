package gameexange.com.gameexangeapp.controllers.activitites;

import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import gameexange.com.gameexangeapp.R;
import gameexange.com.gameexangeapp.controllers.managers.LoginCallback;
import gameexange.com.gameexangeapp.controllers.managers.UsuarioCallback;
import gameexange.com.gameexangeapp.controllers.managers.UsuarioManager;
import gameexange.com.gameexangeapp.models.UserExt;
import gameexange.com.gameexangeapp.models.UserToken;


public class PerfilActivity extends BaseDrawerActivity implements UsuarioCallback {
    LayoutInflater inflater;
    private Context context;
    LinearLayout linearLayout;

    private ImageView ivUsuario;
    private TextView tvLoginUsuario;
    private TextView tvEmailUsuario;
    private TextView tvNombreUsuario;
    private TextView tvApellidoUsuario;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        linearLayout = (LinearLayout) findViewById(R.id.content_layout);

        UsuarioManager.getInstance().getUsuarioExtByLogin(PerfilActivity.this);
    }

    @Override
    public void onSuccessUserExt(UserExt userExt) {
        inflater.inflate(R.layout.activity_perfil, linearLayout);

        Log.e("Usuario->", userExt.toString());

        ivUsuario = (ImageView) findViewById(R.id.imageView2);
        tvLoginUsuario = (TextView) findViewById(R.id.textView6);
        tvEmailUsuario = (TextView) findViewById(R.id.textView7);
        tvNombreUsuario = (TextView) findViewById(R.id.textView8);
        tvApellidoUsuario = (TextView) findViewById(R.id.textView9);

        if (userExt.getFoto() != null) {
            String fotoUsuario = userExt.getFoto();
            byte[] imageAsBytes = Base64.decode(fotoUsuario, Base64.DEFAULT);
            ivUsuario.setImageBitmap(BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length));
        } else {
            ivUsuario.setImageResource(R.drawable.logo);
        }

        tvLoginUsuario.setText(userExt.getUser().getLogin());
        tvEmailUsuario.setText(userExt.getUser().getEmail());
        tvNombreUsuario.setText(userExt.getUser().getFirstName());
        tvApellidoUsuario.setText(userExt.getUser().getLastName());

    }

    @Override
    public void onFailure(Throwable t) {
        Intent intent = new Intent(PerfilActivity.this, ProductoListActivity.class);
        intent.putExtra("errorUsuario", true);
        startActivity(intent);
    }
}