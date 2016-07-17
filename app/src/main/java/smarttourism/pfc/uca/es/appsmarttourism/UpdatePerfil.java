package smarttourism.pfc.uca.es.appsmarttourism;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by AnaMari on 16/03/2016.
 */

/*-----------------------------------------------------------------------------------------------*/
/* Clase que permite actualizar los datos del usuario actual.                                    */
/*-----------------------------------------------------------------------------------------------*/
public class UpdatePerfil extends AppCompatActivity {
    User user = new User();
    Spinner cmbCountrySource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.update_perfil);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Bundle extras = getIntent().getExtras();
        user.setUsername(extras.getString("username"));
        user.setName(extras.getString("name"));
        user.setSurname(extras.getString("surname"));
        user.setPassword(extras.getString("password"));
        user.setSeed(extras.getString("seed"));
        user.setAge(extras.getInt("age"));
        user.setCountrySource(extras.getString("countrySource"));
        String dateInString = extras.get("dateBorn").toString();
        Date date = convertStringToDate(dateInString);
        user.setDateBorn(date);
        actualizarVista();
    }


    //Precondiciones: Recibe la fecha de nacimiento en String.
    //Postcondiciones: Devuelve la fecha de nacimiento en Date.
    private Date convertStringToDate(String dateString){
        String[] dateStringArray = dateString.split(" ");
        String[] hourStringArray = dateStringArray[3].split(":");
        Calendar c = Calendar.getInstance();
        int month = obtenerMes(dateStringArray[1]);
        c.set(Integer.parseInt(dateStringArray[5]), month, Integer.parseInt(dateStringArray[2]),
                Integer.parseInt(hourStringArray[0]), Integer.parseInt(hourStringArray[1]),
                Integer.parseInt(hourStringArray[2]));
        return c.getTime();
    }

    //Precondiciones: Recibe la fecha de nacimiento en String.
    //Postcondiciones: Devuelve la fecha de nacimiento en Date, pero con el formato especificado.
    private Date convertStringToDate2(String dateString){
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        try {
        date = format.parse(dateString);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return date;
    }

    //Precondiciones: Recibe el mes en String.
    //Postcondiciones: Devuelve el mes en entero.
    private Integer obtenerMes(String mes) {
        int month = 0;
        switch (mes) {
            case "Jan": month = 0; break;
            case "Feb": month = 1; break;
            case "Mar": month = 2; break;
            case "Apr": month = 3; break;
            case "May": month = 4; break;
            case "Jun": month = 5; break;
            case "Jul": month = 6; break;
            case "Aug": month = 7; break;
            case "Sep": month = 8; break;
            case "Oct": month = 9; break;
            case "Nov": month = 10; break;
            case "Dec": month = 11; break;
        }
        return month;
    }

    //Precondiciones: Ninguno.
    //Postcondiciones:  Actualizamos los valores en la vista.
    public void actualizarVista(){
        TextView nombre = (TextView) findViewById(R.id.etxNombreUsuario);
        nombre.setText(user.getName());
        TextView apellidos = (TextView) findViewById(R.id.etxApellidosUsuario);
        apellidos.setText(user.getSurname());
        TextView edad = (TextView) findViewById(R.id.etxEdad);
        edad.setText(Integer.toString(user.getAge()));

        //Inicializamos el spinner del país de origen
        cmbCountrySource = (Spinner)findViewById(R.id.spinnerCountrySource);
        ArrayAdapter adaptador = ArrayAdapter.createFromResource(UpdatePerfil.this,
                R.array.countries, android.R.layout.simple_spinner_item);
        cmbCountrySource.setAdapter(adaptador);

        String dateFormateada = formatearFecha(user.getDateBorn().toString());
        TextView fechaNacimiento = (TextView) findViewById(R.id.etxFechaNacimiento);
        fechaNacimiento.setText(dateFormateada);
    }

    //Precondiciones: Recibimos la fecha en string.
    //Postcondiciones: Devuelve la fecha en string pero en formato: dd/mm/aaaa.
    private String formatearFecha(String fecha){
        String[] fechaArray = fecha.split(" ");
        int mes = obtenerMes(fechaArray[1])+1;
        String mesNumero = String.format("%02d", mes) ;
        return fechaArray[2]+"/"+mesNumero+"/"+fechaArray[5];
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_save, menu);
        return true;  /** true -> el menu ya esta visible */
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.accion_save:
                User userNew = new User();
                userNew.setUsername(user.getUsername());
                TextView etxName = (TextView) findViewById(R.id.etxNombreUsuario);
                if(!etxName.getText().toString().trim().equals(""))
                    userNew.setName(etxName.getText().toString());
                else{
                    Intent i = new Intent(UpdatePerfil.this,UpdatePerfil.class);
                    i.putExtra("username",user.getUsername());
                    i.putExtra("name", user.getName());
                    i.putExtra("surname", user.getSurname());
                    i.putExtra("password", user.getPassword());
                    i.putExtra("seed", user.getSeed());
                    i.putExtra("age", user.getAge());
                    i.putExtra("dateBorn", user.getDateBorn());
                    i.putExtra("countrySource", user.getCountrySource());
                    startActivity(i);
                    Toast toast1 = Toast.makeText(getApplicationContext(),
                            "Nombre del usuario obligatorio.", Toast.LENGTH_SHORT);
                    toast1.show();
                    return false;
                }

                TextView etxSurname = (TextView) findViewById(R.id.etxApellidosUsuario);
                if(!etxSurname.getText().toString().trim().equals(""))
                    userNew.setSurname(etxSurname.getText().toString());
                else{
                    Intent i = new Intent(UpdatePerfil.this,UpdatePerfil.class);
                    i.putExtra("username",user.getUsername());
                    i.putExtra("name", user.getName());
                    i.putExtra("surname", user.getSurname());
                    i.putExtra("password", user.getPassword());
                    i.putExtra("seed", user.getSeed());
                    i.putExtra("age", user.getAge());
                    i.putExtra("dateBorn", user.getDateBorn());
                    i.putExtra("countrySource", user.getCountrySource());
                    startActivity(i);
                    Toast toast1 = Toast.makeText(getApplicationContext(),
                            "Apellido del usuario obligatorio.", Toast.LENGTH_SHORT);
                    toast1.show();
                    return false;
                }

                TextView etxAge = (TextView) findViewById(R.id.etxEdad);
                if(!etxAge.getText().toString().trim().equals("")) {
                    int n = Integer.valueOf(etxAge.getText().toString());
                    userNew.setAge(n);
                } else{
                    Intent i = new Intent(UpdatePerfil.this,UpdatePerfil.class);
                    i.putExtra("username",user.getUsername());
                    i.putExtra("name", user.getName());
                    i.putExtra("surname", user.getSurname());
                    i.putExtra("password", user.getPassword());
                    i.putExtra("seed", user.getSeed());
                    i.putExtra("age", user.getAge());
                    i.putExtra("dateBorn", user.getDateBorn());
                    i.putExtra("countrySource", user.getCountrySource());
                    startActivity(i);
                    Toast toast1 = Toast.makeText(getApplicationContext(),
                            "Edad del usuario obligatorio.", Toast.LENGTH_SHORT);
                    toast1.show();
                    return false;
                }

                TextView etxFechanacimiento = (TextView) findViewById(R.id.etxFechaNacimiento);
                if(!etxFechanacimiento.getText().toString().trim().equals("")) {
                    Date dateNueva = convertStringToDate2(etxFechanacimiento.getText().toString());
                    userNew.setDateBorn(dateNueva);
                }else{
                    Intent i = new Intent(UpdatePerfil.this,UpdatePerfil.class);
                    i.putExtra("username",user.getUsername());
                    i.putExtra("name", user.getName());
                    i.putExtra("surname", user.getSurname());
                    i.putExtra("password", user.getPassword());
                    i.putExtra("seed", user.getSeed());
                    i.putExtra("age", user.getAge());
                    i.putExtra("dateBorn", user.getDateBorn());
                    i.putExtra("countrySource", user.getCountrySource());
                    startActivity(i);
                    Toast toast1 = Toast.makeText(getApplicationContext(),
                            "Fecha de nacimiento del usuario obligatorio.", Toast.LENGTH_SHORT);
                    toast1.show();
                    return false;
                }

                Spinner spinnerCountrySource = (Spinner) findViewById(R.id.spinnerCountrySource);
                if(!spinnerCountrySource.getSelectedItem().toString().equals("")) {
                    userNew.setCountrySource(spinnerCountrySource.getSelectedItem().toString());
                }else{
                    Intent i = new Intent(UpdatePerfil.this,UpdatePerfil.class);
                    i.putExtra("username",user.getUsername());
                    i.putExtra("name", user.getName());
                    i.putExtra("surname", user.getSurname());
                    i.putExtra("password", user.getPassword());
                    i.putExtra("seed", user.getSeed());
                    i.putExtra("age", user.getAge());
                    i.putExtra("dateBorn", user.getDateBorn());
                    i.putExtra("countrySource", user.getCountrySource());
                    startActivity(i);
                    Toast toast1 = Toast.makeText(getApplicationContext(),
                            "País de origen del usuario obligatorio.", Toast.LENGTH_SHORT);
                    toast1.show();
                    return false;
                }

                userNew.setSeed(user.getSeed());
                userNew.setPassword(user.getPassword());

                LoadWebServiceUpdateUserASYNC loadWebServiceUpdateUserASYNC = new LoadWebServiceUpdateUserASYNC();
                loadWebServiceUpdateUserASYNC.execute(userNew);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /*-------------------------------------------------------------------------------------------*/
    /* Clase AsyncTask que establece la conexion entre la aplicacion y el ESB.                   */
    /* Arquitectura REST. Actualiza los datos del usuario.                                       */
    /*-------------------------------------------------------------------------------------------*/
    private class LoadWebServiceUpdateUserASYNC extends AsyncTask<User, Integer, Boolean> {
        @Override
        protected Boolean doInBackground(User... params) {
            boolean resul = true;
            HttpClient httpClient = new DefaultHttpClient();
            //String URL = "http://192.168.1.102:8083/userServices/updateUserServices?username=";
            String URL = "http://"+ MainActivity.getDireccionIP() + ":8083/userServices/updateUserServices?username=";
            User userNew = new User(params[0]);
            try {
                JSONObject dato = new JSONObject();
                dato.put("username", userNew.getUsername());
                dato.put("name", userNew.getName());
                dato.put("surname", userNew.getSurname());
                dato.put("password", userNew.getPassword());
                dato.put("seed", userNew.getSeed());
                dato.put("age", userNew.getAge());
                dato.put("dateBorn", userNew.getDateBorn());
                dato.put("countrySource", userNew.getCountrySource());
                String urlJSON  = dato.toString();
                urlJSON = urlJSON.replaceAll("\"","");
                URL = URL + userNew.getUsername();

                HttpPut put = new HttpPut(URL);
                put.setHeader("content-type", "application/json");

                StringEntity entity = new StringEntity(urlJSON);
                put.setEntity(entity);

                HttpResponse resp = httpClient.execute(put);
                String respStr = EntityUtils.toString(resp.getEntity());

            } catch (Exception ex) {
                resul = false;
            }
            return resul;
        }

        @Override
        protected void onPostExecute(Boolean result) {
            if (result) {
                Toast toast1 = Toast.makeText(getApplicationContext(),
                        "Perfil del usuario actualizado correctamente.", Toast.LENGTH_SHORT);
                toast1.show();
            }else{
                Toast toast1 = Toast.makeText(getApplicationContext(),
                        "No se ha podido actualizar el perfil del usuario.", Toast.LENGTH_SHORT);
                toast1.show();
            }
        }
    }
}
