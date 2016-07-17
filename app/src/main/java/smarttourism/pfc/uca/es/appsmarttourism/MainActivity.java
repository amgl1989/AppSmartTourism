package smarttourism.pfc.uca.es.appsmarttourism;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.design.widget.NavigationView;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.accounts.Account;
import android.accounts.AccountManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;

/*-----------------------------------------------------------------------------------------------*/
/* Clase principal                                                                               */
/*-----------------------------------------------------------------------------------------------*/

public class MainActivity extends AppCompatActivity implements android.support.design.widget.NavigationView.OnNavigationItemSelectedListener{
    public static String username,nombreapellidos;
    public String latitudeUser;
    public String longitudeUser;
    public User user;
    private GoogleApiClient client;
    private static String direccionIP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        boolean flag = false;
        myLocationListener myLocationListener;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(conectadoWifi().equals(false)) {
            Toast.makeText(this, "Conecte su dispositivo a una red " +
                    "Wifi para poder acceder a SmartTourism.", Toast.LENGTH_LONG).show();
            flag=true;
        }else {
            myLocationListener = new myLocationListener(this);
            if (!myLocationListener.canGetLocation()) {
                Toast.makeText(this, "Conecte la Ubicacion de su dispositivo para poder " +
                        "acceder a SmartTourism.", Toast.LENGTH_LONG).show();
                flag = true;
            }else{
                //latitudeUser = "36.5303081";
                //longitudeUser = "-6.3031496";
                latitudeUser = String.valueOf(myLocationListener.getLatitude());
                longitudeUser = String.valueOf(myLocationListener.getLongitude());
            }
        }
        if(flag==true)
            finish();

        FragmentManager fragmentManager = getSupportFragmentManager();

        ArrayList<String> usernameList = getAccountManagerUser();
        if(username==null || username.trim()=="") {
            if (usernameList.size() == 1)
                username = usernameList.get(0);
            else {
                if (usernameList.size() == 0) {
                    Toast.makeText(this, "No dispone de una cuenta de Gmail para poder acceder a SmartTourism.", Toast.LENGTH_LONG).show();
                    finish();
                } else {
                    DialogoSeleccionUsername dialogo = new DialogoSeleccionUsername();
                    Bundle args = new Bundle();
                    args.putStringArrayList("usernameList", usernameList);
                    dialogo.setArguments(args);
                    dialogo.show(fragmentManager, "SeleccionUsername");
                }
            }
        }

        //Cuadro de dialogo que solicita la direccion IP
        if(direccionIP==null || direccionIP.trim()=="") {
            DialogoDireccionIP dialogoDireccionIP = new DialogoDireccionIP();
            dialogoDireccionIP.show(fragmentManager, "DireccionIP");
        }

        //Inicializamos el nombre y apellidos del usuario.
        if(username!= null) {
          inicializarOnCreate();
        }
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    //Precondiciones: Recibe por variable global el nombre de usuario.
    //Postcondiciones: Inicializamos los datos del usuario para el navegador deslizable del menu.
    private void inicializarOnCreate(){
        user = new User(username);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        LoadWebServiceUserASYNC loadWebServiceUserASYNC = new LoadWebServiceUserASYNC();
        loadWebServiceUserASYNC.execute(username);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        TextView usernameTxv = (TextView) findViewById(R.id.username);
        usernameTxv.setText(String.valueOf(user.getUsername()));
    }

