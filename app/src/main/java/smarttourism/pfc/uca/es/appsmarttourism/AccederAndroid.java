package smarttourism.pfc.uca.es.appsmarttourism;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by AnaMari on 27/03/2016.
 */

/*-----------------------------------------------------------------------------------------------*/
/* Clase que crea la actividad AccederAndroid. Esta actividad crea una pantalla para el apartado */
/* de Ayuda, el cual responde a la pregunta de como se puede acceder a la aplicacion desde un    */
/* dispositivo Android.                                                                          */
/*-----------------------------------------------------------------------------------------------*/

public class AccederAndroid extends AppCompatActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acceder_android);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}
