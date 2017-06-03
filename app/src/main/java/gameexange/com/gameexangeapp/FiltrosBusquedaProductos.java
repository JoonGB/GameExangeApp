package gameexange.com.gameexangeapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import gameexange.com.gameexangeapp.R;
import gameexange.com.gameexangeapp.controllers.activitites.BusquedaVideojuego;

public class FiltrosBusquedaProductos extends AppCompatActivity {
    private final static int MY_CHILD_ACTIVITY = 1;

    private Button btnFiltroVideojuego;

    private Long videojuego;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filtros_busqueda_productos);
        btnFiltroVideojuego = (Button) findViewById(R.id.btnFiltroVideojuego);
        btnFiltroVideojuego.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(FiltrosBusquedaProductos.this, BusquedaVideojuego.class);
                startActivityForResult(i, MY_CHILD_ACTIVITY);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch(requestCode) {
            case (MY_CHILD_ACTIVITY): {
                if (resultCode == RESULT_OK) {
                    Bundle extras = data.getExtras();
                    videojuego = Long.parseLong(extras.getString("videojuego"));
                    Log.e("FiltrosBusqueda->", videojuego.toString());
                }
                break;
            }

        }
    }
}