    //Precondiciones: Ninguna.
    //Postcondiciones: Devuelve true si el dispositivo está conectado a la Wifi y false en caso contrario.
    protected Boolean conectadoWifi(){
        ConnectivityManager connectivity = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            NetworkInfo info = connectivity.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            if (info != null) {
                if (info.isConnected()) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    //Precondiciones: Ninguna.
    //Postcondiciones: Devuelve la dirección IP introducida.
    public static String getDireccionIP() {
        return direccionIP;
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        //Llamada a los diferentes controladores según la opción del menú seleccionada.
        if (id == R.id.nav_restaurantes) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            DialogoFiltradoRestaurantes  dialogo = new DialogoFiltradoRestaurantes ();
            dialogo.show(fragmentManager, "FiltradoRestaurantes");
        }
        if (id == R.id.nav_ofertasturisticas) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            DialogoFiltradoOfertasTuristicas dialogo = new DialogoFiltradoOfertasTuristicas();
            dialogo.show(fragmentManager, "FiltradoOfertasTuristicas");
        }
        if (id == R.id.nav_usuario) {
            Intent i = new Intent(this, MyAccount.class);
            i.putExtra("username",username);
            startActivity(i);
        }
        if (id == R.id.nav_acercade) {
            Intent i = new Intent(this, AcercaDe.class);
            startActivity(i);
        }
        if (id == R.id.nav_ayuda) {
            Intent i = new Intent(this, HelpCenter.class);
            i.putExtra("username",username);
            startActivity(i);
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    //Precondiciones: Ninguna.
    //Postcondiciones: Devuelve el listado de nombres de usuario (Cuentas de Gmail) que están
    //   vinculadas en el dispositivo móvil.
    private ArrayList<String> getAccountManagerUser() {
        AccountManager manager = (AccountManager) getSystemService(ACCOUNT_SERVICE);
        Account[] listAccount = manager.getAccounts();
        ArrayList<String> usernameList = new ArrayList<String>();
        for (int i = 0; i < listAccount.length; i++) {
            if (listAccount[i].type.equals("com.google")) {
                usernameList.add(listAccount[i].name);
            }
        }
        return usernameList;
    }

    //Precondiciones: Ninguna.
    //Postcondiciones: Devuelve el nombre de usuario.
    public static String getUsername() {
        return username;
    }

    @Override
    public void onStart() {
        super.onStart();
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://smarttourism.pfc.uca.es.appsmarttourism/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://smarttourism.pfc.uca.es.appsmarttourism/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }

    /*-------------------------------------------------------------------------------------------*/
    /* Clase que construye el cuadro de dialogo para seleccionar el nombre de usuario con el que */
    /* el usuario desea acceder a la aplicacion.                                                 */
    /*-------------------------------------------------------------------------------------------*/
    public class DialogoSeleccionUsername extends DialogFragment {
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            List<String> usernameList = getArguments().getStringArrayList("usernameList");
            final String[] usernameListFinal = new String[usernameList.size()];
            for(int i=0; i<usernameList.size(); i++)
                usernameListFinal[i] = usernameList.get(i);

            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle("Seleccione un usuario")
                    .setItems(usernameListFinal, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int item) {
                            username = usernameListFinal[item];
                            inicializarOnCreate();
                        }
                    });
            return builder.create();
        }
    }

