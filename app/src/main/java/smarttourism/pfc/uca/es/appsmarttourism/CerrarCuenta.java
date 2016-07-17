package smarttourism.pfc.uca.es.appsmarttourism;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by AnaMari on 26/03/2016.
 */

/*-----------------------------------------------------------------------------------------------*/
/* Clase que crea la actividad CerrarCuenta. Esta actividad crea una pantalla para el apartado   */
/* de Ayuda, el cual responde a la pregunta de como se puede cerrar la cuenta de esta aplicacion.*/
/*-----------------------------------------------------------------------------------------------*/

public class CerrarCuenta extends AppCompatActivity{
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cerrar_cuenta);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}
