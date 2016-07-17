package smarttourism.pfc.uca.es.appsmarttourism;

import android.content.Intent;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import com.google.android.gms.common.api.GoogleApiClient;
import org.apache.http.HttpEntity;
import org.apache.http.client.HttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * Created by AnaMari on 16/03/2016.
 */

/*-----------------------------------------------------------------------------------------------*/
/* Clase que da funcionalidad a la opción "Mi cuenta" del menú.                                  */
/*-----------------------------------------------------------------------------------------------*/
public class MyAccount extends AppCompatActivity {
    public ListView lstOpciones;
    private GoogleApiClient client;
    String username = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.restaurantes);
        username = MainActivity.getUsername();

        Resources res = getResources();
        final List<String> datos = new ArrayList<String>();
        datos.add(res.getString(R.string.perfil));
        datos.add(res.getString(R.string.prefrestaurantes));
        datos.add(res.getString(R.string.prefofertasturisticas));
        AdaptadorMyAccount adaptador =  new AdaptadorMyAccount(MyAccount.this,datos);
        lstOpciones = (ListView) findViewById(R.id.listViewRestaurante);
        lstOpciones.setAdapter(adaptador);
        lstOpciones.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> a, View v, int position, long id) {
                switch (position) {
                    case 0:
                        LoadWebServiceUserASYNC loadWebServiceUserASYNC = new LoadWebServiceUserASYNC();
                        loadWebServiceUserASYNC.execute(username);
                        break;
                    case 1:
                        LoadWebServiceUserPreferenceASYNC loadWebServiceUserPreferenceASYNC = new LoadWebServiceUserPreferenceASYNC();
                        loadWebServiceUserPreferenceASYNC.execute(username, "1");
                        break;
                    case 2:
                        loadWebServiceUserPreferenceASYNC = new LoadWebServiceUserPreferenceASYNC();
                        loadWebServiceUserPreferenceASYNC.execute(username, "2");
                        break;
                    default:
                        break;
                }
            }
        });
    }

    /*-------------------------------------------------------------------------------------------*/
    /* Clase AsyncTask que establece la conexión entre la aplicación y el ESB.                   */
    /* Arquitectura REST. Obtiene toda la información del usuario actual.                        */
    /*-------------------------------------------------------------------------------------------*/
    private class LoadWebServiceUserASYNC extends AsyncTask<String, Integer, Boolean> {
        private User user = new User();

        @Override
        protected Boolean doInBackground(String... params) {
            boolean resul = true;
            HttpClient httpClient = new DefaultHttpClient();
            HttpContext localContext = new BasicHttpContext();

            String username = params[0];
            //String URL = "http://192.168.1.102:8083/userServices/selectUserServices?username=" + username;
            String URL = "http://" + MainActivity.getDireccionIP() + ":8083/userServices/selectUserServices?username=" + username;
            HttpGet del = new HttpGet(URL);
            try {
                HttpResponse resp = httpClient.execute(del, localContext);
                HttpEntity entity = resp.getEntity();
                String respStr = EntityUtils.toString(entity);
                JSONObject respJSON = new JSONObject(respStr);

                user.setUsername(username);
                if(respStr.contains("name"))
                    user.setName(respJSON.getString("name"));
                if(respStr.contains("surname"))
                    user.setSurname(respJSON.getString("surname"));
                if(respStr.contains("password"))
                    user.setPassword(respJSON.getString("password"));
                if(respStr.contains("seed"))
                    user.setSeed(respJSON.getString("seed"));
                if(respStr.contains("countrySource"))
                    user.setCountrySource(respJSON.getString("countrySource"));
                if(respStr.contains("age"))
                    user.setAge(respJSON.getInt("age"));
                if(respStr.contains("dateBorn")){
                    DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:sss.SSSZ");
                    String dateInString = respJSON.get("dateBorn").toString();
                    Date date = formatter.parse(dateInString);
                    user.setDateBorn(date);
                }
            } catch (Exception ex) {
                resul = false;
            }
            return resul;
        }

        @Override
        protected void onPostExecute(Boolean result) {
            if (result) {
                Intent i = new Intent(MyAccount.this, Perfil.class);
                i.putExtra("username", user.getUsername());
                i.putExtra("password", user.getPassword());
                i.putExtra("seed", user.getSeed());
                i.putExtra("name", user.getName());
                i.putExtra("surname", user.getSurname());
                i.putExtra("age",user.getAge());
                i.putExtra("dateBorn", user.getDateBorn());
                i.putExtra("countrySource", user.getCountrySource());
                startActivity(i);
            }
        }
    }


    /*-------------------------------------------------------------------------------------------*/
    /* Clase AsyncTask que establece la conexión entre la aplicación y el ESB.                   */
    /* Arquitectura REST. Obtiene toda la información de las preferencias del usuario.           */
    /*-------------------------------------------------------------------------------------------*/
    private class LoadWebServiceUserPreferenceASYNC extends AsyncTask<String, Integer, Boolean> {
        private GeneralPreference generalPreference = new GeneralPreference();
        private String position = "";
        @Override
        protected Boolean doInBackground(String... params) {
            position = params[1];
            boolean resul = true;
            HttpClient httpClient = new DefaultHttpClient();
            HttpContext localContext = new BasicHttpContext();

            String username = params[0];
            //String URL = "http://192.168.1.102:8084/userPreferencesServices/userPreference?username=" + username;
            String URL = "http://" + MainActivity.getDireccionIP() + ":8084/userPreferencesServices/userPreference?username=" + username;
            HttpGet del = new HttpGet(URL);
            try {
                HttpResponse resp = httpClient.execute(del, localContext);
                HttpEntity entity = resp.getEntity();
                String respStr = EntityUtils.toString(entity);
                JSONObject respJSON = new JSONObject(respStr);

                if(respStr.contains("username"))
                    generalPreference.setUsername(username);
                if(respStr.contains("booked")) {
                    String s = respJSON.getString("booked");
                    generalPreference.setBooked(Byte.parseByte(s));
                }
                if(respStr.contains("cleaning"))
                    generalPreference.setCleaning(Byte.parseByte(respJSON.getString("cleaning")));
                if(respStr.contains("freshfood"))
                    generalPreference.setFreshFood(Byte.parseByte(respJSON.getString("freshfood")));
                if(respStr.contains("guided"))
                    generalPreference.setGuided(Byte.parseByte(respJSON.getString("guided")));
                if(respStr.contains("openAir"))
                    generalPreference.setOpenAir(Byte.parseByte(respJSON.getString("openAir")));
                if(respStr.contains("terrace"))
                    generalPreference.setTerrace(Byte.parseByte(respJSON.getString("terrace")));
                /*if(respStr.contains("weather"))
                    generalPreference.setWeather(Byte.parseByte(respJSON.getString("weather")));*/
                if(respStr.contains("schedule"))
                    generalPreference.setSchedule(Byte.parseByte(respJSON.getString("schedule")));

                if(respStr.contains("typefoodPreferenceList")) {
                    List<TypeFoodPreference> typeFoodPreferenceList = new ArrayList<TypeFoodPreference>();
                    JSONArray jsonArrayTypeFood = respJSON.getJSONArray("typefoodPreferenceList");
                    for (int i = 0; i < jsonArrayTypeFood.length(); i++) {
                        TypeFoodPreference typeFoodPreference = new TypeFoodPreference(
                                jsonArrayTypeFood.getJSONObject(i).getString("idtypefood").toString(),
                                jsonArrayTypeFood.getJSONObject(i).getString("username").toString());
                        typeFoodPreferenceList.add(typeFoodPreference);
                    }
                    generalPreference.setTypeFoodPreferenceList(typeFoodPreferenceList);
                }

                if(respStr.contains("typerestaurantPreferenceList")) {
                    List<TypeRestaurantPreference> typerestaurantPreferenceList = new ArrayList<TypeRestaurantPreference>();
                    JSONArray jsonArrayTypeRest = respJSON.getJSONArray("typerestaurantPreferenceList");
                    for (int i = 0; i < jsonArrayTypeRest.length(); i++) {
                        TypeRestaurantPreference typeRestaurantPreference = new TypeRestaurantPreference(
                                jsonArrayTypeRest.getJSONObject(i).getString("idtyperestaurant").toString(),
                                jsonArrayTypeRest.getJSONObject(i).getString("username").toString());
                        typerestaurantPreferenceList.add(typeRestaurantPreference);
                    }
                    generalPreference.setTypeRestaurantPreferenceList(typerestaurantPreferenceList);
                }

                if(respStr.contains("typetourismPreferenceList")) {
                    List<TypeTourismPreference> typeTourismPreferenceList = new ArrayList<TypeTourismPreference>();
                    JSONArray jsonArrayTypeTourism = respJSON.getJSONArray("typetourismPreferenceList");
                    for (int i = 0; i < jsonArrayTypeTourism.length(); i++) {
                        TypeTourismPreference typeTourismPreference = new TypeTourismPreference(
                                jsonArrayTypeTourism.getJSONObject(i).getString("idtypetourism").toString(),
                                jsonArrayTypeTourism.getJSONObject(i).getString("username").toString());
                        typeTourismPreferenceList.add(typeTourismPreference);
                    }
                    generalPreference.setTypeTourismPreferenceList(typeTourismPreferenceList);
                }
            } catch (Exception ex) {
                resul = false;
            }
            return resul;
        }

        @Override
        protected void onPostExecute(Boolean result) {
            if (result) {
                if(position.equals("1")) { //Si el usuario selecciona acceder a las preferencias de los restaurantes.
                    Intent i = new Intent(MyAccount.this, PreferenciasRestaurantes.class);
                    i.putExtra("username", generalPreference.getUsername());
                    i.putExtra("cleaning", generalPreference.getCleaning());
                    i.putExtra("freshfood", generalPreference.getFreshFood());
                    i.putExtra("terrace", generalPreference.getTerrace());
                   // i.putExtra("weather", generalPreference.getWeather());
                    TypeFoodPreferenceList typeFoodPreferenceListFinal = new TypeFoodPreferenceList(generalPreference.getTypeFoodPreferenceList());
                    i.putExtra("typefoodPreferenceList", typeFoodPreferenceListFinal);
                    TypeRestaurantPreferenceList typeRestaurantPreferenceListFinal = new TypeRestaurantPreferenceList(generalPreference.getTypeRestaurantPreferenceList());
                    i.putExtra("typerestaurantPreferenceList",typeRestaurantPreferenceListFinal);
                    startActivity(i);
                }
                if(position.equals("2")){ //Si el usuario selecciona acceder a las preferencias de las ofertas turísticas.
                    Intent i = new Intent(MyAccount.this, PreferenciasOfertasTuristicas.class);
                    i.putExtra("username", generalPreference.getUsername());
                    i.putExtra("booked", generalPreference.getBooked());
                    i.putExtra("guided", generalPreference.getGuided());
                    i.putExtra("openAir", generalPreference.getOpenAir());
                   // i.putExtra("weather", generalPreference.getWeather());
                    i.putExtra("schedule", generalPreference.getSchedule());
                    TypeTourismPreferenceList typeTourismPreferenceListFinal = new TypeTourismPreferenceList(generalPreference.getTypeTourismPreferenceList());
                    i.putExtra("typetourismPreferenceList", typeTourismPreferenceListFinal);
                    startActivity(i);
                }
            }
        }
    }
}

