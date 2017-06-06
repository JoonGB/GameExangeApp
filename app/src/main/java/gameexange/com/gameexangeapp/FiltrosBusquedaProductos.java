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
import android.widget.EditText;
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
    private final static int VIDEOJUEGOS_ACTIVITY = 1;
    private final static int LOCALIZACION_ACTIVITY = 2;

    // Filtro nombre productos
    private EditText etBusquedaFiltros;

    // Filtro videojuegos
    private LinearLayout llBuscadorItem;
    private ImageView ivVideojuego;
    private TextView tvNombreVideojuego;
    private Button btnFiltroVideojuego;
    private Videojuego busquedaVideojuego;


    // Filtro precio
    private EditText etPrecioMin;
    private EditText etPrecioMax;

    // Filtro localización
    private GoogleMap mMap;
    private Double latitud;
    private Double longitud;

    // Orden filtro
    private static Button btnOrden;
    private Button btnOrdenProximidad;
    private Button btnOrdenBaratos;
    private Button btnOrdenCaros;
    private Button btnOrdenNovedades;
    private int orden = 1;

    private Button btnReiniciar;
    private Button btnAplicar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filtros_busqueda_productos);

        // Filtro nombre productos
        etBusquedaFiltros = (EditText) findViewById(R.id.etBusquedaFiltros);

        // Filtro videojuegos
        llBuscadorItem = (LinearLayout) findViewById(R.id.llBuscadorItem);
        ivVideojuego = (ImageView) findViewById(R.id.imagenVideojuego);
        tvNombreVideojuego = (TextView) findViewById(R.id.tvNombreVideojuego);
        btnFiltroVideojuego = (Button) findViewById(R.id.btnFiltroVideojuego);
        btnFiltroVideojuego.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(FiltrosBusquedaProductos.this, BusquedaVideojuego.class);
                startActivityForResult(i, VIDEOJUEGOS_ACTIVITY);
            }
        });

        // Filtro precio
        etPrecioMin = (EditText) findViewById(R.id.etPrecioMin);
        etPrecioMax = (EditText) findViewById(R.id.etPrecioMax);


        // Filtro localización
        latitud = 41.3850639;
        longitud = 2.1734034999999494;
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

                btnOrdenProximidad = (Button) mView.findViewById(R.id.btnOrdenProximidad);
                btnOrdenProximidad.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        FiltrosBusquedaProductos.btnOrden.setText("Orden: distancia");
                        Drawable drawable = getResources().getDrawable(R.mipmap.ic_location_mini, null);
                        Drawable drawable2 = getResources().getDrawable(R.mipmap.ic_next, null);
                        btnOrden.setCompoundDrawablesRelativeWithIntrinsicBounds(drawable, null, drawable2, null);
                        orden = 1;
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
                        orden = 2;
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
                        orden = 3;
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
                        orden = 4;
                        dialog.dismiss();
                    }
                });


            }
        });


        // REINICIAR/APLICAR
        btnReiniciar = (Button) findViewById(R.id.btnReiniciar);
        btnAplicar = (Button) findViewById(R.id.btnAplicar);

        btnReiniciar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etBusquedaFiltros.setText("");

                busquedaVideojuego = null;
                ivVideojuego.setVisibility(View.INVISIBLE);
                tvNombreVideojuego.setText("Selecciona un videojuego...");

                etPrecioMin.setText("");
                etPrecioMax.setText("");
                latitud = 41.3850639;
                longitud = 2.1734034999999494;

                mMap.clear();
                MarkerOptions marker = new MarkerOptions()
                        .position(new LatLng(latitud, longitud))
                        .icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_map_marker));
                mMap.addMarker(marker);
                mMap.animateCamera(CameraUpdateFactory.newLatLng(new LatLng(latitud, longitud)));

                btnOrden.setText("Orden: distancia");
                Drawable drawable = getResources().getDrawable(R.mipmap.ic_location_mini, null);
                Drawable drawable2 = getResources().getDrawable(R.mipmap.ic_next, null);
                btnOrden.setCompoundDrawablesRelativeWithIntrinsicBounds(drawable, null, drawable2, null);
                orden = 1;
            }
        });

        btnAplicar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FiltrosBusquedaProductos.this, ResultadosBusqueda.class);
                intent.putExtra("busquedaFiltros", etBusquedaFiltros.getText().toString());
                if(busquedaVideojuego != null) {
                    intent.putExtra("busquedaVideojuego", busquedaVideojuego.getId());
                }
                if (etPrecioMin.getText().toString().equals("")) {
                    intent.putExtra("precioMin", 0);
                } else {
                    intent.putExtra("precioMin", Integer.parseInt(etPrecioMin.getText().toString()));
                }
                if (etPrecioMax.getText().toString().equals("")) {
                    intent.putExtra("precioMax", 0);
                } else {
                    intent.putExtra("precioMax", Integer.parseInt(etPrecioMax.getText().toString()));
                }
                intent.putExtra("latitud", latitud);
                intent.putExtra("longitud", longitud);
                intent.putExtra("orden", orden);
                startActivity(intent);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch(requestCode) {
            case (VIDEOJUEGOS_ACTIVITY): {
                if (resultCode == RESULT_OK) {
                    Bundle extras = data.getExtras();
                    busquedaVideojuego = extras.getParcelable("videojuego");
                    if (busquedaVideojuego != null) {
                        tvNombreVideojuego.setText(busquedaVideojuego.getNombre());

                        String caratula = busquedaVideojuego.getCaratula();
                        if (!caratula.equals("")) {
                            Picasso.with(this).load(caratula).into(ivVideojuego);
                        }

                        ivVideojuego.setVisibility(View.VISIBLE);
                    }
                }
                break;
            }
            case (LOCALIZACION_ACTIVITY): {
                if (resultCode == RESULT_OK) {
                    Bundle extras = data.getExtras();
                    latitud = extras.getDouble("latitude");
                    longitud = extras.getDouble("longitude");
                    mMap.clear();
                    MarkerOptions marker = new MarkerOptions()
                            .position(new LatLng(latitud, longitud))
                            .icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_map_marker));
                    mMap.addMarker(marker);
                    mMap.animateCamera(CameraUpdateFactory.newLatLng(new LatLng(latitud, longitud)));
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
        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                Intent i = new Intent(FiltrosBusquedaProductos.this, ObtenerLocalizacion.class);
                startActivityForResult(i, LOCALIZACION_ACTIVITY);
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
