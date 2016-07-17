package smarttourism.pfc.uca.es.appsmarttourism;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by AnaMari on 26/03/2016.
 */

/*-----------------------------------------------------------------------------------------------*/
/* Clase que crea la actividad QueConsultar. Esta actividad crea una pantalla para el apartado   */
/* de Ayuda, el cual responde a la pregunta que se puede consultar en la aplicacion.             */
/*-----------------------------------------------------------------------------------------------*/
public class QueConsultar extends AppCompatActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.que_consultar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}