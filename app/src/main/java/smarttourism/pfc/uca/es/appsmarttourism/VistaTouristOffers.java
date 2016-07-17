package smarttourism.pfc.uca.es.appsmarttourism;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by AnaMari on 08/02/2016.
 */

/*-----------------------------------------------------------------------------------------------*/
/* Clase que muestra por pantalla toda la información de la oferta turística seleccionada.       */
/*-----------------------------------------------------------------------------------------------*/
public class VistaTouristOffers  extends AppCompatActivity {
    TouristOffers touristOffers = new TouristOffers();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vista_ofertaturistica);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Bundle extras = getIntent().getExtras();
        touristOffers.setId(extras.getString("id"));
        touristOffers.setName(extras.getString("name"));
        touristOffers.setLatitude(extras.getDouble("latitude"));
        touristOffers.setLongitude(extras.getDouble("longitude"));
        touristOffers.setDescription(extras.getString("description"));
        touristOffers.setGuided(extras.getByte("guided"));
        touristOffers.setBooking(extras.getByte("booking"));
        touristOffers.setOpenAir(extras.getByte("openAir"));
        TypeTourismList typeTourismList = (TypeTourismList) getIntent().getSerializableExtra("typeTourismList");
        touristOffers.setTypeTourismList(typeTourismList.getTypeTourismArrayList());
        ScheduleList scheduleList = (ScheduleList) getIntent().getSerializableExtra("scheduleList");
        touristOffers.setScheduleList(scheduleList.getScheduleArrayList());
        actualizarVista();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_vista, menu);
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1234) {
            actualizarVista();
            findViewById(R.id.scrollView1).invalidate();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.accion_inicio:
                Intent i = new Intent(this,MainActivity.class);
                startActivityForResult(i,0);
                return true;
            case R.id.accion_compartir:
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_TEXT, touristOffers.getName() + " - "+ touristOffers.getDescription());
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    //Precondiciones: Ninguno.
    //Postcondiciones:  Actualizamos los valores en la vista.
    public void actualizarVista(){
        TextView nombre = (TextView) findViewById(R.id.nombre);
        nombre.setText(touristOffers.getName());

        if (touristOffers.getLatitude() == 0.0) {
            findViewById(R.id.etxLatitud).setVisibility(View.GONE);
        } else {
            TextView latitud = (TextView) findViewById(R.id.etxLatitud);
            latitud.setText(String.valueOf(touristOffers.getLatitude()));
        }

        if (touristOffers.getLongitude() == 0.0) {
            findViewById(R.id.etxLongitud).setVisibility(View.GONE);
        } else {
            TextView longitud = (TextView) findViewById(R.id.etxLongitud);
            longitud.setText(String.valueOf(touristOffers.getLongitude()));
        }

        if (touristOffers.getDescription().isEmpty()) {
            findViewById(R.id.descripcion).setVisibility(View.GONE);
        } else {
            TextView descripcion = (TextView) findViewById(R.id.descripcion);
            descripcion.setText(touristOffers.getDescription());
        }
        if (touristOffers.getGuided()==1) {
            CheckBox guided = (CheckBox)findViewById(R.id.cbxGuiada);
            guided.setChecked(true);

        }
        if (touristOffers.getBooking()==1) {
            CheckBox booking = (CheckBox)findViewById(R.id.cbxReserva);
            booking.setChecked(true);
        }
        if (touristOffers.getOpenAir()==1) {
            CheckBox openAir = (CheckBox)findViewById(R.id.cbxAireLibre);
            openAir.setChecked(true);
        }

        Button btnTipoTurismo = (Button) findViewById(R.id.btnTypeTourism);
        btnTipoTurismo.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                FragmentManager fragmentManager = getSupportFragmentManager();
                DialogoTiposTurismo dialogo = new DialogoTiposTurismo();
                dialogo.show(fragmentManager, "InformacionTiposTurismo");
            }
        });

        Button btnSchedule = (Button) findViewById(R.id.btnFechasHorarios);
        btnSchedule.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                FragmentManager fragmentManager = getSupportFragmentManager();
                DialogoFechasHorarios dialogo = new DialogoFechasHorarios();
                dialogo.show(fragmentManager, "InformacionFechasHorarios");
            }
        });

        FloatingActionButton btnMapaOfertaTuristica = (FloatingActionButton) findViewById(R.id.btnMapaOfertasTuristica);
        btnMapaOfertaTuristica.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(VistaTouristOffers.this, MapTouristOffers.class);
                i.putExtra("id", touristOffers.getId());
                i.putExtra("name", touristOffers.getName());
                i.putExtra("latitude", touristOffers.getLatitude());
                i.putExtra("longitude", touristOffers.getLongitude());
                startActivity(i);
            }
        });
    }

    /*-------------------------------------------------------------------------------------------*/
    /* Clase que construye el cuadro de diálogo que muestra los tipos de turimos en los que está */
    /* clasificado la oferta turística actual.                                                   */
    /*-------------------------------------------------------------------------------------------*/
    public class DialogoTiposTurismo extends DialogFragment {
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            String texto = "";
            for (int i = 0; i < touristOffers.getTypeTourismList().size(); i++)
                texto = texto + "- " + touristOffers.getTypeTourismList().get(i).getTypetourism() + '\n';

            if(texto.trim().equals(""))
                texto = "No aplica.";

            Resources res = getResources();
            builder.setMessage(texto)
                    .setTitle(res.getString(R.string.tipoturismo))
                    .setPositiveButton(res.getString(R.string.aceptar), new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });
            return builder.create();
        }
    }

    //Precondiciones: Recibe la fecha en String.
    //Postcondiciones: Devuelve la fecha en formate Date yyyy-MM-dd.
    private Date convertStringToDate(String dateString){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = format.parse(dateString);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return date;
    }

    /*-------------------------------------------------------------------------------------------*/
    /* Clase que construye el cuadro de diálogo que muestra las fechas y horarios en las que se  */
    /* puede visitar o realizar la oferta turística actual.                                      */
    /*-------------------------------------------------------------------------------------------*/
    public class DialogoFechasHorarios extends DialogFragment {
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            String texto = "";
            int j=0;
            for (int i=touristOffers.getScheduleList().size()-1; i>=0 && j<5; i--,j++) {
                String fechaInicial = touristOffers.getScheduleList().get(i).getDate();
                Date today = new Date();
                String[] fechaHora = fechaInicial.split("T");
                if(convertStringToDate(fechaHora[0]).compareTo(today) >= 0)
                    texto = texto + "- " + touristOffers.getScheduleList().get(i).getSchedule() + '\n';
            }

            if(texto.trim().equals(""))
                texto = "No aplica.";

            Resources res = getResources();
            builder.setMessage(texto)
                    .setTitle(res.getString(R.string.horarios))
                    .setPositiveButton(res.getString(R.string.aceptar), new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });
            return builder.create();
        }
    }
}