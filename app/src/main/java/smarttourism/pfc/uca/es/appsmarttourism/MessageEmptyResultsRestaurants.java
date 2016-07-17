package smarttourism.pfc.uca.es.appsmarttourism;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by AnaMari on 19/06/2016.
 */

/*-----------------------------------------------------------------------------------------------*/
/* Clase que crea la actividad MessageEmptyResultsRestaurants. Esta actividad se crea cuando el  */
/* listado de las ofertas turisticas a resolver es vacio y se muestra al usuario un mensaje      */
/* informando de esto.                                                                           */
/*-----------------------------------------------------------------------------------------------*/

public class MessageEmptyResultsRestaurants extends AppCompatActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.messageemptyresultsrestaurants);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}
