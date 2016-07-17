package smarttourism.pfc.uca.es.appsmarttourism;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by AnaMari on 19/06/2016.
 */

/*-----------------------------------------------------------------------------------------------*/
/* Clase que crea la actividad MessageEmptyResultsTouristOffers. Esta actividad se crea cuando el*/
/* listado de los restaurantes a resolver es vacio y se muestra al usuario un mensaje informando */
/* de esto.                                                                                      */
/*-----------------------------------------------------------------------------------------------*/

public class MessageEmptyResultsTouristOffers extends AppCompatActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.messageemptyresultstouristoffers);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}
