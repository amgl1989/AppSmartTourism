package smarttourism.pfc.uca.es.appsmarttourism;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by AnaMari on 09/11/2015.
 */

/*-----------------------------------------------------------------------------------------------*/
/* Clase que permite actualizar las preferencias de las ofertas turísticas.                      */
/*-----------------------------------------------------------------------------------------------*/
public class UpdatePreferenciasOfertasTuristicas extends AppCompatActivity {
    GeneralPreference generalPreference = new GeneralPreference();
    TypeTourismPreferenceList typeTourismPreferencesAux;

    private static List<TypeTourism> typetourismList = new ArrayList<TypeTourism>();
    private static ArrayList<String> typeTourismSelected = new ArrayList<String>();

    Boolean flagTourism = false; //Flag que controla si se han editado o no los tipos de turismos seleccionado.
                                 //Solo se actualizará en la base de datos cuando el flag sea true.

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.update_preferences_touristoffer);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        flagTourism = false;
        Bundle extras = getIntent().getExtras();
        generalPreference.setUsername(extras.getString("username"));
        //generalPreference.setWeather(extras.getByte("weather"));
        generalPreference.setOpenAir(extras.getByte("openAir"));
        generalPreference.setBooked(extras.getByte("booked"));
        generalPreference.setGuided(extras.getByte("guided"));
        generalPreference.setSchedule(extras.getByte("schedule"));

        typeTourismPreferencesAux = (TypeTourismPreferenceList) extras.getSerializable("typetourismPreferenceList");

        CheckBox cbxBooked = (CheckBox) findViewById(R.id.cbxBookedPrefTOUp);
        cbxBooked.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });

        CheckBox cbxOpenAir = (CheckBox) findViewById(R.id.cbxOpenAirPrefTOUp);
        cbxOpenAir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });

        CheckBox cbxGuided = (CheckBox) findViewById(R.id.cbxGuidedPrefTOUp);
        cbxGuided.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });
        CheckBox cbxSchedule = (CheckBox) findViewById(R.id.cbxSchedulePrefTOUp);
        cbxSchedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });

        Button btnTypeTourism = (Button) findViewById(R.id.btnTypesTourismPrefTOUp);
        btnTypeTourism.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flagTourism = true;
                LoadWebServiceTypeTourismASYNC loadWebServiceTypeTourismASYNC = new LoadWebServiceTypeTourismASYNC();
                loadWebServiceTypeTourismASYNC.execute(typeTourismPreferencesAux);
            }
        });
        actualizarVista();
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    //Precondiciones: Recibe la lista de tipos de turismo seleccionadas.
    //Postcondiciones: Actualiza la variable typeTourismSelected con la lista recibida por parámetros.
    public static void setTypeTourismSelected(ArrayList<String> typeTourismSelected) {
        UpdatePreferenciasOfertasTuristicas.typeTourismSelected = typeTourismSelected;
    }

    //Precondiciones: Ninguno.
    //Postcondiciones: Actualizamos los valores en la vista.
    public void actualizarVista() {
        CheckBox cbxOpenAir = (CheckBox) findViewById(R.id.cbxOpenAirPrefTOUp);
        if (generalPreference.getOpenAir() == 1)
            cbxOpenAir.setChecked(true);
        CheckBox cbxBooked = (CheckBox) findViewById(R.id.cbxBookedPrefTOUp);
        if (generalPreference.getBooked() == 1)
            cbxBooked.setChecked(true);
        CheckBox cbxGuided = (CheckBox) findViewById(R.id.cbxGuidedPrefTOUp);
        if (generalPreference.getGuided() == 1)
            cbxGuided.setChecked(true);
        CheckBox cbxSchedule = (CheckBox) findViewById(R.id.cbxSchedulePrefTOUp);
        if (generalPreference.getSchedule() == 1)
            cbxSchedule.setChecked(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_save, menu);
        return true;  /** true -> el menú ya está visible */
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.accion_save:
                GeneralPreference generalPreferenceNew = new GeneralPreference();
                generalPreferenceNew.setUsername(generalPreference.getUsername());
             /*   CheckBox cbxTiempoMeteorologico = (CheckBox) findViewById(R.id.cbxTiempoMeteorologicoPrefTOUp);
                generalPreferenceNew.setWeather(booleanToByte(cbxTiempoMeteorologico.isChecked()));*/
                CheckBox cbxBooked = (CheckBox) findViewById(R.id.cbxBookedPrefTOUp);
                generalPreferenceNew.setBooked(booleanToByte(cbxBooked.isChecked()));
                CheckBox cbxGuided = (CheckBox) findViewById(R.id.cbxGuidedPrefTOUp);
                generalPreferenceNew.setGuided(booleanToByte(cbxGuided.isChecked()));
                CheckBox cbxOpenAir = (CheckBox) findViewById(R.id.cbxOpenAirPrefTOUp);
                generalPreferenceNew.setOpenAir(booleanToByte(cbxOpenAir.isChecked()));
                CheckBox cbxSchedule = (CheckBox) findViewById(R.id.cbxSchedulePrefTOUp);
                generalPreferenceNew.setSchedule(booleanToByte(cbxSchedule.isChecked()));
                generalPreferenceNew.setTypeTourismPreferenceList(convertListStringToTypeTourismPreferenceList(typeTourismSelected, generalPreferenceNew.getUsername()));
                LoadWebServiceUpdateTypeTourismASYNC loadWebServiceUpdateTypeTourismASYNC = new LoadWebServiceUpdateTypeTourismASYNC();
                loadWebServiceUpdateTypeTourismASYNC.execute(generalPreferenceNew);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    //Precondiciones: Recibe el nombre de usuario y la lista de tipos de turismo que ha seleccionado dicho usuario.
    //Postcondiciones: Convierte dicha lista en List<TypeTourismPreference>
    private List<TypeTourismPreference> convertListStringToTypeTourismPreferenceList(ArrayList<String> typeTourismSelected, String username) {
        List<TypeTourismPreference> typeTourismPreferenceList = new ArrayList<TypeTourismPreference>();
        for (int i = 0; i < typeTourismSelected.size(); i++) {
            for (int j = 0; j < typetourismList.size(); j++) {
                if (typeTourismSelected.get(i).equals(typetourismList.get(j).getTypetourism())) {
                    TypeTourismPreference typeTourismPreference = new TypeTourismPreference(typetourismList.get(j).getId(), username);
                    typeTourismPreferenceList.add(typeTourismPreference);
                }
            }
        }
        return typeTourismPreferenceList;
    }

    //Preconcidiciones: Recibe un bool.
    //Postcondiciones: Devuelve 1 si ha recibido true; y 0 si ha recibido false.
    public static Byte booleanToByte(boolean bool){
        if(bool==true)
            return Byte.valueOf("1");
        else
            return Byte.valueOf("0");
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "UpdatePreferenciasOfertasTuristicas Page", // TODO: Define a title for the content shown.
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
                "UpdatePreferenciasOfertasTuristicas Page", // TODO: Define a title for the content shown.
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
    /* Arquitectura REST. Obtiene el listado de los tipos de turismos almacenados.               */
    /*-------------------------------------------------------------------------------------------*/
    private class LoadWebServiceTypeTourismASYNC extends AsyncTask<TypeTourismPreferenceList, Integer, Boolean> {
        @Override
        protected Boolean doInBackground(TypeTourismPreferenceList... params) {
            boolean resul = true;
            TypeTourismPreferenceList typeTourismPreferenceList = params[0];
            HttpClient httpClient = new DefaultHttpClient();
            HttpContext localContext = new BasicHttpContext();
            //String URL = "http://192.168.1.102:8085/informationService/typetourism";
            String URL = "http://"+ MainActivity.getDireccionIP() + ":8085/informationService/typetourism";
            HttpGet del = new HttpGet(URL);
            try {
                HttpResponse resp = httpClient.execute(del, localContext);
                HttpEntity entity = resp.getEntity();
                String respStr = EntityUtils.toString(entity);
                JSONObject jsonObject = new JSONObject(respStr);
                JSONArray typetourismArray = jsonObject.getJSONArray("typetourism");

                typetourismList.clear();
                for (int i = 0; i < typetourismArray.length(); i++) {
                    JSONObject typetourismJson = typetourismArray.getJSONObject(i);
                    TypeTourism typeTourism = new TypeTourism();
                    typeTourism.setId(typetourismJson.getString("id"));
                    typeTourism.setTypetourism(typetourismJson.getString("typetourism"));
                    typetourismList.add(typeTourism);
                }

                for (int i = 0; i < typeTourismPreferenceList.getTypetourismPreferenceArrayList().size(); i++) {
                    for (int j = 0; j < typetourismList.size(); j++) {
                        if (typeTourismPreferenceList.getTypetourismPreferenceArrayList().get(i).getIdtypetourism().equals(typetourismList.get(j).getId().toString()))
                            typeTourismSelected.add(typetourismList.get(j).getTypetourism());
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
                FragmentManager fragmentManager = getSupportFragmentManager();
                ArrayList<String> typetourismString = new ArrayList<String>();
                for (int i = 0; i < typetourismList.size(); i++)
                    typetourismString.add(typetourismList.get(i).getTypetourism());

                Bundle args = new Bundle();
                args.putStringArrayList("typetourism", typetourismString);
                args.putStringArrayList("typetourismselected", typeTourismSelected);
                DialogoTypeTourismPreferences dialogo = new DialogoTypeTourismPreferences();
                dialogo.setArguments(args);
                dialogo.show(fragmentManager, "TiposTurismo");
            }
        }
    }

    /*-------------------------------------------------------------------------------------------*/
    /* Clase AsyncTask que establece la conexión entre la aplicación y el ESB.                   */
    /* Arquitectura REST. Actualiza los valores de las preferencias de las ofertas turísticas    */
    /* con los valores seleccionados por el usuario.                                             */
    /*-------------------------------------------------------------------------------------------*/
    private class LoadWebServiceUpdateTypeTourismASYNC extends AsyncTask<GeneralPreference, Integer, Boolean> {
        @Override
        protected Boolean doInBackground(GeneralPreference... params) {
            boolean resul = true;
            HttpClient httpClient = new DefaultHttpClient();
            //String URL = "http://192.168.1.102:8084/userPreferencesServices/updateUserPreferencesServices?username=";
            String URL = "http://"+ MainActivity.getDireccionIP() + ":8084/userPreferencesServices/updateUserPreferencesServices?username=";
            GeneralPreference generalPreferenceNew = new GeneralPreference(params[0]);
            try {
                generalPreference = generalPreferenceNew;
                JSONObject dato = new JSONObject();
                dato.put("username", generalPreferenceNew.getUsername());
                dato.put("booked", generalPreferenceNew.getBooked());
                dato.put("guided", generalPreferenceNew.getGuided());
                dato.put("openAir", generalPreferenceNew.getOpenAir());
                dato.put("weather", 0);
                dato.put("schedule", generalPreferenceNew.getSchedule());

                JSONArray jsonArray = new JSONArray();
                for (int i=0; i<generalPreferenceNew.getTypeTourismPreferenceList().size(); i++) {
                    JSONObject typeTourismPreferenceObj = new JSONObject();
                    typeTourismPreferenceObj.put("username", generalPreferenceNew.getTypeTourismPreferenceList().get(i).getUsername());
                    typeTourismPreferenceObj.put("idtypetourism", generalPreferenceNew.getTypeTourismPreferenceList().get(i).getIdtypetourism());
                    jsonArray.put(typeTourismPreferenceObj);
                }
                if(flagTourism == true)
                    dato.put("typetourismPreferenceList", jsonArray);
                String urlJSON  = dato.toString();
                urlJSON = urlJSON.replaceAll("\"","");
                URL = URL + generalPreferenceNew.getUsername();

                HttpPut put = new HttpPut(URL);
                put.setHeader("content-type", "application/json");

                StringEntity entity = new StringEntity(urlJSON);
                put.setEntity(entity);

                HttpResponse resp = httpClient.execute(put);
                String respStr = EntityUtils.toString(resp.getEntity());

            } catch (Exception ex) {
                resul = false;
            }
            return resul;
        }

        @Override
        protected void onPostExecute(Boolean result) {
            if (result) {
                Toast toast1 = Toast.makeText(getApplicationContext(),
                                "Preferencias actualizadas correctamente.", Toast.LENGTH_SHORT);
                toast1.show();
            }else{
                Toast toast1 = Toast.makeText(getApplicationContext(),
                        "No se ha podido actualizar las preferencias.", Toast.LENGTH_SHORT);
                toast1.show();
            }
            Intent i = new Intent(UpdatePreferenciasOfertasTuristicas.this, MyAccount.class);
            startActivity(i);
        }
    }

    /*-------------------------------------------------------------------------------------------*/
    /* Clase que construye el cuadro de diálogo que muestra los tipos de turismos existente y que*/
    /* el usuario seleccionará aquellos que quiera que se apliquen en las adaptaciones.          */
    /*-------------------------------------------------------------------------------------------*/
    private static class DialogoTypeTourismPreferences extends DialogFragment {
        ArrayList<String> typetourismStringList = new ArrayList<String>();
        ArrayList<String> typetourismSelectedStringList = new ArrayList<String>();

        /*public static DialogoTypeTourismPreferences newInstance(String[] typetourismList, ArrayList<String> typeTourismSelected) {
            DialogoTypeTourismPreferences dialogo = new DialogoTypeTourismPreferences();
            Bundle args = new Bundle();
            args.putStringArray("typetourism", typetourismList);
            args.putStringArrayList("typetourismselected", typeTourismSelected);
            dialogo.setArguments(args);
            return dialogo;
        }*/

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            typetourismStringList = getArguments().getStringArrayList("typetourism");
            final CharSequence[] typetourismSequence = new CharSequence[typetourismStringList.size()];
            for (int i = 0; i < typetourismStringList.size(); i++)
                typetourismSequence[i] = typetourismStringList.get(i);

            final ArrayList<String> typeTourismSelectedAux = new ArrayList<String>();
            typetourismSelectedStringList = getArguments().getStringArrayList("typetourismselected");
            final boolean[] optionSelected = new boolean[typetourismSequence.length];
            boolean flag;
            for (int i = 0; i < typetourismSequence.length; i++) {
                flag = false;
                for (int j = 0; j < typetourismSelectedStringList.size(); j++) {
                    if (typetourismSequence[i].equals(typetourismSelectedStringList.get(j))) {
                        optionSelected[i] = true;
                        flag = true;
                    }
                }
                if (flag == false)
                    optionSelected[i] = false;
            }

            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle("Tipos de turismo preferidos")
                    .setMultiChoiceItems(typetourismSequence, optionSelected, new DialogInterface.OnMultiChoiceClickListener() {
                        public void onClick(DialogInterface dialog, int item, boolean isChecked) {
                            if (isChecked)
                               optionSelected[item] = true;
                            else {
                                if(optionSelected[item] == true)
                                    optionSelected[item] = false;
                            }
                        }
                    }).setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            }).setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    for(int i=0; i<optionSelected.length;i++) {
                        if (optionSelected[i] == true && !typeTourismSelectedAux.contains(typetourismStringList.get(i))) {
                            typeTourismSelectedAux.add(typetourismStringList.get(i));
                        } else {
                            if (optionSelected[i] == false && typeTourismSelectedAux.contains(typetourismStringList.get(i))) {
                                typeTourismSelectedAux.remove(typetourismStringList.get(i));
                            }
                        }
                    }
                    UpdatePreferenciasOfertasTuristicas.setTypeTourismSelected(typeTourismSelectedAux);
                    dialog.dismiss();
                }
            });
            return builder.create();
        }
    }
}