    /*-------------------------------------------------------------------------------------------*/
    /* Clase que construye el cuadro de diálogo que selecciona si desea filtrar los restaurantes */
    /* o mostrarlos todos.                                                                       */
    /*-------------------------------------------------------------------------------------------*/
    public class DialogoFiltradoRestaurantes extends DialogFragment {
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setMessage(R.string.filtradoRestaurante);
            builder.setTitle(R.string.confirmacion);
            builder.setPositiveButton(R.string.si, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    DialogoNumSeating dialogoNumSeating = new DialogoNumSeating();
                    dialogoNumSeating.show(fragmentManager, "FiltradoNumeroAsientos");
                    dialog.cancel();
                }
            });
            builder.setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    //String URL = "http://192.168.1.102:8081/smartTourism/RestaurantService/restaurant?username=";
                    String URL = "http://"+ getDireccionIP()+":8081/smartTourism/RestaurantService/restaurant?username=";
                    URL = URL + username + "&latitude=" + latitudeUser + "&longitude=" + longitudeUser + "&all=1";

                    Intent i = new Intent(MainActivity.this, RestaurantList.class);
                    i.putExtra("url", URL);
                    startActivity(i);
                    dialog.cancel();
                }
            });
            return builder.create();
        }
    }

    /*-------------------------------------------------------------------------------------------*/
    /* Clase que construye el cuadro de dialogo que selecciona si desea filtrar las ofertas      */
    /* turísticas o mostrarlas todas.                                                            */
    /*-------------------------------------------------------------------------------------------*/
    public class DialogoFiltradoOfertasTuristicas extends DialogFragment {
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setMessage(R.string.filtradoOfertasTuristicas)
                    .setTitle(R.string.confirmacion)
                    .setPositiveButton(R.string.si, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            //String URL = "http://192.168.1.102:8082/smartTourism/TouristOfferService/touristoffers?username=";
                            String URL = "http://"+ getDireccionIP()+":8082/smartTourism/TouristOfferService/touristoffers?username=";
                            URL = URL + username + "&latitude=" + latitudeUser + "&longitude=" + longitudeUser + "&all=0";

                            Intent i = new Intent(MainActivity.this, TouristOfferList.class);
                            i.putExtra("url", URL);
                            startActivity(i);
                            dialog.cancel();
                        }
                    })
                    .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            //String URL = "http://192.168.1.102:8082/smartTourism/TouristOfferService/touristoffers?username=";
                            String URL = "http://"+ getDireccionIP()+":8082/smartTourism/TouristOfferService/touristoffers?username=";
                            URL = URL + username + "&latitude=" + latitudeUser + "&longitude=" + longitudeUser + "&all=1";

                            Intent i = new Intent(MainActivity.this, TouristOfferList.class);
                            i.putExtra("url", URL);
                            startActivity(i);
                            dialog.cancel();
                        }
                    });
            return builder.create();
        }
    }

    /*-------------------------------------------------------------------------------------------*/
    /* Clase que construye el cuadro de diálogo que permite introducir el número de asientos     */
    /* solicitados por el usuario.                                                               */
    /*-------------------------------------------------------------------------------------------*/
    public class DialogoNumSeating extends DialogFragment {
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle(R.string.numeroasientos);
            builder.setMessage(R.string.solicitudnumeroasientos);
            final EditText input = new EditText(getActivity());
            input.setId(0);
            builder.setView(input);
            builder.setPositiveButton(R.string.si, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int id) {
                    //String URL = "http://192.168.1.102:8081/smartTourism/RestaurantService/restaurant?username=";
                    String URL = "http://"+ getDireccionIP()+ ":8081/smartTourism/RestaurantService/restaurant?username=";
                    URL = URL + username + "&latitude=" + latitudeUser + "&longitude=" + longitudeUser + "&all=0&seating=";
                    String value = input.getText().toString();
                    if (value.trim().equalsIgnoreCase("")) {
                        Toast.makeText(MainActivity.this, "Obligatorio introducir el número de asientos necesarios.", Toast.LENGTH_LONG).show();
                        dialog.cancel();
                        FragmentManager fragmentManager = getSupportFragmentManager();
                        DialogoNumSeating dialogoNumSeating = new DialogoNumSeating();
                        dialogoNumSeating.show(fragmentManager, "FiltradoNumeroAsientos");
                        dialog.cancel();
                    } else {
                        URL = URL + value;

                        Intent i = new Intent(MainActivity.this, RestaurantList.class);
                        i.putExtra("url", URL);
                        startActivity(i);
                        dialog.cancel();
                    }
                }
            }).setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int id) {
                    //String URL = "http://192.168.1.102:8081/smartTourism/RestaurantService/restaurant?username=";
                    String URL = "http://"+ getDireccionIP() +":8081/smartTourism/RestaurantService/restaurant?username=";
                    URL = URL + username + "&latitude=" + latitudeUser + "&longitude=" + longitudeUser + "&all=0&seating=1000";

                    Intent i = new Intent(MainActivity.this, RestaurantList.class);
                    i.putExtra("url", URL);
                    startActivity(i);
                    dialog.cancel();
                }
            });

            return builder.create();
        }
    }


    /*-------------------------------------------------------------------------------------------*/
    /* Clase que construye el cuadro de diálogo que  permite introducir la dirección IP de la    */
    /* red Wifi en la que el usuario se encuentra.                                               */
    /*-------------------------------------------------------------------------------------------*/
    public class DialogoDireccionIP extends DialogFragment {
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle(R.string.direccionIP);
            builder.setMessage(R.string.direccionIP1);
            final EditText input = new EditText(getActivity());
            input.setId(0);
            builder.setView(input);
            builder.setPositiveButton(R.string.aceptar, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int id) {
                    direccionIP = input.getText().toString();
                    if (direccionIP.trim().equalsIgnoreCase("")) {
                        Toast.makeText(MainActivity.this, "Obligatorio introducir la dirección IP.", Toast.LENGTH_LONG).show();
                        dialog.cancel();
                        FragmentManager fragmentManager = getSupportFragmentManager();
                        DialogoDireccionIP dialogoDireccionIP = new DialogoDireccionIP();
                        dialogoDireccionIP.show(fragmentManager, "DireccionIP");
                    }
                    dialog.cancel();
                }
            });
            return builder.create();
        }
    }

    /*-------------------------------------------------------------------------------------------*/
    /* Clase AsyncTask que establece la conexión entre la aplicación y el ESB.                   */
    /* Arquitectura REST. Obtiene el nombre y apellido del usuario actual.                       */
    /*-------------------------------------------------------------------------------------------*/
   private class LoadWebServiceUserASYNC extends AsyncTask<String, Integer, Boolean> {
        private User user = new User();
        @Override
        protected Boolean doInBackground(String... params) {
            boolean resul = true;
            HttpClient httpClient = new DefaultHttpClient();
            HttpContext localContext = new BasicHttpContext();

            String username = params[0];
            //String URL = "http://192.168.1.102:8083/userServices/selectUserServices?username=" + username;
            String URL = "http://"+ getDireccionIP() + ":8083/userServices/selectUserServices?username=" + username;
            HttpGet del = new HttpGet(URL);
            try {
                HttpResponse resp = httpClient.execute(del, localContext);
                HttpEntity entity = resp.getEntity();
                String respStr = EntityUtils.toString(entity);
                JSONObject respJSON = new JSONObject(respStr);

                user.setUsername(username);
                if(respStr.contains("name"))
                    user.setName(respJSON.getString("name"));
                if(respStr.contains("surname"))
                    user.setSurname(respJSON.getString("surname"));

                if(user.getName() == null || user.getName().trim().isEmpty())
                    resul = false;
            } catch (Exception ex) {
                resul = false;
            }
            return resul;
        }

        @Override
        protected void onPostExecute(Boolean result) {
            if (result) {
               nombreapellidos = user.getName() + " " + user.getSurname();
               TextView nombreTxv = (TextView) findViewById(R.id.nombreapellidos);
               nombreTxv.setText(String.valueOf(nombreapellidos));
            }else{
                Intent i = new Intent(MainActivity.this,CrearPerfil.class);
                i.putExtra("username",user.getUsername());
                startActivity(i);
            }
        }
    }
}
