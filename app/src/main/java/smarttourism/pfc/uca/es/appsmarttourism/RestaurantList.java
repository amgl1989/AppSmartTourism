package smarttourism.pfc.uca.es.appsmarttourism;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

/**
 * Created by AnaMari on 19/06/2016.
 */

/*-----------------------------------------------------------------------------------------------*/
/* Clase que permite construir una lista de restaurantes.                                        */
/*-----------------------------------------------------------------------------------------------*/
public class RestaurantList extends AppCompatActivity {

    public ListView listViewRestaurant1;
    public String URL = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.restaurantes);

        Bundle extras = getIntent().getExtras();
        URL = extras.getString("url");

        LoadWebServiceRestaurantASYNC loadWebServiceRestaurantASYNC = new LoadWebServiceRestaurantASYNC();
        loadWebServiceRestaurantASYNC.execute(URL);
    }

    /*-------------------------------------------------------------------------------------------*/
    /* Clase AsyncTask que establece la conexión entre la aplicación y el ESB.                   */
    /* Arquitectura SOAP. Obtiene el listado de los restaurantes que están almacenados.          */
    /*-------------------------------------------------------------------------------------------*/
    private class LoadWebServiceRestaurantASYNC extends AsyncTask<String, Void, Boolean> {
        Vector<SoapObject> result = null;
        SoapObject resultObject = null;
        public List<Restaurant> restaurantList = new ArrayList<Restaurant>();
        private Restaurant restaurant;
        private static final String NAMESPACE = "http://services.smartTourism.pfc.uca.es/";
        private static final String SOAP_ACTION = "";
        private static final String METHOD_NAME ="inRestaurant";

        @Override
        protected Boolean doInBackground(String... arg0) {
            String URL = arg0[0];
            boolean resul = true;
            SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.setOutputSoapObject(request);
            HttpTransportSE transporte = new HttpTransportSE(URL);

            try {
                transporte.call(SOAP_ACTION, envelope);
                if (envelope.getResponse() == null) { // Si el ESB no devuelve ningún restaurante.
                    restaurantList = null;
                } else {
                    if (envelope.getResponse().getClass().toString().contains("Vector")) { //Si el ESB devuelve más de un restaurante.
                        result = (Vector<SoapObject>) envelope.getResponse();
                        if (result != null) {
                            for (int i = 0; i < result.size(); i++) {
                                SoapObject so = result.get(i);
                                restaurant = new Restaurant();
                                restaurant.setId(so.getProperty("id").toString());
                                restaurant.setName(so.getProperty("name").toString());
                                restaurant.setLatitude(Double.parseDouble(so.getProperty("latitude").toString()));
                                restaurant.setLongitude(Double.parseDouble(so.getProperty("longitude").toString()));
                                if (so.getProperty("description") != null)
                                    restaurant.setDescription(so.getProperty("description").toString());
                                else
                                    restaurant.setDescription("");
                                if (so.getProperty("cleaning") != null)
                                    restaurant.setCleaning(Integer.parseInt(so.getProperty("cleaning").toString()));
                                else
                                    restaurant.setCleaning(0);
                                if (so.getProperty("numSeating") != null)
                                    restaurant.setNumSeating(Integer.parseInt(so.getProperty("numSeating").toString()));
                                else
                                    restaurant.setNumSeating(1000);
                                if (so.getProperty("terrace") != null)
                                    restaurant.setTerrace(Byte.parseByte(so.getProperty("terrace").toString()));
                                else
                                    restaurant.setTerrace(Byte.parseByte("false"));

                                List<Typerestaurant> typerestaurantList = new ArrayList<Typerestaurant>();
                                List<Typefood> typefoodList = new ArrayList<Typefood>();
                                List<FreshFood> freshFoodList = new ArrayList<FreshFood>();
                                List<SeatingEmpty> seatingEmptyList = new ArrayList<SeatingEmpty>();

                                for (int j = 0; j < so.getPropertyCount(); j++) {
                                    if (so.getPropertyAsString(j).contains("typeRestaurant") == true) {
                                        String property = so.getPropertyAsString(j);
                                        String id = property.substring(property.indexOf("id=") + "id=".length(), property.indexOf("; "));
                                        String typeRestaurantString = property.substring(property.indexOf("typeRestaurant=") +
                                                "typeRestaurant=".length(), property.indexOf("; }"));
                                        Typerestaurant typerestaurant = new Typerestaurant(id, typeRestaurantString);
                                        typerestaurantList.add(typerestaurant);
                                    }
                                    if (so.getPropertyAsString(j).contains("typeFood") == true) {
                                        String property = so.getPropertyAsString(j);
                                        String id = property.substring(property.indexOf("id=") + "id=".length(), property.indexOf("; "));
                                        String typefoodString = property.substring(property.indexOf("typeFood=") +
                                                "typeFood=".length(), property.indexOf("; }"));
                                        Typefood typefood = new Typefood(id, typefoodString);
                                        typefoodList.add(typefood);
                                    }
                                    if (so.getPropertyAsString(j).contains("freshfood") == true) {
                                        String property = so.getPropertyAsString(j);
                                        String id = property.substring(property.indexOf("restaurantId=") +
                                                "restaurantId=".length(), property.indexOf("; }"));
                                        String freshfoodString = property.substring(property.indexOf("freshfood=") +
                                                "freshfood=".length(), property.indexOf("; id="));
                                        String dateString = property.substring(property.indexOf("{date=") +
                                                "{date=".length(), property.indexOf("+0"));
                                        FreshFood freshfood = new FreshFood(id, dateString, freshfoodString);
                                        freshFoodList.add(freshfood);
                                    }
                                    if (so.getPropertyAsString(j).contains("seatingEmpty") == true) {
                                        String property = so.getPropertyAsString(j);
                                        String id = property.substring(property.indexOf("restaurantId=") +
                                                "restaurantId=".length(), property.indexOf("; }; seatingEmpty"));
                                        String seatingEmptyString = property.substring(property.indexOf("seatingEmpty=") +
                                                "seatingEmpty=".length(), property.length() - 3);
                                        String dateString = property.substring(property.indexOf("{date=") +
                                                "{date=".length(), property.indexOf("+0"));
                                        SeatingEmpty seatingEmpty = new SeatingEmpty(id, dateString, seatingEmptyString);
                                        seatingEmptyList.add(seatingEmpty);
                                    }
                                }
                                restaurant.setTypeRestList(typerestaurantList);
                                restaurant.setTypefoodList(typefoodList);
                                restaurant.setSeatingEmptyList(seatingEmptyList);
                                restaurant.setFreshfoodList(freshFoodList);
                                restaurantList.add(restaurant);
                            }
                        }
                    } else {
                        if (envelope.getResponse().getClass().toString().contains("SoapObject")) { //Para el caso en que el ESB devuelva solo un restaurante.
                            resultObject = (SoapObject) envelope.getResponse();
                            if (resultObject != null) {
                                restaurant = new Restaurant();
                                restaurant.setId(resultObject.getProperty("id").toString());
                                restaurant.setName(resultObject.getProperty("name").toString());
                                restaurant.setLatitude(Double.parseDouble(resultObject.getProperty("latitude").toString()));
                                restaurant.setLongitude(Double.parseDouble(resultObject.getProperty("longitude").toString()));
                                if (resultObject.getProperty("description") != null)
                                    restaurant.setDescription(resultObject.getProperty("description").toString());
                                else
                                    restaurant.setDescription("");
                                if (resultObject.getProperty("cleaning") != null)
                                    restaurant.setCleaning(Integer.parseInt(resultObject.getProperty("cleaning").toString()));
                                else
                                    restaurant.setCleaning(0);
                                if (resultObject.getProperty("numSeating") != null)
                                    restaurant.setNumSeating(Integer.parseInt(resultObject.getProperty("numSeating").toString()));
                                else
                                    restaurant.setNumSeating(1000);
                                if (resultObject.getProperty("terrace") != null)
                                    restaurant.setTerrace(Byte.parseByte(resultObject.getProperty("terrace").toString()));
                                else
                                    restaurant.setTerrace(Byte.parseByte("false"));

                                List<Typerestaurant> typerestaurantList = new ArrayList<Typerestaurant>();
                                List<Typefood> typefoodList = new ArrayList<Typefood>();
                                List<FreshFood> freshFoodList = new ArrayList<FreshFood>();
                                List<SeatingEmpty> seatingEmptyList = new ArrayList<SeatingEmpty>();

                                for (int j = 0; j < resultObject.getPropertyCount(); j++) {
                                    if (resultObject.getPropertyAsString(j).contains("typeRestaurant") == true) {
                                        String property = resultObject.getPropertyAsString(j);
                                        String id = property.substring(property.indexOf("id=") + "id=".length(), property.indexOf("; "));
                                        String typeRestaurantString = property.substring(property.indexOf("typeRestaurant=") +
                                                "typeRestaurant=".length(), property.indexOf("; }"));
                                        Typerestaurant typerestaurant = new Typerestaurant(id, typeRestaurantString);
                                        typerestaurantList.add(typerestaurant);
                                    }
                                    if (resultObject.getPropertyAsString(j).contains("typeFood") == true) {
                                        String property = resultObject.getPropertyAsString(j);
                                        String id = property.substring(property.indexOf("id=") + "id=".length(), property.indexOf("; "));
                                        String typefoodString = property.substring(property.indexOf("typeFood=") +
                                                "typeFood=".length(), property.indexOf("; }"));
                                        Typefood typefood = new Typefood(id, typefoodString);
                                        typefoodList.add(typefood);
                                    }
                                    if (resultObject.getPropertyAsString(j).contains("freshfood") == true) {
                                        String property = resultObject.getPropertyAsString(j);
                                        String id = property.substring(property.indexOf("restaurantId=") +
                                                "restaurantId=".length(), property.indexOf("; }"));
                                        String freshfoodString = property.substring(property.indexOf("freshfood=") +
                                                "freshfood=".length(), property.indexOf("; id="));
                                        String dateString = property.substring(property.indexOf("{date=") +
                                                "{date=".length(), property.indexOf("+0"));
                                        FreshFood freshfood = new FreshFood(id, dateString, freshfoodString);
                                        freshFoodList.add(freshfood);
                                    }
                                    if (resultObject.getPropertyAsString(j).contains("seatingEmpty") == true) {
                                        String property = resultObject.getPropertyAsString(j);
                                        String id = property.substring(property.indexOf("restaurantId=") +
                                                "restaurantId=".length(), property.indexOf("; }; seatingEmpty"));
                                        String seatingEmptyString = property.substring(property.indexOf("seatingEmpty=") +
                                                "seatingEmpty=".length(), property.length() - 3);
                                        String dateString = property.substring(property.indexOf("{date=") +
                                                "{date=".length(), property.indexOf("+0"));
                                        SeatingEmpty seatingEmpty = new SeatingEmpty(id, dateString, seatingEmptyString);
                                        seatingEmptyList.add(seatingEmpty);
                                    }
                                }
                                restaurant.setTypeRestList(typerestaurantList);
                                restaurant.setTypefoodList(typefoodList);
                                restaurant.setSeatingEmptyList(seatingEmptyList);
                                restaurant.setFreshfoodList(freshFoodList);
                            }
                        }
                    }
                }
            }catch (Exception e) {
                restaurantList = null;
                System.out.println("Invocacion error ");
                resul = false;
            }
            return resul;
        }

        @Override
        protected void onPostExecute(Boolean resul) {
            if (resul) {
                if (restaurantList != null) {
                    AdaptadorRestaurantes adaptador = new AdaptadorRestaurantes(RestaurantList.this, restaurantList);
                    listViewRestaurant1 = (ListView) findViewById(R.id.listViewRestaurante);
                    listViewRestaurant1.setAdapter(adaptador);
                    listViewRestaurant1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView a, View v, int position, long id) {
                            Restaurant restaurant = ((Restaurant) a.getItemAtPosition(position));
                            Intent i = new Intent(RestaurantList.this, VistaRestaurante.class);
                            i.putExtra("id", restaurant.getId());
                            i.putExtra("name", restaurant.getName());
                            i.putExtra("latitude", restaurant.getLatitude());
                            i.putExtra("longitude", restaurant.getLongitude());
                            i.putExtra("description", restaurant.getDescription());
                            i.putExtra("cleaning", restaurant.getCleaning());
                            i.putExtra("numSeating", restaurant.getNumSeating());
                            i.putExtra("terrace", restaurant.getTerrace());
                            i.putExtra("typeRestList", new TyperestaurantList(restaurant.getTypeRestList()));
                            i.putExtra("typeFoodList", new TypefoodList(restaurant.getTypefoodList()));
                            i.putExtra("freshFoodList", new FreshFoodList(restaurant.getFreshfoodList()));
                            i.putExtra("seatingEmptyList", new SeatingEmptyList(restaurant.getSeatingEmptyList()));
                            startActivity(i);
                        }
                    });
                }else{
                    Intent i = new Intent(RestaurantList.this, MessageEmptyResultsRestaurants.class);
                    startActivity(i);
                }
            }
        }
    }
}
