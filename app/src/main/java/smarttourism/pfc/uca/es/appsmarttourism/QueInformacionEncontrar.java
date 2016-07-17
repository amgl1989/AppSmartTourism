package smarttourism.pfc.uca.es.appsmarttourism;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by AnaMari on 01/06/2016.
 */

/*-----------------------------------------------------------------------------------------------*/
/* Clase que crea la actividad QueInformacionEncontrar. Esta actividad crea una pantalla para el */
/* apartado de Ayuda, el cual informa al usuario de la informacion que se puede encontrar en     */
/* esta aplicacion.                                                                              */
/*-----------------------------------------------------------------------------------------------*/

public class QueInformacionEncontrar extends AppCompatActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.que_informacion_encontrar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}