package smarttourism.pfc.uca.es.appsmarttourism;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

/**
 * Created by AnaMari on 15/10/2015.
 */

/*-----------------------------------------------------------------------------------------------*/
/* Clase que da funcionalidad a las vistas de preferencias de usuario                */
/*-----------------------------------------------------------------------------------------------*/
public class PreferenciasUsuario extends ListActivity {

    static final String[] opciones = new String[] {"Tiempo meteorológico",
            "Restaurantes", "Ofertas turísticas"};

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setListAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, opciones));
        getListView().setTextFilterEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_lista, menu);
        return true;  /** true -> el menú ya está visible */
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.accion_lista) {
            Intent i = new Intent(this,MainActivity.class);
            startActivity(i);
            return true;
        }
        return true;
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        switch (position){
            case 0:
                final String[] items = {"Sol", "Lluvia"};
                super.onListItemClick(l, v, position, id);
                new AlertDialog.Builder(this)
                        .setTitle("Tiempo meteorológico")
                        .setSingleChoiceItems(items, -1,
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int item) {
                                        Log.i("Dialogos", "Opción elegida: " + items[item]);
                                    }
                                })
                        .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Log.i("Dialogos", "Confirmacion Aceptada.");
                                dialog.cancel();
                            }
                        })
                        .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Log.i("Dialogos", "Confirmacion Cancelada.");
                                dialog.cancel();
                            }
                        }).show();
                break;
            case 1:
                Intent i = new Intent(this, PreferenciasRestaurantes.class);
                startActivity(i);
                break;
            case 2:
                Intent j = new Intent(this, PreferenciasOfertasTuristicas.class);
                startActivity(j);
                break;
            }
        }
}



