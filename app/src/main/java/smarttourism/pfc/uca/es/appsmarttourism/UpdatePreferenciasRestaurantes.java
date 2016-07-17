package smarttourism.pfc.uca.es.appsmarttourism;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
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
 * Created by AnaMari on 18/03/2016.
 */

/*-----------------------------------------------------------------------------------------------*/
/* Clase que permite actualizar las preferencias de los restaurantes.                            */
/*-----------------------------------------------------------------------------------------------*/
public class UpdatePreferenciasRestaurantes extends AppCompatActivity {
    GeneralPreference generalPreference = new GeneralPreference();
    TypeFoodPreferenceList typeFoodPreferencesAux;
    TypeRestaurantPreferenceList typeRestaurantPreferencesAux;
    private GoogleApiClient client;

    private static List<Typefood> typefoodList = new ArrayList<Typefood>();
    private static List<Typerestaurant> typerestaurantList = new ArrayList<Typerestaurant>();
    private static ArrayList<String> typeFoodSelected = new ArrayList<String>();
    private static ArrayList<String> typeRestaurantSelected = new ArrayList<String>();

    //Flag que controla si se han editado los valores de los cuadros de diálogos (Tipos de restaurantes y tipos de comida servida).
    //Solo se actualizará en la base de datos cuando el flag sea true.
    Boolean flagFood = false, flagRestaurant = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.update_preferences_restaurant);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        flagFood = false;
        flagRestaurant = false;
        Bundle extras = getIntent().getExtras();
        generalPreference.setUsername(extras.getString("username"));
       // generalPreference.setWeather(extras.getByte("weather"));
        generalPreference.setTerrace(extras.getByte("terrace"));
        generalPreference.setFreshFood(extras.getByte("freshfood"));
        generalPreference.setCleaning(extras.getByte("cleaning"));

        typeFoodPreferencesAux = (TypeFoodPreferenceList) extras.getSerializable("typefoodPreferenceList");
        typeRestaurantPreferencesAux = (TypeRestaurantPreferenceList) extras.getSerializable("typerestaurantPreferenceList");


        Button btnTypeFood = (Button) findViewById(R.id.btnTypesFoodprefRestUpd);
        btnTypeFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flagFood = true;
                LoadWebServiceTypeFoodASYNC loadWebServiceTypeFoodASYNC = new LoadWebServiceTypeFoodASYNC();
                loadWebServiceTypeFoodASYNC.execute(typeFoodPreferencesAux);
            }
        });

        Button btnTypeRestaurant = (Button) findViewById(R.id.btnTypesRestaurantprefRestUpd);
        btnTypeRestaurant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flagRestaurant = true;
                LoadWebServiceTypeRestaurantASYNC loadWebServiceTypeRestaurantASYNC = new LoadWebServiceTypeRestaurantASYNC();
                loadWebServiceTypeRestaurantASYNC.execute(typeRestaurantPreferencesAux);
            }
        });

        CheckBox cbxTerraza = (CheckBox) findViewById(R.id.cbxPoseeTerrazaprefRestUpd);
        cbxTerraza.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });

        CheckBox cbxFreshFood = (CheckBox) findViewById(R.id.cbxFreshFoodprefRestUpd);
        cbxFreshFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });

        CheckBox cbxCleaning = (CheckBox) findViewById(R.id.cbxCleaningprefRestUpd);
        cbxCleaning.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });

        actualizarVista();
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    //Precondiciones: Recibe la lista de tipos de comida seleccionadas.
    //Postcondiciones: Actualiza la variable typeFoodSelected con la lista recibida por parámetros.
    public static void setTypeFoodSelected(ArrayList<String> typeFoodSelected) {
        UpdatePreferenciasRestaurantes.typeFoodSelected = typeFoodSelected;
    }

    //Precondiciones: Recibe la lista de tipos de restaurantes seleccionadas.
    //Postcondiciones: Actualiza la variable typeRestaurantSelected con la lista recibida por parámetros.
    public static void setTypeRestaurantSelected(ArrayList<String> typeRestaurantSelected) {
        UpdatePreferenciasRestaurantes.typeRestaurantSelected = typeRestaurantSelected;
    }

    //Precondiciones: Ninguno.
    //Postcondiciones: Actualizamos los valores en la vista.
    public void actualizarVista() {
        CheckBox cbxTerraza = (CheckBox) findViewById(R.id.cbxPoseeTerrazaprefRestUpd);
        if (generalPreference.getTerrace() == 1)
            cbxTerraza.setChecked(true);
        CheckBox cbxFreshFood = (CheckBox) findViewById(R.id.cbxFreshFoodprefRestUpd);
        if (generalPreference.getFreshFood() == 1)
            cbxFreshFood.setChecked(true);
        CheckBox cbxCleaning = (CheckBox) findViewById(R.id.cbxCleaningprefRestUpd);
        if (generalPreference.getCleaning() == 1)
            cbxCleaning.setChecked(true);
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
                CheckBox cbxTerraza = (CheckBox) findViewById(R.id.cbxPoseeTerrazaprefRestUpd);
                generalPreferenceNew.setTerrace(booleanToByte(cbxTerraza.isChecked()));
                CheckBox cbxFreshFoodPref= (CheckBox) findViewById(R.id.cbxFreshFoodprefRestUpd);
                generalPreferenceNew.setFreshFood(booleanToByte(cbxFreshFoodPref.isChecked()));
                CheckBox cbxCleaningPref= (CheckBox) findViewById(R.id.cbxCleaningprefRestUpd);
                generalPreferenceNew.setCleaning(booleanToByte(cbxCleaningPref.isChecked()));
                generalPreferenceNew.setTypeRestaurantPreferenceList(convertListStringToTypeRestaurantPreferenceList(typeRestaurantSelected, generalPreferenceNew.getUsername()));
                generalPreferenceNew.setTypeFoodPreferenceList(convertListStringToTypeFoodPreferenceList(typeFoodSelected, generalPreferenceNew.getUsername()));
                LoadWebServiceUpdatePreferencesRestaurantASYNC loadWebServiceUpdatePreferencesRestaurantASYNC = new LoadWebServiceUpdatePreferencesRestaurantASYNC();
                loadWebServiceUpdatePreferencesRestaurantASYNC.execute(generalPreferenceNew);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    //Precondiciones: Recibe el nombre de usuario y la lista de tipos de restaurantes que ha seleccionado dicho usuario.
    //Postcondiciones: Convierte dicha lista en List<TypeRestaurantPreference>
    private List<TypeRestaurantPreference> convertListStringToTypeRestaurantPreferenceList(ArrayList<String> typeRestaurantSelected, String username) {
        List<TypeRestaurantPreference> typeRestaurantPreferenceList = new ArrayList<TypeRestaurantPreference>();
        for (int i = 0; i < typeRestaurantSelected.size(); i++) {
            for (int j = 0; j < typerestaurantList.size(); j++) {
                if (typeRestaurantSelected.get(i).equals(typerestaurantList.get(j).getTypeRestaurant())) {
                    TypeRestaurantPreference typeRestaurantPreference = new TypeRestaurantPreference(typerestaurantList.get(j).getId(), username);
                    typeRestaurantPreferenceList.add(typeRestaurantPreference);
                }
            }
        }
        return typeRestaurantPreferenceList;
    }

    //Precondiciones: Recibe el nombre de usuario y la lista de tipos de comidas que ha seleccionado dicho usuario.
    //Postcondiciones: Convierte dicha lista en List<TypeFoodPreference>
    private List<TypeFoodPreference> convertListStringToTypeFoodPreferenceList(ArrayList<String> typeFoodSelected, String username) {
        List<TypeFoodPreference> typeFoodPreferenceList = new ArrayList<TypeFoodPreference>();
        for (int i = 0; i < typeFoodSelected.size(); i++) {
            for (int j = 0; j < typefoodList.size(); j++) {
                if (typeFoodSelected.get(i).equals(typefoodList.get(j).getTypefood())) {
                    TypeFoodPreference typeFoodPreference = new TypeFoodPreference(typefoodList.get(j).getId(), username);
                    typeFoodPreferenceList.add(typeFoodPreference);
                }
            }
        }
        return typeFoodPreferenceList;
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
    /* Arquitectura REST. Obtiene el listado de los tipos de comidas servidos que están          */
    /* almacenados.                                                                              */
    /*-------------------------------------------------------------------------------------------*/
    private class LoadWebServiceTypeFoodASYNC extends AsyncTask<TypeFoodPreferenceList, Integer, Boolean> {
        private ArrayList<String> typeFoodSelected = new ArrayList<String>();

        @Override
        protected Boolean doInBackground(TypeFoodPreferenceList... params) {
            boolean resul = true;
            TypeFoodPreferenceList typeFoodPreferenceList = params[0];
            HttpClient httpClient = new DefaultHttpClient();
            HttpContext localContext = new BasicHttpContext();
            //String URL = "http://192.168.1.102:8085/informationService/typefood";
            String URL = "http://" + MainActivity.getDireccionIP() +":8085/informationService/typefood";
            HttpGet del = new HttpGet(URL);
            try {
                HttpResponse resp = httpClient.execute(del, localContext);
                HttpEntity entity = resp.getEntity();
                String respStr = EntityUtils.toString(entity);
                JSONObject jsonObject = new JSONObject(respStr);
                JSONArray typefoodArray = jsonObject.getJSONArray("typefood");

                typefoodList.clear();
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
                FragmentManager fragmentManager = getSupportFragmentManager();
                ArrayList<String> typefoodString = new ArrayList<String>();
                for (int i = 0; i < typefoodList.size(); i++)
                    typefoodString.add(typefoodList.get(i).getTypefood());

                Bundle args = new Bundle();
                args.putStringArrayList("typefood", typefoodString);
                args.putStringArrayList("typefoodselected", typeFoodSelected);
                DialogoTypeFoodPreferences dialogo = new DialogoTypeFoodPreferences();
                dialogo.setArguments(args);
                dialogo.show(fragmentManager, "TiposComida");
            }
        }
    }

    /*-------------------------------------------------------------------------------------------*/
    /* Clase AsyncTask que establece la conexión entre la aplicación y el ESB.                   */
    /* Arquitectura REST. Obtiene el listado de los tipos de restaurantes que están almacenados. */
    /*-------------------------------------------------------------------------------------------*/
    private class LoadWebServiceTypeRestaurantASYNC extends AsyncTask<TypeRestaurantPreferenceList, Integer, Boolean> {
        private ArrayList<String> typeRestaurantSelected = new ArrayList<String>();

        @Override
        protected Boolean doInBackground(TypeRestaurantPreferenceList... params) {
            boolean resul = true;
            TypeRestaurantPreferenceList typeRestaurantPreferenceList = params[0];
            HttpClient httpClient = new DefaultHttpClient();
            HttpContext localContext = new BasicHttpContext();
            //String URL = "http://192.168.1.102:8085/informationService/typerestaurant";
            String URL = "http://"+ MainActivity.getDireccionIP() + ":8085/informationService/typerestaurant";
            HttpGet del = new HttpGet(URL);
            try {
                HttpResponse resp = httpClient.execute(del, localContext);
                HttpEntity entity = resp.getEntity();
                String respStr = EntityUtils.toString(entity);
                JSONObject jsonObject = new JSONObject(respStr);
                JSONArray typerestaurantArray = jsonObject.getJSONArray("typerestaurant");

                typerestaurantList.clear();
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
                FragmentManager fragmentManager = getSupportFragmentManager();
                ArrayList<String> typerestaurantString = new ArrayList<String>();
                for(int i=0; i<typerestaurantList.size();i++)
                    typerestaurantString.add(typerestaurantList.get(i).getTypeRestaurant());

                Bundle args = new Bundle();
                args.putStringArrayList("typerestaurant", typerestaurantString);
                args.putStringArrayList("typerestaurantselected", typeRestaurantSelected);
                DialogoTypeRestaurantPreferences dialogo = new DialogoTypeRestaurantPreferences();
                dialogo.setArguments(args);
                dialogo.show(fragmentManager, "TiposRestaurantes");
            }
        }
    }

    /*-------------------------------------------------------------------------------------------*/
    /* Clase que construye el cuadro de diálogo que muestra los tipos de comida servidos, que    */
    /* están almacenados en la base de datos y que el usuario seleccionará aquellos que quiera   */
    /* que se apliquen en las adaptaciones.                                                      */
    /*-------------------------------------------------------------------------------------------*/
    public static class DialogoTypeFoodPreferences extends android.support.v4.app.DialogFragment {
        ArrayList<String> typefoodStringList = new ArrayList<String>();
        ArrayList<String> typefoodSelectedStringList = new ArrayList<String>();
        /*public static DialogoTypeFoodPreferences newInstance(String[] typefoodList, ArrayList<String> typeFoodSelected) {
            DialogoTypeFoodPreferences dialogo = new DialogoTypeFoodPreferences();
            Bundle args = new Bundle();
            args.putStringArray("typefood", typefoodList);
            args.putStringArrayList("typefoodselected", typeFoodSelected);
            dialogo.setArguments(args);
            return dialogo;
        }*/

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            typefoodStringList = getArguments().getStringArrayList("typefood");
            final CharSequence[] typefoodSequence = new CharSequence[typefoodStringList.size()];
            for (int i = 0; i < typefoodStringList.size(); i++)
                typefoodSequence[i] = typefoodStringList.get(i);

            final ArrayList<String> typeFoodSelectedAux = new ArrayList<String>();
            typefoodSelectedStringList = getArguments().getStringArrayList("typefoodselected");
            final boolean[] optionSelected = new boolean[typefoodSequence.length];

            boolean flag;
            for (int i = 0; i < typefoodSequence.length; i++) {
                flag = false;
                for (int j = 0; j < typefoodSelectedStringList.size(); j++) {
                    if (typefoodSequence[i].equals(typefoodSelectedStringList.get(j))) {
                        optionSelected[i] = true;
                        flag = true;
                    }
                }
                if (flag == false)
                    optionSelected[i] = false;
            }
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle("Tipos de comida preferidos")
                    .setMultiChoiceItems(typefoodSequence, optionSelected,
                            new DialogInterface.OnMultiChoiceClickListener() {
                                public void onClick(DialogInterface dialog, int item, boolean isChecked) {
                                    if (isChecked)
                                        optionSelected[item] = true;
                                    else {
                                        if (optionSelected[item] == true)
                                            optionSelected[item] = false;
                                    }
                                }
                            }).setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    for(int i=0; i<optionSelected.length;i++) {
                        if (optionSelected[i] == true && !typeFoodSelectedAux.contains(typefoodStringList.get(i))) {
                            typeFoodSelectedAux.add(typefoodStringList.get(i));
                        } else {
                            if (optionSelected[i] == false && typeFoodSelectedAux.contains(typefoodStringList.get(i))) {
                                typeFoodSelectedAux.remove(typefoodStringList.get(i));
                            }
                        }
                    }
                    UpdatePreferenciasRestaurantes.setTypeFoodSelected(typeFoodSelectedAux);
                    dialog.dismiss();
                }
            }).setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            return builder.create();
        }
    }

    /*-------------------------------------------------------------------------------------------*/
    /* Clase que construye el cuadro de diálogo que muestra los tipos de restaurantes, que están */
    /* almacenados en la base de datos y que el usuario seleccionará aquellos que quiera que se  */
    /* apliquen en las adaptaciones.                                                      */
    /*-------------------------------------------------------------------------------------------*/
    private static class DialogoTypeRestaurantPreferences extends android.support.v4.app.DialogFragment {
        ArrayList<String> typerestaurantStringList = new ArrayList<String>();
        ArrayList<String> typerestaurantSelectedStringList = new ArrayList<String>();

        /*public static DialogoTypeRestaurantPreferences newInstance(String[] typerestaurantList, ArrayList<String> typeRestaurantSelected) {
            DialogoTypeRestaurantPreferences dialogo = new DialogoTypeRestaurantPreferences();
            Bundle args = new Bundle();
            args.putStringArray("typerestaurant", typerestaurantList);
            args.putStringArrayList("typerestaurantselected", typeRestaurantSelected);
            dialogo.setArguments(args);
            return dialogo;
        }*/

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            typerestaurantStringList = getArguments().getStringArrayList("typerestaurant");
            final CharSequence[] typerestaurantSequence = new CharSequence[typerestaurantStringList.size()];
            for(int i=0; i<typerestaurantStringList.size(); i++)
                typerestaurantSequence[i]= typerestaurantStringList.get(i);
            final ArrayList<String> typeRestaurantSelectedAux = new ArrayList<String>();
            typerestaurantSelectedStringList = getArguments().getStringArrayList("typerestaurantselected");
            final boolean[] optionSelected = new boolean[typerestaurantSequence.length];

            boolean flag;
            for (int i = 0; i < typerestaurantSequence.length; i++) {
                flag = false;
                for (int j = 0; j < typerestaurantSelectedStringList.size(); j++) {
                    if (typerestaurantSequence[i].equals(typerestaurantSelectedStringList.get(j))) {
                        optionSelected[i] = true;
                        flag = true;
                    }
                }
                if (flag == false)
                    optionSelected[i] = false;
            }

            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle("Tipos de restaurantes preferidos")
                    .setMultiChoiceItems(typerestaurantSequence, optionSelected,
                            new DialogInterface.OnMultiChoiceClickListener() {
                                public void onClick(DialogInterface dialog, int item, boolean isChecked) {
                                    if (isChecked)
                                        optionSelected[item] = true;
                                    else {
                                        if (optionSelected[item] == true)
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
                        if (optionSelected[i] == true && !typeRestaurantSelectedAux.contains(typerestaurantStringList.get(i))) {
                            typeRestaurantSelectedAux.add(typerestaurantStringList.get(i));
                        } else {
                            if (optionSelected[i] == false && typeRestaurantSelectedAux.contains(typerestaurantStringList.get(i))) {
                                typeRestaurantSelectedAux.remove(typerestaurantStringList.get(i));
                            }
                        }
                    }
                    UpdatePreferenciasRestaurantes.setTypeRestaurantSelected(typeRestaurantSelectedAux);
                    dialog.dismiss();
                }
            });
            return builder.create();
        }
    }


    /*-------------------------------------------------------------------------------------------*/
    /* Clase AsyncTask que establece la conexión entre la aplicación y el ESB.                   */
    /* Arquitectura REST. Actualiza los valores de las preferencias de los restaurantes, con los */
    /* valores seleccionados por el usuario.                                                     */
    /*-------------------------------------------------------------------------------------------*/
    private class LoadWebServiceUpdatePreferencesRestaurantASYNC extends AsyncTask<GeneralPreference, Integer, Boolean> {
        @Override
        protected Boolean doInBackground(GeneralPreference... params) {
            boolean resul = true;
            HttpClient httpClient = new DefaultHttpClient();
            //String URL = "http://192.168.1.102:8084/userPreferencesServices/updateUserPreferencesServices?username=";
            String URL = "http://"+ MainActivity.getDireccionIP() + ":8084/userPreferencesServices/updateUserPreferencesServices?username=";
            GeneralPreference generalPreferenceNew = new GeneralPreference(params[0]);
            try {
                JSONObject dato = new JSONObject();
                dato.put("username", generalPreferenceNew.getUsername());
                dato.put("weather", 0);
                dato.put("terrace", generalPreferenceNew.getTerrace());
                dato.put("freshFood", generalPreferenceNew.getFreshFood());
                dato.put("cleaning", generalPreferenceNew.getCleaning());

                JSONArray jsonArray = new JSONArray();
                for (int i=0; i<generalPreferenceNew.getTypeFoodPreferenceList().size(); i++) {
                    JSONObject typeFoodPreferenceObj = new JSONObject();
                    typeFoodPreferenceObj.put("username", generalPreferenceNew.getTypeFoodPreferenceList().get(i).getUsername());
                    typeFoodPreferenceObj.put("idtypefood", generalPreferenceNew.getTypeFoodPreferenceList().get(i).getIdtypefood());
                    jsonArray.put(typeFoodPreferenceObj);
                }
                if(flagFood==true)
                    dato.put("typefoodPreferenceList", jsonArray);


                jsonArray = new JSONArray();
                for (int i=0; i<generalPreferenceNew.getTypeRestaurantPreferenceList().size(); i++) {
                    JSONObject typeRestaurantPreferenceObj = new JSONObject();
                    typeRestaurantPreferenceObj.put("username", generalPreferenceNew.getTypeRestaurantPreferenceList().get(i).getUsername());
                    typeRestaurantPreferenceObj.put("idtyperestaurant", generalPreferenceNew.getTypeRestaurantPreferenceList().get(i).getIdtyperestaurant());
                    jsonArray.put(typeRestaurantPreferenceObj);
                }
                if(flagRestaurant==true)
                    dato.put("typerestaurantPreferenceList", jsonArray);

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
            Intent i = new Intent(UpdatePreferenciasRestaurantes.this, MyAccount.class);
            startActivity(i);
        }
    }
}

