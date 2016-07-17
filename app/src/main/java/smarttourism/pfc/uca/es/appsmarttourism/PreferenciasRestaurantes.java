package smarttourism.pfc.uca.es.appsmarttourism;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.Spinner;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by AnaMari on 09/11/2015.
 */

/*-----------------------------------------------------------------------------------------------*/
/* Clase que da funcionalidad a las vistas de preferencias de restaurantes.                      */
/*-----------------------------------------------------------------------------------------------*/
public class PreferenciasRestaurantes extends AppCompatActivity {
    GeneralPreference generalPreference = new GeneralPreference();
    TypeFoodPreferenceList typeFoodPreferencesAux;
    TypeRestaurantPreferenceList typeRestaurantPreferencesAux;

    Spinner cmbTypesFood;
    Spinner cmbTypesRestaurant;

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_preferences_restaurant);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Bundle extras = getIntent().getExtras();
        //generalPreference.setWeather(extras.getByte("weather"));
        generalPreference.setUsername(extras.getString("username"));
        generalPreference.setTerrace(extras.getByte("terrace"));
        generalPreference.setFreshFood(extras.getByte("freshfood"));
        generalPreference.setCleaning(extras.getByte("cleaning"));

        typeFoodPreferencesAux = (TypeFoodPreferenceList) extras.getSerializable("typefoodPreferenceList");
        typeRestaurantPreferencesAux = (TypeRestaurantPreferenceList) extras.getSerializable("typerestaurantPreferenceList");

        //Llamamos a los servicios necesarios.
        LoadWebServiceTypeRestaurantASYNC loadWebServiceTypeRestaurantASYNC = new LoadWebServiceTypeRestaurantASYNC();
        loadWebServiceTypeRestaurantASYNC.execute(typeRestaurantPreferencesAux);

        LoadWebServiceTypeFoodASYNC loadWebServiceTypeFoodASYNC = new LoadWebServiceTypeFoodASYNC();
        loadWebServiceTypeFoodASYNC.execute(typeFoodPreferencesAux);

        actualizarVista();
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    private HashMap<String, List<String>> returnGroupedChildItems(List<String>parentHeaderInformation, List<String> tipos){
        HashMap<String, List<String>> childContent = new HashMap<String, List<String>>();
        childContent.put(parentHeaderInformation.get(0), tipos);
        return childContent;
    }

    //Precondiciones: Ninguna.
    //Postcondiciones: Actualizamos los valores de los checkbox que aparecen en la vista.
    public void actualizarVista() {
        /*CheckBox cbxTiempoMeteorologico = (CheckBox) findViewById(R.id.cbxTiempoMeteorologicoprefRest);
        if (generalPreference.getWeather() == 1)
            cbxTiempoMeteorologico.setChecked(true);*/
        CheckBox cbxTerraza = (CheckBox) findViewById(R.id.cbxPoseeTerrazapref);
        if (generalPreference.getTerrace() == 1)
            cbxTerraza.setChecked(true);
       CheckBox cbxFreshFood = (CheckBox) findViewById(R.id.cbxFreshFoodPref);
        if (generalPreference.getFreshFood() == 1)
            cbxFreshFood.setChecked(true);
        CheckBox cbxCleaning = (CheckBox) findViewById(R.id.cbxCleaningPref);
        if (generalPreference.getCleaning() == 1)
            cbxCleaning.setChecked(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_user, menu);
        return true;  /** true -> el menú ya está visible */
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.accion_inicio:
                Intent i = new Intent(this,MainActivity.class);
                startActivityForResult(i,0);
                return true;
            case R.id.accion_editar:
                i = new Intent(this, UpdatePreferenciasRestaurantes.class);
                i.putExtra("username", generalPreference.getUsername());
                i.putExtra("cleaning", generalPreference.getCleaning());
                i.putExtra("freshfood", generalPreference.getFreshFood());
                i.putExtra("terrace", generalPreference.getTerrace());
                //i.putExtra("weather", generalPreference.getWeather());
                i.putExtra("typefoodPreferenceList", typeFoodPreferencesAux);
                i.putExtra("typerestaurantPreferenceList",typeRestaurantPreferencesAux);
                startActivity(i);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "PreferenciasRestaurantes Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://smarttourism.pfc.uca.es.appsmarttourism/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "PreferenciasRestaurantes Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://smarttourism.pfc.uca.es.appsmarttourism/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }

    /*-------------------------------------------------------------------------------------------*/
    /* Clase AsyncTask que establece la conexión entre la aplicación y el ESB.                   */
    /* Arquitectura REST. Obtenemos todos los tipos de comida que sirven los restaurantes y que  */
    /* están almacenados en la BD.                                                               */
    /*-------------------------------------------------------------------------------------------*/
    private class LoadWebServiceTypeFoodASYNC extends AsyncTask<TypeFoodPreferenceList, Integer, Boolean> {
        private List<Typefood> typefoodList = new ArrayList<Typefood>();
        private ArrayList<String> typeFoodSelected = new ArrayList<String>();

        @Override
        protected Boolean doInBackground(TypeFoodPreferenceList... params) {
            boolean resul = true;
            TypeFoodPreferenceList typeFoodPreferenceList = params[0];
            HttpClient httpClient = new DefaultHttpClient();
            HttpContext localContext = new BasicHttpContext();
            //String URL = "http://192.168.1.102:8085/informationService/typefood";
            String URL = "http://"+ MainActivity.getDireccionIP() + ":8085/informationService/typefood";
            HttpGet del = new HttpGet(URL);
            try {
                HttpResponse resp = httpClient.execute(del, localContext);
                HttpEntity entity = resp.getEntity();
                String respStr = EntityUtils.toString(entity);
                JSONObject jsonObject = new JSONObject(respStr);
                JSONArray typefoodArray = jsonObject.getJSONArray("typefood");

                for (int i = 0; i < typefoodArray.length(); i++) {
                    JSONObject typefoodJson = typefoodArray.getJSONObject(i);
                    Typefood typefood = new Typefood();
                    typefood.setId(typefoodJson.getString("id"));
                    typefood.setTypefood(typefoodJson.getString("typeFood"));
                    typefoodList.add(typefood);
                }
                for (int i = 0; i < typeFoodPreferenceList.getTypefoodPreferenceArrayList().size(); i++) {
                    for (int j = 0; j < typefoodList.size(); j++) {
                        if (typeFoodPreferenceList.getTypefoodPreferenceArrayList().get(i).getIdtypefood().equals(typefoodList.get(j).getId().toString()))
                            typeFoodSelected.add(typefoodList.get(j).getTypefood());
                    }
                }
            } catch (Exception ex) {
                resul = false;
            }
            return resul;
        }


        @Override
        protected void onPostExecute(Boolean result) {
            if (result) {
                if(typeFoodSelected.size()==0)
                    typeFoodSelected.add("No hay tipos de comidas preferentes seleccionado.");

                cmbTypesFood = (Spinner)findViewById(R.id.spinnerTypeFood);
                ArrayAdapter<String> adaptador = new ArrayAdapter<String>(PreferenciasRestaurantes.this,
                        android.R.layout.simple_spinner_item, typeFoodSelected);
                adaptador.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                cmbTypesFood.setAdapter(adaptador);
            }
        }
    }

    /*-------------------------------------------------------------------------------------------*/
    /* Clase AsyncTask que establece la conexión entre la aplicación y el ESB.                   */
    /* Arquitectura REST. Obtenemos todos los tipos de restaurantes quee están almacenados en la */
    /* BD.                                                                                       */
    /*-------------------------------------------------------------------------------------------*/
    private class LoadWebServiceTypeRestaurantASYNC extends AsyncTask<TypeRestaurantPreferenceList, Integer, Boolean> {
        private List<Typerestaurant> typerestaurantList = new ArrayList<Typerestaurant>();
        private ArrayList<String> typeRestaurantSelected = new ArrayList<String>();

        @Override
        protected Boolean doInBackground(TypeRestaurantPreferenceList... params) {
            boolean resul = true;
            TypeRestaurantPreferenceList typeRestaurantPreferenceList = params[0];
            HttpClient httpClient = new DefaultHttpClient();
            HttpContext localContext = new BasicHttpContext();
            //String URL = "http://192.168.1.102:8085/informationService/typerestaurant";
            String URL = "http://"+ MainActivity.getDireccionIP() +":8085/informationService/typerestaurant";
            HttpGet del = new HttpGet(URL);
            try {
                HttpResponse resp = httpClient.execute(del, localContext);
                HttpEntity entity = resp.getEntity();
                String respStr = EntityUtils.toString(entity);
                JSONObject jsonObject = new JSONObject(respStr);
                JSONArray typerestaurantArray = jsonObject.getJSONArray("typerestaurant");

                for (int i = 0; i < typerestaurantArray.length(); i++) {
                    JSONObject typerestaurantJson = typerestaurantArray.getJSONObject(i);
                    Typerestaurant typerestaurant = new Typerestaurant();
                    typerestaurant.setId(typerestaurantJson.getString("id"));
                    typerestaurant.setTypeRestaurant(typerestaurantJson.getString("typeRestaurant"));
                    typerestaurantList.add(typerestaurant);
                }

                for (int i = 0; i < typeRestaurantPreferenceList.getTyperestaurantPreferenceArrayList().size(); i++) {
                    for (int j = 0; j < typerestaurantList.size(); j++) {
                        if (typeRestaurantPreferenceList.getTyperestaurantPreferenceArrayList().get(i).getIdtyperestaurant().equals
                                (typerestaurantList.get(j).getId().toString()))
                            typeRestaurantSelected.add(typerestaurantList.get(j).getTypeRestaurant());
                    }
                }
            } catch (Exception ex) {
                resul = false;
            }
            return resul;
        }

        @Override
        protected void onPostExecute(Boolean result) {
            if (result) {
                if(typeRestaurantSelected.size()==0)
                    typeRestaurantSelected.add("No hay tipos de restaurantes seleccionado.");

                cmbTypesRestaurant = (Spinner)findViewById(R.id.spinnerTypeRestaurant);
                ArrayAdapter<String> adaptador = new ArrayAdapter<String>(PreferenciasRestaurantes.this,
                        android.R.layout.simple_spinner_item, typeRestaurantSelected);
                adaptador.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                cmbTypesRestaurant.setAdapter(adaptador);
            }
        }
    }
}











