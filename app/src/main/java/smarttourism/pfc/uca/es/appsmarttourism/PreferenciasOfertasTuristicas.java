package smarttourism.pfc.uca.es.appsmarttourism;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.Spinner;
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
/* Clase que da funcionalidad a las vistas de preferencias de ofertas turísticas.                */
/*-----------------------------------------------------------------------------------------------*/
public class PreferenciasOfertasTuristicas extends AppCompatActivity {
    GeneralPreference generalPreference = new GeneralPreference();
    TypeTourismPreferenceList typeTourismPreferencesAux;
    Spinner cmbTypesTourism;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_preferences_touristoffer);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Bundle extras = getIntent().getExtras();
        generalPreference.setUsername(extras.getString("username"));
        //generalPreference.setWeather(extras.getByte("weather"));
        generalPreference.setOpenAir(extras.getByte("openAir"));
        generalPreference.setBooked(extras.getByte("booked"));
        generalPreference.setGuided(extras.getByte("guided"));
        generalPreference.setSchedule(extras.getByte("schedule"));

        typeTourismPreferencesAux = (TypeTourismPreferenceList) extras.getSerializable("typetourismPreferenceList");

        //Llamamos al servicio.
        LoadWebServiceTypeTourismASYNC loadWebServiceTypeTourismASYNC = new LoadWebServiceTypeTourismASYNC();
        loadWebServiceTypeTourismASYNC.execute(typeTourismPreferencesAux);

        actualizarVista();
    }

    //Precondiciones: Ninguna.
    //Postcondiciones: Actualizamos los valores de los checkbox que aparecen en la vista.
    public void actualizarVista() {
        /*CheckBox cbxTiempoMeteoroLogico = (CheckBox) findViewById(R.id.cbxTiempoMeteorologicoPrefTouristOffers);
        if (generalPreference.getWeather() == 1)
            cbxTiempoMeteoroLogico.setChecked(true);*/
        CheckBox cbxOpenAir = (CheckBox) findViewById(R.id.cbxOpenAirPrefTouristOffers);
        if (generalPreference.getOpenAir() == 1)
            cbxOpenAir.setChecked(true);
        CheckBox cbxBooked = (CheckBox) findViewById(R.id.cbxBookedPrefTouristOffers);
        if (generalPreference.getBooked() == 1)
            cbxBooked.setChecked(true);
        CheckBox cbxGuided = (CheckBox) findViewById(R.id.cbxGuidedPrefTouristOffers);
        if (generalPreference.getGuided() == 1)
            cbxGuided.setChecked(true);
        CheckBox cbxSchedule = (CheckBox) findViewById(R.id.cbxSchedulePrefTouristOffers);
        if (generalPreference.getSchedule() == 1)
            cbxSchedule.setChecked(true);
    }


    /*private HashMap<String, List<String>> returnGroupedChildItems(List<String>parentHeaderInformation, List<String> tipos){
        HashMap<String, List<String>> childContent = new HashMap<String, List<String>>();
        childContent.put(parentHeaderInformation.get(0), tipos);
        return childContent;
    }*/

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
                i = new Intent(this, UpdatePreferenciasOfertasTuristicas.class);
                i.putExtra("username", generalPreference.getUsername());
                i.putExtra("booked", generalPreference.getBooked());
                i.putExtra("guided", generalPreference.getGuided());
                i.putExtra("openAir", generalPreference.getOpenAir());
               // i.putExtra("weather", generalPreference.getWeather());
                i.putExtra("schedule", generalPreference.getSchedule());
                i.putExtra("typetourismPreferenceList", typeTourismPreferencesAux);
                startActivity(i);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /*-------------------------------------------------------------------------------------------*/
    /* Clase AsyncTask que establece la conexión entre la aplicación y el ESB.                   */
    /* Arquitectura REST. Obtenemos todos los tipos de turimos que están almacenados en la BD.   */
    /*-------------------------------------------------------------------------------------------*/
    private class LoadWebServiceTypeTourismASYNC extends AsyncTask<TypeTourismPreferenceList, Integer, Boolean> {
        private List<TypeTourism> typetourismList = new ArrayList<TypeTourism>();
        private ArrayList<String> typeTourismSelected = new ArrayList<String>();

        @Override
        protected Boolean doInBackground(TypeTourismPreferenceList... params) {
            boolean resul = true;
            TypeTourismPreferenceList typeTourismPreferenceList = params[0];
            HttpClient httpClient = new DefaultHttpClient();
            HttpContext localContext = new BasicHttpContext();
            //String URL = "http://192.168.1.102:8085/informationService/typetourism";
            String URL = "http://"+ MainActivity.getDireccionIP() + ":8085/informationService/typetourism";
            HttpGet del = new HttpGet(URL);
            try { //Establecemos la conexión
                HttpResponse resp = httpClient.execute(del, localContext);
                HttpEntity entity = resp.getEntity();
                String respStr = EntityUtils.toString(entity);
                JSONObject jsonObject = new JSONObject(respStr);
                JSONArray typetourismArray = jsonObject.getJSONArray("typetourism");

                //recogemos los datos.
                for (int i = 0; i < typetourismArray.length(); i++) {
                    JSONObject typetourismJson = typetourismArray.getJSONObject(i);
                    TypeTourism typeTourism = new TypeTourism();
                    typeTourism.setId(typetourismJson.getString("id"));
                    typeTourism.setTypetourism(typetourismJson.getString("typetourism"));
                    typetourismList.add(typeTourism);
                }

                for (int i = 0; i < typeTourismPreferenceList.getTypetourismPreferenceArrayList().size(); i++) {
                    for (int j = 0; j < typetourismList.size(); j++) {
                        if (typeTourismPreferenceList.getTypetourismPreferenceArrayList().get(i).getIdtypetourism().equals(typetourismList.get(j).getId().toString())) {
                            typeTourismSelected.add(typetourismList.get(j).getTypetourism());
                        }
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
                if(typeTourismSelected.size()==0)
                    typeTourismSelected.add("No hay tipos de turismo seleccionado.");

                cmbTypesTourism = (Spinner)findViewById(R.id.spinnerTypeTourism);
                ArrayAdapter<String> adaptador = new ArrayAdapter<String>(PreferenciasOfertasTuristicas.this,
                        android.R.layout.simple_spinner_item, typeTourismSelected);
                adaptador.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                cmbTypesTourism.setAdapter(adaptador);
            }
        }
    }
}









