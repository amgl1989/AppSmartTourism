package smarttourism.pfc.uca.es.appsmarttourism;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by AnaMari on 27/03/2016.
 */

/*-----------------------------------------------------------------------------------------------*/
/* Clase que crea la actividad QueEsMiCuenta. Esta actividad crea una pantalla para el apartado  */
/* de Ayuda, el cual responde a la pregunta ¿Que es Mi cuenta?                                   */
/*-----------------------------------------------------------------------------------------------*/

public class QueEsMiCuenta  extends AppCompatActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.que_es_micuenta);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}
