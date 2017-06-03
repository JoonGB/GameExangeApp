package gameexange.com.gameexangeapp;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ImageSpan;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.formats.NativeAd;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.places.AutocompleteFilter;
import com.google.android.gms.location.places.AutocompletePrediction;
import com.google.android.gms.location.places.AutocompletePredictionBuffer;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlaceBuffer;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.TimeUnit;

public class ObtenerLocalizacion extends AppCompatActivity implements OnMapReadyCallback {

    private Button btnConfirmLocation;

    private PlacesAutoCompleteAdapter adapter;
    private AutoCompleteTextView actvLocation;

    private TextView locationLatitude;
    private TextView locationLongitude;


    private static final int PLACE_PICKER_FLAG = 1;

    private static final LatLngBounds BOUNDS_GREATER_BARCELONA = new LatLngBounds(
            new LatLng(41.34794849344354, 2.217864990234375), new LatLng(41.41338061238166, 2.1031951904296875));
    protected GoogleApiClient mGoogleApiClient;
    private GoogleMap mMap;
    private MarkerOptions marker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_obtener_localizacion);

        btnConfirmLocation = (Button) findViewById(R.id.btnConfirmLocation);

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(Places.GEO_DATA_API)
                .build();
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        actvLocation = (AutoCompleteTextView) findViewById(R.id.actvLocation);
        adapter = new PlacesAutoCompleteAdapter(this, android.R.layout.simple_list_item_1,
                mGoogleApiClient, BOUNDS_GREATER_BARCELONA, null);
        actvLocation.setAdapter(adapter);

        locationLatitude = (TextView) findViewById(R.id.locationLatitude);
        locationLongitude = (TextView) findViewById(R.id.locationLongitude);


        actvLocation.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                final PlacesAutoCompleteAdapter.PlaceAutocomplete item = adapter.getItem(i);
                final String placeId = String.valueOf(item.placeId);
                PendingResult<PlaceBuffer> placeResult = Places.GeoDataApi
                        .getPlaceById(mGoogleApiClient, placeId);
                placeResult.setResultCallback(mUpdatePlaceDetailsCallback);
                Places.GeoDataApi.getPlaceById(mGoogleApiClient, placeId)
                        .setResultCallback(new ResultCallback<PlaceBuffer>() {
                            @Override
                            public void onResult(PlaceBuffer places) {
                                if (places.getStatus().isSuccess() && places.getCount() > 0) {
                                    final Place myPlace = places.get(0);
                                    mMap.clear();
                                    marker = new MarkerOptions()
                                            .position(myPlace.getLatLng())
                                            .title(item.description.toString())
                                            .icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_map_marker));
                                    mMap.addMarker(marker);
                                    mMap.animateCamera(CameraUpdateFactory.newLatLng(myPlace.getLatLng()));
                                    locationLatitude.setText("" + myPlace.getLatLng().latitude);
                                    locationLongitude.setText("" + myPlace.getLatLng().longitude);
                                }
                                places.release();
                            }
                        });
            }
        });

    }


    @TargetApi(Build.VERSION_CODES.M)
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        marker = new MarkerOptions()
                .position(new LatLng(41.3850639, 2.1734034999999494))
                .title("Barcelona, España")
                .icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_map_marker));
        locationLatitude.setText("" + 41.3850639);
        locationLongitude.setText("" + 2.1734034999999494);
        mMap.addMarker(marker);
        mMap.animateCamera(CameraUpdateFactory.newLatLng(new LatLng(41.3850639, 2.1734034999999494)));

        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            mMap.setMyLocationEnabled(true);
        } else {
            if (shouldShowRequestPermissionRationale(android.Manifest.permission.ACCESS_FINE_LOCATION)) {
                Toast.makeText(this, "Se necesitan permisos de geolocalización", Toast.LENGTH_SHORT).show();
            }
            requestPermissions(new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                mMap.setMyLocationEnabled(true);
            }
        }

        /*mMap.setOnMyLocationButtonClickListener(new GoogleMap.OnMyLocationButtonClickListener() {
            @Override
            public boolean onMyLocationButtonClick() {
                LocationListener locationListener = new LocationListener() {
                    @Override
                    public void onLocationChanged(Location location) {
                        mMap.clear();
                        marker = new MarkerOptions()
                                .position(new LatLng(location.getLatitude(), location.getLongitude()))
                                .title("Posición actual")
                                .icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_map_marker));
                        mMap.addMarker(marker);
                        mMap.animateCamera(CameraUpdateFactory.newLatLng(new LatLng(location.getLatitude(), location.getLongitude())));
                        locationLatitude.setText(""+location.getLatitude());
                        locationLongitude.setText(""+location.getLongitude());
                    }
                };
                LocationManager locationManager = (LocationManager) getSystemService(ObtenerLocalizacion.this.LOCATION_SERVICE);
                if (ActivityCompat.checkSelfPermission(ObtenerLocalizacion.this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(ObtenerLocalizacion.this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 10, (android.location.LocationListener) locationListener);
                }
                return true;
            }
        });*/
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case PLACE_PICKER_FLAG:
                    Place place = PlacePicker.getPlace(data, this);
                    actvLocation.setText(place.getName() + ", " + place.getAddress());
                    break;
            }
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
    }

    @Override
    protected void onStop() {
        mGoogleApiClient.disconnect();
        super.onStop();
    }

    private ResultCallback<PlaceBuffer> mUpdatePlaceDetailsCallback
            = new ResultCallback<PlaceBuffer>() {
        @Override
        public void onResult(PlaceBuffer places) {
            if (!places.getStatus().isSuccess()) {
                Log.e("Place", "Resultados no completados. Error: " +
                        places.getStatus().toString());
                return;
            }
            final Place place = places.get(0);
        }
    };

    class PlacesAutoCompleteAdapter extends ArrayAdapter<PlacesAutoCompleteAdapter.PlaceAutocomplete> implements Filterable {

        private static final String TAG = "PlaceAutocomplete";
        private ArrayList<PlaceAutocomplete> mResultList;
        private GoogleApiClient mGoogleApiClient;
        private LatLngBounds mBounds;
        private AutocompleteFilter mPlaceFilter;


        public PlacesAutoCompleteAdapter(Context context, int resource, GoogleApiClient googleApiClient,
                                         LatLngBounds bounds, AutocompleteFilter filter) {
            super(context, resource);
            mGoogleApiClient = googleApiClient;
            mBounds = bounds;
            mPlaceFilter = filter;
        }

        public void setBounds(LatLngBounds bounds) {
            mBounds = bounds;
        }

        @Override public int getCount() {
            return mResultList.size();
        }
        @Override public PlaceAutocomplete getItem(int position) { return mResultList.get(position); }

        @Override
        public Filter getFilter() {
            Filter filter = new Filter() {
                @Override
                protected FilterResults performFiltering(CharSequence constraint) {
                    FilterResults results = new FilterResults();
                    if (constraint != null) {
                        mResultList = getAutocomplete(constraint);
                        if (mResultList != null) {
                            results.values = mResultList;
                            results.count = mResultList.size();
                        }
                    }
                    return results;
                }

                @Override
                protected void publishResults(CharSequence constraint, FilterResults results) {
                    if (results != null && results.count > 0) {
                        notifyDataSetChanged();
                    } else {
                        notifyDataSetInvalidated();
                    }
                }
            };
            return filter;
        }

        private ArrayList<PlaceAutocomplete> getAutocomplete(CharSequence constraint) {
            if (mGoogleApiClient.isConnected()) {
                PendingResult<AutocompletePredictionBuffer> results = Places.GeoDataApi.getAutocompletePredictions(mGoogleApiClient, constraint.toString(), mBounds, mPlaceFilter);
                AutocompletePredictionBuffer autocompletePredictions = results
                        .await(60, TimeUnit.SECONDS);

                final Status status = autocompletePredictions.getStatus();
                if (!status.isSuccess()) {
                    Toast.makeText(getContext(), "Error obteniendo predicciones: " + status.toString(),
                            Toast.LENGTH_SHORT).show();
                    autocompletePredictions.release();
                    return null;
                }

                // Predicciones recibidas
                Iterator<AutocompletePrediction> iterator = autocompletePredictions.iterator();
                ArrayList resultList = new ArrayList<>(autocompletePredictions.getCount());
                while (iterator.hasNext()) {
                    AutocompletePrediction prediction = iterator.next();
                    PlaceAutocomplete placeAutocomplete = new PlaceAutocomplete();
                    placeAutocomplete.placeId = prediction.getPlaceId();
                    placeAutocomplete.description = prediction.getFullText(null);
                    resultList.add(placeAutocomplete);
                }
                autocompletePredictions.release();

                return resultList;
            }
            Toast.makeText(getContext(), "Error en la conexion con Google API ",
                    Toast.LENGTH_SHORT).show();
            return null;
        }

        // El holder de informacion
        public class PlaceAutocomplete {

            public CharSequence placeId;
            public CharSequence description;

            PlaceAutocomplete() { }
            PlaceAutocomplete(CharSequence placeId, CharSequence description) {
                this.placeId = placeId;
                this.description = description;
            }

            @Override
            public String toString() {
                return description.toString();
            }
        }
    }

}
