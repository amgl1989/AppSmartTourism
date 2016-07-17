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
import android.widget.RatingBar;
import android.widget.TextView;

/**
 * Created by AnaMari on 07/08/2015.
 */

/*-----------------------------------------------------------------------------------------------*/
/* Clase que muestra por pantalla toda la información del restaurante seleccionado.              */
/*-----------------------------------------------------------------------------------------------*/
public class VistaRestaurante extends AppCompatActivity {
    Restaurant restaurant = new Restaurant();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vista_restaurante);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Bundle extras = getIntent().getExtras();
        restaurant.setId(extras.getString("id"));
        restaurant.setName(extras.getString("name"));
        restaurant.setLatitude(extras.getDouble("latitude"));
        restaurant.setLongitude(extras.getDouble("longitude"));
        restaurant.setDescription(extras.getString("description"));
        restaurant.setCleaning(extras.getInt("cleaning"));
        restaurant.setNumSeating(extras.getInt("numSeating"));
        restaurant.setTerrace(extras.getByte("terrace"));
        TyperestaurantList typerestaurantList = (TyperestaurantList) getIntent().getSerializableExtra("typeRestList");
        restaurant.setTypeRestList(typerestaurantList.getTyperestaurantArrayList());
        TypefoodList typefoodList = (TypefoodList) getIntent().getSerializableExtra("typeFoodList");
        restaurant.setTypefoodList(typefoodList.getTypefoodArrayList());
        FreshFoodList freshFoodList = (FreshFoodList) getIntent().getSerializableExtra("freshFoodList");
        restaurant.setFreshfoodList(freshFoodList.getFreshFoodArrayList());
        SeatingEmptyList seatingEmptyList = (SeatingEmptyList) getIntent().getSerializableExtra("seatingEmptyList");
        restaurant.setSeatingEmptyList(seatingEmptyList.getSeatingEmptyArrayList());
        actualizarVista();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_vista, menu);
        return true;
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
                intent.putExtra(Intent.EXTRA_TEXT, restaurant.getName() + " - "+ restaurant.getDescription());
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1234) {
            actualizarVista();
            findViewById(R.id.scrollView1).invalidate();
        }
    }

    //Precondiciones: Ninguno.
    //Postcondiciones:  Actualizamos los valores en la vista.
    public void actualizarVista(){
        TextView nombre = (TextView) findViewById(R.id.nombre);
        nombre.setText(restaurant.getName());

        if (restaurant.getLatitude() == 0.0) {
            findViewById(R.id.etxLatitud).setVisibility(View.GONE);
        } else {
            TextView latitud = (TextView) findViewById(R.id.etxLatitud);
            latitud.setText(String.valueOf(restaurant.getLatitude()));
        }

        if (restaurant.getLongitude() == 0.0) {
            findViewById(R.id.etxLongitud).setVisibility(View.GONE);
        } else {
            TextView longitud = (TextView) findViewById(R.id.etxLongitud);
            longitud.setText(String.valueOf(restaurant.getLongitude()));
        }

        if (restaurant.getDescription().isEmpty()) {
            findViewById(R.id.descripcion).setVisibility(View.GONE);
        } else {
            TextView descripcion = (TextView) findViewById(R.id.descripcion);
            descripcion.setText(restaurant.getDescription());
        }

        if (restaurant.getNumSeating()==0) {
            findViewById(R.id.numeroAsientos).setVisibility(View.GONE);
        } else {
            TextView numeroAsientos = (TextView) findViewById(R.id.numeroAsientos);
            numeroAsientos.setText(Integer.toString(restaurant.getNumSeating()));
        }

        if (restaurant.getTerrace()== 1) {
            CheckBox terraza = (CheckBox)findViewById(R.id.cbxTerraza);
            terraza.setChecked(true);
        }

        RatingBar rbLimpieza = (RatingBar) findViewById(R.id.ratingBarLimpieza);
        rbLimpieza.setRating(restaurant.getCleaning());
        rbLimpieza.setOnRatingBarChangeListener(
                new RatingBar.OnRatingBarChangeListener() {
                    @Override
                    public void onRatingChanged(RatingBar ratingBar, float valor, boolean fromUser) {
                        restaurant.setCleaning((int) valor);
                    }
                });

        Button btnTipoRestaurante = (Button) findViewById(R.id.btnTypesRestaurant);
        btnTipoRestaurante.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                FragmentManager fragmentManager = getSupportFragmentManager();
                DialogoTiposRestaurantes dialogo = new DialogoTiposRestaurantes();
                dialogo.show(fragmentManager, "InformacionTiposRestaurantes");
            }
        });
        Button btnTipoComida = (Button) findViewById(R.id.btnTypesFood);
        btnTipoComida.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                FragmentManager fragmentManager = getSupportFragmentManager();
                DialogoTiposComida dialogo = new DialogoTiposComida();
                dialogo.show(fragmentManager, "InformacionTiposComida");
            }
        });

        Button btnFreshFood = (Button) findViewById(R.id.btnFreshFood);
        btnFreshFood.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                FragmentManager fragmentManager = getSupportFragmentManager();
                DialogoFreshFood dialogo = new DialogoFreshFood();
                dialogo.show(fragmentManager, "InformacionFreshFood");
            }
        });

        Button btnSeatingEmpty = (Button) findViewById(R.id.btnSeatingEmpty);
        btnSeatingEmpty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getSupportFragmentManager();
                DialogoSeatingEmpty dialogo = new DialogoSeatingEmpty();
                dialogo.show(fragmentManager, "InformacionSeatingEmpty");
            }
        });

        FloatingActionButton btnMapaRestaurante = (FloatingActionButton) findViewById(R.id.btnMapaRestaurante);
        btnMapaRestaurante.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(VistaRestaurante.this, MapRestaurant.class);
                i.putExtra("id", restaurant.getId());
                i.putExtra("name", restaurant.getName());
                i.putExtra("latitude", restaurant.getLatitude());
                i.putExtra("longitude", restaurant.getLongitude());
                startActivity(i);
            }
        });
    }

    /*-------------------------------------------------------------------------------------------*/
    /* Clase que construye el cuadro de diálogo que muestra los tipos que son el restaurante     */
    /* actualmente seleccionado.                                                                 */
    /*-------------------------------------------------------------------------------------------*/
    public class DialogoTiposRestaurantes extends DialogFragment {
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            AlertDialog.Builder builder =new AlertDialog.Builder(getActivity());
            String texto= "";
            for(int i=0; i<restaurant.getTypeRestList().size(); i++){
                texto = texto + "- " + restaurant.getTypeRestList().get(i).getTypeRestaurant() + '\n';
            }
            if(texto.trim().equals("")){
                texto = "No aplica.";
            }

            Resources res = getResources();
            builder.setMessage(texto)
                    .setTitle(res.getString(R.string.tiposrestaurantes))
                    .setPositiveButton(res.getString(R.string.aceptar), new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });
            return builder.create();
        }
    }

    /*-------------------------------------------------------------------------------------------*/
    /* Clase que construye el cuadro de diálogo que muestra los tipos de comida que sirve el     */
    /* restaurante actualmente seleccionado.                                                     */
    /*-------------------------------------------------------------------------------------------*/
    public class DialogoTiposComida extends DialogFragment {
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            String texto = "";
            for (int i = 0; i < restaurant.getTypefoodList().size(); i++) {
                texto = texto +  "- " + restaurant.getTypefoodList().get(i).getTypefood() + '\n';
            }
            if(texto.trim().equals("")){
                texto = "No aplica.";
            }

            Resources res = getResources();
            builder.setMessage(texto)
                    .setTitle(res.getString(R.string.tiposcomidas))
                    .setPositiveButton(res.getString(R.string.aceptar), new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });
            return builder.create();
        }
    }

    /*-------------------------------------------------------------------------------------------*/
    /* Clase que construye el cuadro de diálogo que muestra las últimas 5 adquisiciones de estado*/
    /* de los productos que sirve el restaurante.                                                */
    /*-------------------------------------------------------------------------------------------*/
    public class DialogoFreshFood extends DialogFragment {
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            AlertDialog.Builder builder =new AlertDialog.Builder(getActivity());
            String texto= "";
            int j=0;
            for(int i=restaurant.getFreshfoodList().size()-1; i>=0 && j<5; i--, j++) {
                String fechaInicial = restaurant.getFreshfoodList().get(i).getDate();
                String[] fechaHora = fechaInicial.split("T");
                String[] fechaAux = fechaHora[0].split("-");
                //String[] horaAux = fechaHora[1].split(":");
                String fecha = fechaAux[2]+"/"+fechaAux[1]+"/"+fechaAux[0];//+ " a las " +
                        //horaAux[0]+":"+horaAux[1]+":"+horaAux[2].subSequence(0,2);
                texto = texto + "- Alimentos " + restaurant.getFreshfoodList().get(i).getFreshfood() + " el " + fecha  + '\n';
            }

            if(texto.trim().equals(""))
                texto = "No aplica.";

            Resources res = getResources();
            builder.setMessage(texto)
                    .setTitle(res.getString(R.string.fechacomidafresca))
                    .setPositiveButton(res.getString(R.string.aceptar), new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });
            return builder.create();
        }
    }

    /*-------------------------------------------------------------------------------------------*/
    /* Clase que construye el cuadro de diálogo que muestra el número de asientos vacíos, junto  */
    /* con la fecha y hora del restaurante seleccionado.                                         */
    /*-------------------------------------------------------------------------------------------*/
    public class DialogoSeatingEmpty extends DialogFragment {
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            AlertDialog.Builder builder =new AlertDialog.Builder(getActivity());
            String texto= "";
            int j=0;
            for(int i=restaurant.getSeatingEmptyList().size()-1; i>=0 && j<5; i--,j++) {
                String fechaInicial = restaurant.getSeatingEmptyList().get(i).getDate();
                String[] fechaHora = fechaInicial.split("T");
                String[] fechaAux = fechaHora[0].split("-");
                String[] horaAux = fechaHora[1].split(":");

                String fecha = fechaAux[2]+"/"+fechaAux[1]+"/"+fechaAux[0]+ " a las " +
                        horaAux[0]+":"+horaAux[1]+":"+horaAux[2].subSequence(0,2);
                texto = texto + "- " + restaurant.getSeatingEmptyList().get(i).getSeatingEmpty() +
                        " asientos libres el " + fecha+ '\n';
            }
            if(texto.trim().equals(""))
                texto = "No aplica.";
            Resources res = getResources();
            builder.setMessage(texto)
                    .setTitle(res.getString(R.string.fechaasientoslibres))
                    .setPositiveButton(res.getString(R.string.aceptar), new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });
            return builder.create();
        }
    }
}

