package smarttourism.pfc.uca.es.appsmarttourism;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by AnaMari on 27/03/2016.
 */

/*-----------------------------------------------------------------------------------------------*/
/* Clase que crea la actividad Requisitos. Esta actividad crea una pantalla para el apartado de  */
/* Ayuda, el cual informa al usuario de los requisitos necesarios para ejecutar la aplicacion.   */
/*-----------------------------------------------------------------------------------------------*/

public class Requisitos extends AppCompatActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.requisitos);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}
