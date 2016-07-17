package smarttourism.pfc.uca.es.appsmarttourism;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by AnaMari on 26/03/2016.
 */

/*-----------------------------------------------------------------------------------------------*/
/* Clase que crea la actividad AccederTablet. Esta actividad crea una pantalla para el apartado */
/* de Ayuda, el cual responde a la pregunta de como se puede acceder a la aplicacion desde un    */
/* dispositivo Tablet.                                                                          */
/*-----------------------------------------------------------------------------------------------*/

public class AccederTablet extends AppCompatActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acceder_tablet);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}
