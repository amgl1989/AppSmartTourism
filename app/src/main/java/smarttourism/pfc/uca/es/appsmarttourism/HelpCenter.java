package smarttourism.pfc.uca.es.appsmarttourism;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by AnaMari on 06/08/2015.
 */

/*-----------------------------------------------------------------------------------------------*/
/* Clase que crea la actividad HelpCenter. Esta actividad crea una pantalla para el              */
/* apartado de Ayuda, el cual responde a los diferentes apartados en que se divide la ayuda.     */
/*-----------------------------------------------------------------------------------------------*/

public class HelpCenter extends AppCompatActivity {
    ListView lstHelpCenter;
    String username = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.restaurantes);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Resources res = getResources();

        final List<String> datos = new ArrayList<String>();
        datos.add(res.getString(R.string.caracteristicassitio));
        datos.add(res.getString(R.string.asistenciatecnica));
        datos.add(res.getString(R.string.contacto));
        AdaptadorMyAccount adaptador =  new AdaptadorMyAccount(HelpCenter.this,datos);
        lstHelpCenter = (ListView) findViewById(R.id.listViewRestaurante);
        lstHelpCenter.setAdapter(adaptador);
        lstHelpCenter.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> a, View v, int position, long id) {
                Intent i;
                switch (position) {
                    case 0:
                        i = new Intent(HelpCenter.this, CaracteristicasSitio.class);
                        startActivity(i);
                        break;
                    case 1:
                        i = new Intent(HelpCenter.this, AsistenciaTecnica.class);
                        startActivity(i);
                        break;
                    case 2:
                        username = MainActivity.getUsername();
                        i = new Intent(HelpCenter.this, Contacto.class);
                        i.putExtra("username",username);
                        startActivity(i);
                        break;
                    default:
                        break;
                }
            }
        });
    }
}
