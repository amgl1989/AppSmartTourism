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
/* Clase que permite construir una lista de ofertas turísticas.                                  */
/*-----------------------------------------------------------------------------------------------*/
public class TouristOfferList extends AppCompatActivity{
    public ListView listViewRestaurant1;
    public String URL = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.restaurantes);

        Bundle extras = getIntent().getExtras();
        URL = extras.getString("url");

        LoadWebServiceTouristOffersASYNC loadWebServiceTouristOffersASYNC = new LoadWebServiceTouristOffersASYNC();
        loadWebServiceTouristOffersASYNC.execute(URL);
    }

    /*-------------------------------------------------------------------------------------------*/
    /* Clase AsyncTask que establece la conexión entre la aplicación y el ESB.                   */
    /* Arquitectura SOAP. Obtiene el listado de las ofertas turísticas que están almacenados.    */
    /*-------------------------------------------------------------------------------------------*/
    private class LoadWebServiceTouristOffersASYNC extends AsyncTask<String, Void, Boolean> {
        Vector<SoapObject> result = null;
        SoapObject resultObject = null;
        public List<TouristOffers> touristOffersList = new ArrayList<TouristOffers>();
        private TouristOffers touristOffers;
        private static final String NAMESPACE = "http://services.smartTourism.pfc.uca.es/";
        private static final String SOAP_ACTION = "";
        private static final String METHOD_NAME ="inTouristoffers";
        @Override
        protected Boolean doInBackground(String... arg0) {
            boolean resul = true;
            String URL = arg0[0];
            SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.setOutputSoapObject(request);
            HttpTransportSE transporte = new HttpTransportSE(URL);

            try {
                transporte.call(SOAP_ACTION, envelope);
                if (envelope.getResponse()==null){ // Si el ESB no devuelve ningún restaurante.
                    touristOffersList = null;
                }else {
                    if (envelope.getResponse().getClass().toString().contains("Vector")) {  //Si el ESB devuelve más de un restaurante.
                        result = (Vector<SoapObject>) envelope.getResponse();
                        if (result != null) {
                            for (int i = 0; i < result.size(); ++i) {
                                SoapObject so = result.get(i);
                                touristOffers = new TouristOffers();
                                touristOffers.setId(so.getProperty("id").toString());
                                touristOffers.setName(so.getProperty("name").toString());
                                touristOffers.setLatitude(Double.parseDouble(so.getProperty("latitude").toString()));
                                touristOffers.setLongitude(Double.parseDouble(so.getProperty("longitude").toString()));
                                if (so.getProperty("description") != null)
                                    touristOffers.setDescription(so.getProperty("description").toString());
                                else
                                    touristOffers.setDescription("");
                                if (so.getProperty("guided") != null)
                                    touristOffers.setGuided(Byte.parseByte(so.getProperty("guided").toString()));
                                else
                                    touristOffers.setGuided(Byte.parseByte("false"));
                                if (so.getProperty("booking") != null)
                                    touristOffers.setBooking(Byte.parseByte(so.getProperty("booking").toString()));
                                else
                                    touristOffers.setBooking(Byte.parseByte("false"));
                                if (so.getProperty("openAir") != null)
                                    touristOffers.setOpenAir(Byte.parseByte(so.getProperty("openAir").toString()));
                                else
                                    touristOffers.setOpenAir(Byte.parseByte("false"));

                                List<TypeTourism> typeTourismList = new ArrayList<TypeTourism>();
                                List<Schedule> scheduleList = new ArrayList<Schedule>();

                                for (int j = 0; j < so.getPropertyCount(); j++) {
                                    if (so.getPropertyAsString(j).contains("typetourism") == true) {
                                        String property = so.getPropertyAsString(j);
                                        String id = property.substring(property.indexOf("id=") + "id=".length(), property.indexOf("; "));
                                        String typeTourismString = property.substring(property.indexOf("typetourism=") +
                                                "typetourism=".length(), property.indexOf("; }"));
                                        TypeTourism typeTourism = new TypeTourism(id, typeTourismString);
                                        typeTourismList.add(typeTourism);
                                    }
                                    if (so.getPropertyAsString(j).contains("schedule") == true) {
                                        String property = so.getPropertyAsString(j);
                                        String id = property.substring(property.indexOf("idTouristoffers=") +
                                                "idTouristoffers=".length(), property.indexOf("; }; schedule="));
                                        String scheduleString = property.substring(property.indexOf("schedule=") +
                                                "schedule=".length(), property.length() - 3);

                                        String dateStringAux = property.substring(property.indexOf("{date=") +
                                                "{date=".length(), property.indexOf(":00;"));
                                        String[] dateStringArray = dateStringAux.split("T");
                                        String dateStringArray1 = dateStringArray[1].substring(0,8);
                                        String dateString = dateStringArray[0] + " " + dateStringArray1;
                                        Schedule schedule = new Schedule(id, dateString, scheduleString);
                                        scheduleList.add(schedule);
                                    }
                                }
                                touristOffers.setScheduleList(scheduleList);
                                touristOffers.setTypeTourismList(typeTourismList);
                                touristOffersList.add(touristOffers);
                            }
                        }
                    } else {
                        if (envelope.getResponse().getClass().toString().contains("SoapObject")) { //Para el caso en que el ESB devuelva solo un restaurante.
                            resultObject = (SoapObject) envelope.getResponse();
                            if (resultObject != null) {
                                touristOffers = new TouristOffers();
                                touristOffers.setId(resultObject.getProperty("id").toString());
                                touristOffers.setName(resultObject.getProperty("name").toString());
                                touristOffers.setLatitude(Double.parseDouble(resultObject.getProperty("latitude").toString()));
                                touristOffers.setLongitude(Double.parseDouble(resultObject.getProperty("longitude").toString()));
                                if (resultObject.getProperty("description") != null)
                                    touristOffers.setDescription(resultObject.getProperty("description").toString());
                                else
                                    touristOffers.setDescription("");
                                if (resultObject.getProperty("guided") != null)
                                    touristOffers.setGuided(Byte.parseByte(resultObject.getProperty("guided").toString()));
                                else
                                    touristOffers.setGuided(Byte.parseByte("false"));
                                if (resultObject.getProperty("booking") != null)
                                    touristOffers.setBooking(Byte.parseByte(resultObject.getProperty("booking").toString()));
                                else
                                    touristOffers.setBooking(Byte.parseByte("false"));
                                if (resultObject.getProperty("openAir") != null)
                                    touristOffers.setOpenAir(Byte.parseByte(resultObject.getProperty("openAir").toString()));
                                else
                                    touristOffers.setOpenAir(Byte.parseByte("false"));

                                List<TypeTourism> typeTourismList = new ArrayList<TypeTourism>();
                                List<Schedule> scheduleList = new ArrayList<Schedule>();

                                for (int j = 0; j < resultObject.getPropertyCount(); j++) {
                                    if (resultObject.getPropertyAsString(j).contains("typetourism") == true) {
                                        String property = resultObject.getPropertyAsString(j);
                                        String id = property.substring(property.indexOf("id=") + "id=".length(), property.indexOf("; "));
                                        String typeTourismString = property.substring(property.indexOf("typetourism=") +
                                                "typetourism=".length(), property.indexOf("; }"));
                                        TypeTourism typeTourism = new TypeTourism(id, typeTourismString);
                                        typeTourismList.add(typeTourism);
                                    }
                                    if (resultObject.getPropertyAsString(j).contains("schedule") == true) {
                                        String property = resultObject.getPropertyAsString(j);
                                        String id = property.substring(property.indexOf("idTouristoffers=") +
                                                "idTouristoffers=".length(), property.indexOf("; }; schedule="));
                                        String scheduleString = property.substring(property.indexOf("schedule=") +
                                                "schedule=".length(), property.length() - 3);
                                        String dateStringAux = property.substring(property.indexOf("{date=") +
                                                "{date=".length(), property.indexOf(":00;"));
                                        String[] dateStringArray = dateStringAux.split("T");
                                        String dateStringArray1 = dateStringArray[1].substring(0,8);
                                        String dateString = dateStringArray[0] + " " + dateStringArray1;
                                        Schedule schedule = new Schedule(id, dateString, scheduleString);
                                        scheduleList.add(schedule);
                                    }
                                }
                                touristOffers.setScheduleList(scheduleList);
                                touristOffers.setTypeTourismList(typeTourismList);
                                touristOffersList.add(touristOffers);
                            }
                        }
                    }
                }
            } catch (Exception e) {
                touristOffersList = null;
                System.out.println("Invocacion error");
                resul = false;
            }
            return resul;
        }

        @Override
        protected void onPostExecute(Boolean resul) {
            if (resul) {
                if (touristOffersList != null) {
                    AdaptadorOfertasTuristicas adaptador = new AdaptadorOfertasTuristicas(TouristOfferList.this, touristOffersList);
                    listViewRestaurant1 = (ListView) findViewById(R.id.listViewRestaurante);
                    listViewRestaurant1.setAdapter(adaptador);
                    listViewRestaurant1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView a, View v, int position, long id) {
                            TouristOffers touristOffers = ((TouristOffers) a.getItemAtPosition(position));
                            Intent i = new Intent(TouristOfferList.this, VistaTouristOffers.class);
                            i.putExtra("id", touristOffers.getId());
                            i.putExtra("name", touristOffers.getName());
                            i.putExtra("latitude", touristOffers.getLatitude());
                            i.putExtra("longitude", touristOffers.getLongitude());
                            i.putExtra("description", touristOffers.getDescription());
                            i.putExtra("guided", touristOffers.getGuided());
                            i.putExtra("booking", touristOffers.getBooking());
                            i.putExtra("openAir", touristOffers.getOpenAir());
                            i.putExtra("typeTourismList", new TypeTourismList(touristOffers.getTypeTourismList()));
                            i.putExtra("scheduleList", new ScheduleList(touristOffers.getScheduleList()));
                            i.putExtra("url",URL);
                            startActivity(i);
                        }
                    });
                }else{
                    Intent i = new Intent(TouristOfferList.this, MessageEmptyResultsTouristOffers.class);
                    startActivity(i);
                }
            }
        }
    }
}
