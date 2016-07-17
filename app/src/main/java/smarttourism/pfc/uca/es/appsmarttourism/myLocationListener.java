package smarttourism.pfc.uca.es.appsmarttourism;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by AnaMari on 01/05/2016.
 */

/*-----------------------------------------------------------------------------------------------*/
/* Clase que obtiene la localización del usuario y la actualiza cada vez que haya un cambio.     */
/*-----------------------------------------------------------------------------------------------*/
public class myLocationListener extends MainActivity implements LocationListener {

    private final Context mContext;
    private LocationManager locManager;
    boolean isGPSEnabled = false; //Flag para el estado del GPS.
    boolean isNetworkEnabled = false; //Flag para el estado de la conexión con la red wifi.
    boolean canGetLocation = false;

    Location location;
    double latitudeUser; //Latitud de la ubicación del usuario
    double longitudeUser; //Longitud de la ubicación del usuario

    //Mínima distancia para actualizar los cambios (10 metros).
    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 10;

    //Mínimo tiempo entre actualizaciones (milisegundos).
    private static final long MIN_TIME_BW_UPDATES = 1000 * 60 * 1; //1 minuto.

    protected LocationManager locationManager;

    public myLocationListener(Context context) {
        this.mContext = context;
        getLocation();
    }

    public Location getLocation() {
        try {
            locManager = (LocationManager) mContext.getSystemService(LOCATION_SERVICE);
            //Obtenemos el estado (si está activado) el GPS.
            isGPSEnabled = locManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
            //Obtenemos el estado de la red.
            isNetworkEnabled = locManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
            if (!isGPSEnabled && !isNetworkEnabled) {
                Toast.makeText(getBaseContext(),
                        "Error. Compruebe que tiene activada su ubicación.", Toast.LENGTH_SHORT)
                        .show();
                this.finish();
            } else {
                this.canGetLocation = true;
                // Obtenemos la localización del proveedor de red.
                if (isNetworkEnabled) {
                    locManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, MIN_TIME_BW_UPDATES, MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
                    Log.d("Network", "Network");
                    if (locManager != null) {
                        location = locManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                        if (location != null) {
                            latitudeUser = location.getLatitude();
                            longitudeUser = location.getLongitude();
                        }
                    }
                }
                //Si el GPS está activo, obtenemos latitud/longitud usando el servicio de GPS.
                if (isGPSEnabled) {
                    if (location == null) {
                        locManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,MIN_TIME_BW_UPDATES,MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
                        Log.d("GPS Enabled", "GPS Enabled");
                        if (locManager != null) {
                            location = locManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                            if (location != null) {
                                latitudeUser = location.getLatitude();
                                longitudeUser = location.getLongitude();
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return location;
    }

    @Override
    public void onLocationChanged(Location location) {
    }

    @Override
    public void onProviderDisabled(String provider) {
    }

    @Override
    public void onProviderEnabled(String provider) {
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
    }

    public IBinder onBind(Intent arg0) {
        return null;
    }

    //Precondiciones: Ninguna.
    //Postcondiciones: Devolvemos la latitud del usuario.
    public double getLatitude(){
        if(location != null){
            latitudeUser = location.getLatitude();
        }
        return latitudeUser;
    }

    //Precondiciones: Ninguna.
    //Postcondiciones: Devolvemos la longitud del usuario.
    public double getLongitude(){
        if(location != null){
            longitudeUser = location.getLongitude();
        }

        // return longitude
        return longitudeUser;
    }
    //Precondiciones: Ninguna.
    //Postcondiciones: Devolvemos el valor de canGetLocation.
    public boolean canGetLocation() {
        return this.canGetLocation;
    }
}
