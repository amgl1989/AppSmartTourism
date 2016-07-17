package smarttourism.pfc.uca.es.appsmarttourism;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * Created by AnaMari on 25/02/2016.
 */

/*-----------------------------------------------------------------------------------------------*/
/* Clase que llama al mapa de Google. Muestra la localizacion del usuario, asi como de la        */
/* oferta turistica que se esta consultando.                                                     */
/*-----------------------------------------------------------------------------------------------*/
public class MapTouristOffers extends FragmentActivity {
    private GoogleMap mapa;
    TouristOffers touristOffers = new TouristOffers();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map);
        Bundle extras = getIntent().getExtras();
        touristOffers.setLatitude(extras.getDouble("latitude"));
        touristOffers.setLongitude(extras.getDouble("longitude"));
        touristOffers.setName(extras.getString("name"));

        mapa = ((SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map)).getMap();
        mapa.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        mapa.setMyLocationEnabled(true);
        mapa.getUiSettings().setZoomControlsEnabled(true);
        mapa.getUiSettings().setCompassEnabled(false);
        mapa.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(touristOffers.getLatitude(), touristOffers.getLongitude()), 18));

        mapa.addMarker(new MarkerOptions()
                .position(new LatLng(touristOffers.getLatitude(), touristOffers.getLongitude()))
                .title(touristOffers.getName())
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED))
                .anchor(0.5f, 0.5f));
    }

    public void miPosicion(View view) {
        if (mapa.getMyLocation() != null) {
            mapa.animateCamera(CameraUpdateFactory.newLatLngZoom(
                    new LatLng(mapa.getMyLocation().getLatitude(), mapa.getMyLocation().getLongitude()), 18));
            //Utiliza la formula de Haversine para obtener la distancia entre el usuario y la oferta turistica.
            Double distance = 6371 * Math.acos(Math.cos(Math.toRadians(touristOffers.getLatitude())) *
                    Math.cos(Math.toRadians(mapa.getMyLocation().getLatitude())) *
                    Math.cos(Math.toRadians(mapa.getMyLocation().getLongitude()) - Math.toRadians(touristOffers.getLongitude())) +
                    Math.sin(Math.toRadians(touristOffers.getLatitude())) * Math.sin(Math.toRadians(mapa.getMyLocation().getLatitude())));

            Resources res = getResources();
            //Imprime un mensaje por pantalla informando de la distancia en kms a la que se encuentra la
            //oferta turistica del usuario.
            mapa.addMarker(new MarkerOptions()
                    .position(new LatLng(mapa.getMyLocation().getLatitude(), mapa.getMyLocation().getLongitude()))
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_CYAN))
                    .title(String.format("%.3f", distance) + res.getString(R.string.texto1) + touristOffers.getName()));
        }
    }
}