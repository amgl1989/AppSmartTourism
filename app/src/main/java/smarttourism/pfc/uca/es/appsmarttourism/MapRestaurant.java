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

/*-----------------------------------------------------------------------------------------------*/
/* Clase que llama al mapa de Google. Muestra la localizacion del usuario, asi como del          */
/* restaurante que se esta consultando.                                                          */
/*-----------------------------------------------------------------------------------------------*/
public class MapRestaurant extends FragmentActivity {
    private GoogleMap mapa;
    Restaurant restaurant = new Restaurant();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map);
        Bundle extras = getIntent().getExtras();
        restaurant.setLatitude(extras.getDouble("latitude"));
        restaurant.setLongitude(extras.getDouble("longitude"));
        restaurant.setName(extras.getString("name"));

        mapa = ((SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map)).getMap();
        mapa.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        mapa.setMyLocationEnabled(true);
        mapa.getUiSettings().setZoomControlsEnabled(true);
        mapa.getUiSettings().setCompassEnabled(false);
        mapa.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(restaurant.getLatitude(), restaurant.getLongitude()), 18));

        mapa.addMarker(new MarkerOptions()
                .position(new LatLng(restaurant.getLatitude(), restaurant.getLongitude()))
                .title(restaurant.getName())
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED))
                .anchor(0.5f, 0.5f));
    }

    public void miPosicion(View view) {
        if (mapa.getMyLocation() != null) {
            mapa.animateCamera(CameraUpdateFactory.newLatLngZoom(
                    new LatLng(mapa.getMyLocation().getLatitude(), mapa.getMyLocation().getLongitude()), 18));
            //Utiliza la feomula de Haversine para obtener la distancia entre el usuario y el restaurante.
            Double distance = 6371 * Math.acos(Math.cos(Math.toRadians(restaurant.getLatitude())) *
                    Math.cos(Math.toRadians(mapa.getMyLocation().getLatitude())) *
                    Math.cos(Math.toRadians(mapa.getMyLocation().getLongitude()) - Math.toRadians(restaurant.getLongitude())) +
                    Math.sin(Math.toRadians(restaurant.getLatitude())) * Math.sin(Math.toRadians(mapa.getMyLocation().getLatitude())));

           Resources res = getResources();
           //Imprime un mensaje por pantalla informando de la distancia en kms a la que se encuentra el
           // restaurante del usuario.
           mapa.addMarker(new MarkerOptions()
                   .position(new LatLng(mapa.getMyLocation().getLatitude(), mapa.getMyLocation().getLongitude()))
                   .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_CYAN))
                   .title(String.format("%.3f", distance) + res.getString(R.string.texto1) + " " + restaurant.getName()));
        }
    }
}