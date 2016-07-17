package smarttourism.pfc.uca.es.appsmarttourism;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by AnaMari on 26/03/2016.
 */

/*-----------------------------------------------------------------------------------------------*/
/* Clase que crea la actividad CompartirInformacion. Esta actividad crea una pantalla para el    */
/* apartado de Ayuda, el cual responde a la pregunta de si se comparte la informacion a traves de*/
/* la aplicacion.                                                                                */
/*-----------------------------------------------------------------------------------------------*/

public class CompartirInformacion extends AppCompatActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.compartir_informacion);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}
