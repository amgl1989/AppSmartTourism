package smarttourism.pfc.uca.es.appsmarttourism;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by AnaMari on 30/05/2016.
 */

/*-----------------------------------------------------------------------------------------------*/
/* Clase que da simula la carga de la aplicacion.                                                */
/*-----------------------------------------------------------------------------------------------*/
public class SplashScreen extends Activity {
    //Tiempo de duracion del splash screen
    private static final long SPLASH_SCREEN_DELAY = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.splash_screen);
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                Intent mainIntent = new Intent().setClass(
                        SplashScreen.this, MainActivity.class);
                startActivity(mainIntent);
                finish();
            }
        };

        //Simula la carga de la aplicacion a traves del timer.
        Timer timer = new Timer();
        timer.schedule(task, SPLASH_SCREEN_DELAY);
    }
}