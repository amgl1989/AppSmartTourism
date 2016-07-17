package smarttourism.pfc.uca.es.appsmarttourism;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by AnaMari on 26/03/2016.
 */


/*-----------------------------------------------------------------------------------------------*/
/* Clase que crea la actividad AsistenciaTecnica. Esta actividad crea una pantalla para el       */
/* apartado de Ayuda, el cual responde al listado de preguntas frecuentes correspondientes a la  */
/* "Asistencia Tecnica".                                                                         */
/*-----------------------------------------------------------------------------------------------*/

public class AsistenciaTecnica extends AppCompatActivity {

    ListView lstOpciones;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.restaurantes);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Resources res = getResources();
        final List<String> datos = new ArrayList<String>();
        datos.add(res.getString(R.string.comocierrocuenta));
        datos.add(res.getString(R.string.compartirinformacion));
        datos.add(res.getString(R.string.queesmicuenta));
        datos.add(res.getString(R.string.accederAndroid));
        datos.add(res.getString(R.string.accederIphone));
        datos.add(res.getString(R.string.accederTablet));
        AdaptadorMyAccount adaptador =  new AdaptadorMyAccount(AsistenciaTecnica.this,datos);
        lstOpciones = (ListView) findViewById(R.id.listViewRestaurante);
        lstOpciones.setAdapter(adaptador);
        lstOpciones.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> a, View v, int position, long id) {
                Intent i;
                switch (position) {
                    case 0:
                        i = new Intent(AsistenciaTecnica.this, CerrarCuenta.class);
                        startActivity(i);
                        break;
                    case 1:
                        i = new Intent(AsistenciaTecnica.this, CompartirInformacion.class);
                        startActivity(i);
                        break;
                    case 2:
                        i = new Intent(AsistenciaTecnica.this, QueEsMiCuenta.class);
                        startActivity(i);
                        break;
                    case 3:
                        i = new Intent(AsistenciaTecnica.this, AccederAndroid.class);
                        startActivity(i);
                        break;
                    case 4:
                        i = new Intent(AsistenciaTecnica.this, AccederIphone.class);
                        startActivity(i);
                        break;
                    case 5:
                        i = new Intent(AsistenciaTecnica.this, AccederTablet.class);
                        startActivity(i);
                        break;
                    default:
                        break;
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_help, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.accion_inicio:
                Intent i = new Intent(this, MainActivity.class);
                startActivityForResult(i, 0);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}