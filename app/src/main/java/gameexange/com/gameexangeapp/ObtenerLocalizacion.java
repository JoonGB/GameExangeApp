package gameexange.com.gameexangeapp;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.AutocompleteFilter;
import com.google.android.gms.location.places.AutocompletePrediction;
import com.google.android.gms.location.places.AutocompletePredictionBuffer;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.squareup.picasso.Picasso;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileDescriptor;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import gameexange.com.gameexangeapp.controllers.activitites.BusquedaVideojuego;
import gameexange.com.gameexangeapp.models.Videojuego;

public class ObtenerLocalizacion extends FragmentActivity implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    private EditText etLocalizacion;
    private Button btnLocalizacion;
    private AutoCompleteTextView actvLocalizacion;
    //private GooglePlacesAdapter adapter;

    private TextView locationLatitude;
    private TextView locationLongitude;

    private GoogleMap mMap;
    private GoogleApiClient mGoogleApiClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_obtener_localizacion);

        /*mGoogleApiClient = new GoogleApiClient
                .Builder(this)
                .addApi(Places.GEO_DATA_API)
                .addApi(Places.PLACE_DETECTION_API)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();
*/
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        etLocalizacion = (EditText) findViewById(R.id.etLocalizacion);


        btnLocalizacion = (Button) findViewById(R.id.btnLocalizacion);
        btnLocalizacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String localizacion = etLocalizacion.getText().toString();
                List<Address> addressList = null;

                if (localizacion != null || !localizacion.equals("")) {
                    Geocoder geocoder = new Geocoder(ObtenerLocalizacion.this);
                    try {
                        addressList = geocoder.getFromLocationName(localizacion, 1);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Address address = addressList.get(0);
                    LatLng latLng = new LatLng(address.getLatitude(), address.getLongitude());
                    locationLatitude.setText("" + address.getLatitude());
                    locationLongitude.setText("" + address.getLongitude());
                    mMap.addMarker(new MarkerOptions().position(latLng).title(localizacion).icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_location)));
                    mMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
                }
            }
        });


        actvLocalizacion = (AutoCompleteTextView) findViewById(R.id.actvLocalizacion);
        actvLocalizacion.setAdapter(new GooglePlacesAutocompleteAdapter(this, R.layout.list_item));
        actvLocalizacion.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String str = (String) adapterView.getItemAtPosition(i);
                Toast.makeText(ObtenerLocalizacion.this, str, Toast.LENGTH_SHORT).show();
            }
        });

        locationLatitude = (TextView) findViewById(R.id.locationLatitude);
        locationLongitude = (TextView) findViewById(R.id.locationLongitude);
    }



    @TargetApi(Build.VERSION_CODES.M)
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(0, 0);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        mMap.setMyLocationEnabled(true);
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            mMap.setMyLocationEnabled(true);
        } else {
            if (shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION)) {
                Toast.makeText(this, "Se necesitan permisos de geolocalización", Toast.LENGTH_SHORT).show();
            }
            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                mMap.setMyLocationEnabled(true);
            }
        }
    }


    @Override
    protected void onStart() {
        super.onStart();
        if (mGoogleApiClient != null)
            mGoogleApiClient.connect();
    }


    @Override
    public void onConnected(@Nullable Bundle bundle) {
        //if( adapter != null ) {
        //adapter.setmGoogleApiClient(mGoogleApiClient);
        //}
    }

    @Override
    protected void onStop() {
        if (mGoogleApiClient != null && mGoogleApiClient.isConnected()) {
            //adapter.setmGoogleApiClient(null);
            mGoogleApiClient.disconnect();
        }
        super.onStop();
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    class GooglePlacesAutocompleteAdapter extends ArrayAdapter implements Filterable {
        private ArrayList resultList;

        public GooglePlacesAutocompleteAdapter(Context context, int textViewResourceId) {
            super(context, textViewResourceId);
        }

        @Override
        public int getCount() {
            return resultList.size();
        }

        @Override
        public String getItem(int index) {
            return resultList.get(index).toString();
        }

        @Override
        public Filter getFilter() {
            Filter filter = new Filter() {
                @Override
                protected FilterResults performFiltering(CharSequence busqueda) {
                    FilterResults filterResults = new FilterResults();
                    if (busqueda != null) {
                        resultList = autocomplete(busqueda.toString());

                        filterResults.values = resultList;
                        filterResults.count = resultList.size();
                    }
                    return filterResults;
                }

                @Override
                protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                    if (filterResults != null && filterResults.count > 0) {
                        notifyDataSetChanged();
                    } else {
                        notifyDataSetInvalidated();
                    }
                }
            };
            return filter;
        }

    }

    public static ArrayList autocomplete (String busqueda) {
        ArrayList resultList = null;

        HttpURLConnection conn = null;
        StringBuilder jsonResults = new StringBuilder();
        try {
            StringBuilder sb = new StringBuilder("https://maps.googleapis.com/maps/api/place" + "/autocomplete" + "/json");
            sb.append("?key=" + "AIzaSyBl6O982GkxvefVnw3BzjmcozKfzoPvZ6k");
            //sb.append("&components=country:gr");
            sb.append("&input=" + URLEncoder.encode(busqueda, "utf8"));

            URL url = new URL(sb.toString());
            conn = (HttpURLConnection) url.openConnection();
            InputStreamReader in = new InputStreamReader(conn.getInputStream());

            int read;
            char[] buff = new char[1024];
            while ((read = in.read(buff)) != -1) {
                jsonResults.append(buff, 0, read);
            }
        } catch (MalformedURLException e) {
            Log.e("Google Places", "Error processing Places API URL", e);
            return resultList;
        } catch (IOException e) {
            Log.e("Google Places", "Error connecting to Places API", e);
            return resultList;
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }

        try {
            JSONObject jsonObj = new JSONObject(jsonResults.toString());
            JSONArray predsJsonArray = jsonObj.getJSONArray("predictions");

            resultList = new ArrayList(predsJsonArray.length());
            for (int i = 0; i < predsJsonArray.length(); i++) {
                System.out.println(predsJsonArray.getJSONObject(i).getString("description"));
                System.out.println("============================================================");
                resultList.add(predsJsonArray.getJSONObject(i).getString("description"));
            }
        } catch (JSONException e) {
            Log.e("Google Places", "Cannot process JSON results", e);
        }
        return resultList;
    }


    /*public class GooglePlacesAdapter extends ArrayAdapter implements Filterable {

        private Context context;
        private GoogleApiClient mGoogleApiClient;
        private String busqueda;

        public GooglePlacesAdapter(Context context){
            super(context,android.R.layout.simple_dropdown_item_1line);
            this.context = context;
        }

        @Override
        public Filter getFilter() {
            return new Filter() {
                @Override
                protected FilterResults performFiltering(CharSequence constraint) {

                    if( mGoogleApiClient == null || !mGoogleApiClient.isConnected() ) {
                        Toast.makeText( getContext(), "Not connected", Toast.LENGTH_SHORT ).show();
                        return null;
                    }

                    clear();

                    displayPredictiveResults(constraint.toString());

                    return null;
                }

                @Override
                protected void publishResults(CharSequence constraint, FilterResults results) {
                    notifyDataSetChanged();
                }
            };
        }

        @Override
        public View getView (int position, View convertView, ViewGroup parent) {
            View myView = convertView;
            if (myView == null) {
                LayoutInflater inflater = LayoutInflater.from(context);
                myView = inflater.inflate(R.layout.buscador_videojuego_item, parent, false);

                BusquedaVideojuego.VideojuegoListAdapter.ViewHolder holder = new BusquedaVideojuego.VideojuegoListAdapter.ViewHolder();
                holder.ivImagenVideojuego = (ImageView) myView.findViewById(R.id.imagenVideojuego);
                holder.tvNombreVideojuego = (TextView) myView.findViewById(R.id.nombreVideojuego);

                myView.setTag(holder);
            }
            BusquedaVideojuego.VideojuegoListAdapter.ViewHolder holder = (BusquedaVideojuego.VideojuegoListAdapter.ViewHolder) myView.getTag();

            Videojuego videojuego = videojuegos.get(position);
            String nombre = videojuego.getNombre();
            holder.tvNombreVideojuego.setText(nombre);

            String miniatura = videojuego.getMiniatura();
            if(!videojuego.getMiniatura().equals("")){
                Picasso.with(context).load(miniatura).into(holder.ivImagenVideojuego);
            }
            return myView;
        }

        public void setmGoogleApiClient(GoogleApiClient mGoogleApiClient) {
            this.mGoogleApiClient = mGoogleApiClient;
        }

        private void displayPredictiveResults( String query )
        {
            //Southwest corner to Northeast corner.
            LatLngBounds bounds = new LatLngBounds( new LatLng( 39.906374, -105.122337 ), new LatLng( 39.949552, -105.068779 ) );

            //Filter: https://developers.google.com/places/supported_types#table3
            List<Integer> filterTypes = new ArrayList<Integer>();
            filterTypes.add( Place.TYPE_ESTABLISHMENT );

            Places.GeoDataApi.getAutocompletePredictions( mGoogleApiClient, query, bounds, AutocompleteFilter.create( filterTypes ) )
                    .setResultCallback (
                            new ResultCallback<AutocompletePredictionBuffer>() {
                                @Override
                                public void onResult( AutocompletePredictionBuffer buffer ) {

                                    if( buffer == null )
                                        return;

                                    if( buffer.getStatus().isSuccess() ) {
                                        for( AutocompletePrediction prediction : buffer ) {
                                            add(prediction.getPlaceId().toString(), prediction.getDescription().toString());
                                        }
                                    }

                                    //Prevent memory leak by releasing buffer
                                    buffer.release();
                                }
                            }, 60, TimeUnit.SECONDS );
        }
    }*/


}
