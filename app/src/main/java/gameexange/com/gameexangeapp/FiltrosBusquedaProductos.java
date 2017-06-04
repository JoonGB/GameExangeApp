package gameexange.com.gameexangeapp;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ScaleDrawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.formats.NativeAd;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.vision.text.Text;
import com.squareup.picasso.Picasso;

import gameexange.com.gameexangeapp.R;
import gameexange.com.gameexangeapp.controllers.activitites.BusquedaVideojuego;
import gameexange.com.gameexangeapp.models.Videojuego;

public class FiltrosBusquedaProductos extends AppCompatActivity implements OnMapReadyCallback {
    private final static int MY_CHILD_ACTIVITY = 1;

    private LinearLayout llBuscadorItem;
    private TextView tvNombreVideojuego;
    private ImageView ivVideojuego;
    private Button btnFiltroVideojuego;
    private static Button btnOrden;
    Button btnOrdenProximidad;
    Button btnOrdenBaratos;
    Button btnOrdenCaros;
    Button btnOrdenNovedades;
    private GoogleMap mMap;


    private Videojuego videojuego;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filtros_busqueda_productos);

        llBuscadorItem = (LinearLayout) findViewById(R.id.llBuscadorItem);
        ivVideojuego = (ImageView) findViewById(R.id.imagenVideojuego);
        tvNombreVideojuego = (TextView) findViewById(R.id.nombreVideojuego);
        btnFiltroVideojuego = (Button) findViewById(R.id.btnFiltroVideojuego);
        btnFiltroVideojuego.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(FiltrosBusquedaProductos.this, BusquedaVideojuego.class);
                startActivityForResult(i, MY_CHILD_ACTIVITY);
            }
        });

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


        btnOrden = (Button) findViewById(R.id.btnOrden);
        btnOrden.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(FiltrosBusquedaProductos.this);
                View mView = getLayoutInflater().inflate(R.layout.dialog_orderby, null);
                mBuilder.setView(mView);
                final AlertDialog dialog = mBuilder.create();
                dialog.show();

                final Button btnOrdenProximidad = (Button) mView.findViewById(R.id.btnOrdenProximidad);
                btnOrdenProximidad.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        FiltrosBusquedaProductos.btnOrden.setText("Orden: distancia");
                        Drawable drawable = getResources().getDrawable(R.mipmap.ic_location_mini, null);
                        Drawable drawable2 = getResources().getDrawable(R.mipmap.ic_next, null);
                        btnOrden.setCompoundDrawablesRelativeWithIntrinsicBounds(drawable, null, drawable2, null);
                        dialog.dismiss();
                    }
                });
                btnOrdenBaratos = (Button) mView.findViewById(R.id.btnOrdenBaratos);
                btnOrdenBaratos.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        FiltrosBusquedaProductos.btnOrden.setText("Orden: de más barato a más caro");
                        Drawable drawable = getResources().getDrawable(R.mipmap.ic_coin, null);
                        Drawable drawable2 = getResources().getDrawable(R.mipmap.ic_next, null);
                        btnOrden.setCompoundDrawablesRelativeWithIntrinsicBounds(drawable, null, drawable2, null);
                        dialog.dismiss();
                    }
                });
                btnOrdenCaros = (Button) mView.findViewById(R.id.btnOrdenCaros);
                btnOrdenCaros.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        FiltrosBusquedaProductos.btnOrden.setText("Orden: de más caro a más barato");
                        Drawable drawable = getResources().getDrawable(R.mipmap.ic_coins, null);
                        Drawable drawable2 = getResources().getDrawable(R.mipmap.ic_next, null);
                        btnOrden.setCompoundDrawablesRelativeWithIntrinsicBounds(drawable, null, drawable2, null);
                        dialog.dismiss();
                    }
                });
                btnOrdenNovedades = (Button) mView.findViewById(R.id.btnOrdenNovedades);
                btnOrdenNovedades.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        FiltrosBusquedaProductos.btnOrden.setText("Orden: novedades");
                        Drawable drawable = getResources().getDrawable(R.mipmap.ic_novedades, null);
                        Drawable drawable2 = getResources().getDrawable(R.mipmap.ic_next, null);
                        btnOrden.setCompoundDrawablesRelativeWithIntrinsicBounds(drawable, null, drawable2, null);
                        dialog.dismiss();
                    }
                });


            }
        });



    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch(requestCode) {
            case (MY_CHILD_ACTIVITY): {
                if (resultCode == RESULT_OK) {
                    Bundle extras = data.getExtras();
                    videojuego = extras.getParcelable("videojuego");
                    if (videojuego != null) {
                        tvNombreVideojuego.setText(videojuego.getNombre());

                        String caratula = videojuego.getCaratula();
                        if (!caratula.equals("")) {
                            Picasso.with(this).load(caratula).into(ivVideojuego);
                        }

                        llBuscadorItem.setVisibility(View.VISIBLE);
                    }
                }
                break;
            }

        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        MarkerOptions marker = new MarkerOptions()
                .position(new LatLng(41.3850639, 2.1734034999999494))
                .icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_map_marker));
        mMap.addMarker(marker);
        mMap.animateCamera(CameraUpdateFactory.newLatLng(new LatLng(41.3850639, 2.1734034999999494)));
        mMap.getUiSettings().setScrollGesturesEnabled(false);
        //mMap.getUiSettings().setAllGesturesEnabled(false);
        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                Intent i = new Intent(FiltrosBusquedaProductos.this, ObtenerLocalizacion.class);
                startActivityForResult(i, MY_CHILD_ACTIVITY);
            }
        });
        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                return true;
            }
        });
    }
}
