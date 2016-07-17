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
import java.util.Date;

/**
 * Created by AnaMari on 16/06/2016.
 */
public class CrearPerfil extends AppCompatActivity {
        User user = new User();
        Spinner cmbCountrySource;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.update_perfil);
            Bundle extras = getIntent().getExtras();
            user.setUsername(extras.getString("username"));

            //Inicializamos el spinner del pais de origen del usuario
            cmbCountrySource = (Spinner)findViewById(R.id.spinnerCountrySource);
            ArrayAdapter adaptador = ArrayAdapter.createFromResource(CrearPerfil.this,
                    R.array.countries, android.R.layout.simple_spinner_item);
            cmbCountrySource.setAdapter(adaptador);
        }

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

        @Override
        public boolean onCreateOptionsMenu(Menu menu) {
            // Inflate the menu; this adds items to the action bar if it is present.
            getMenuInflater().inflate(R.menu.menu_save, menu);
            return true;  /** true -> el menu ya esta visible */
        }

      /*  @Override
        public boolean onOptionsItemSelected(MenuItem item) {
            switch(item.getItemId()){
                case R.id.accion_save:
                    User userNew = new User();
                    userNew.setUsername(user.getUsername());
                    TextView etxName = (TextView) findViewById(R.id.etxNombreUsuario);
                    userNew.setName(etxName.getText().toString());
                    TextView etxSurname = (TextView) findViewById(R.id.etxApellidosUsuario);
                    userNew.setSurname(etxSurname.getText().toString());
                    TextView etxAge = (TextView) findViewById(R.id.etxEdad);
                    int n = Integer.valueOf(etxAge.getText().toString());
                    userNew.setAge(n);
                    TextView etxFechanacimiento = (TextView) findViewById(R.id.etxFechaNacimiento);
                    Date dateNueva = convertStringToDate2(etxFechanacimiento.getText().toString());
                    userNew.setDateBorn(dateNueva);
                    Spinner spinnerCountrySource = (Spinner) findViewById(R.id.spinnerCountrySource);
                    userNew.setCountrySource(spinnerCountrySource.getSelectedItem().toString());

                    LoadWebServiceInsertUserASYNC loadWebServiceInsertUserASYNC = new LoadWebServiceInsertUserASYNC();
                    loadWebServiceInsertUserASYNC.execute(userNew);
                    return true;
                default:
                    return super.onOptionsItemSelected(item);
            }
        }*/



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
                        Intent i = new Intent(CrearPerfil.this,CrearPerfil.class);
                        i.putExtra("username",user.getUsername());
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
                        Intent i = new Intent(CrearPerfil.this,CrearPerfil.class);
                        i.putExtra("username",user.getUsername());
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
                        Intent i = new Intent(CrearPerfil.this,CrearPerfil.class);
                        i.putExtra("username",user.getUsername());
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
                        Intent i = new Intent(CrearPerfil.this,CrearPerfil.class);
                        i.putExtra("username",user.getUsername());
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
                        Intent i = new Intent(CrearPerfil.this,CrearPerfil.class);
                        i.putExtra("username",user.getUsername());
                        startActivity(i);
                        Toast toast1 = Toast.makeText(getApplicationContext(),
                                "País de origen del usuario obligatorio.", Toast.LENGTH_SHORT);
                        toast1.show();
                        return false;
                    }

                    LoadWebServiceInsertUserASYNC loadWebServiceInsertUserASYNC = new LoadWebServiceInsertUserASYNC();
                    loadWebServiceInsertUserASYNC.execute(userNew);
                    return true;
                default:
                    return super.onOptionsItemSelected(item);
            }
        }

        private class LoadWebServiceInsertUserASYNC extends AsyncTask<User, Integer, Boolean> {
            @Override
            protected Boolean doInBackground(User... params) {
                boolean resul = true;
                HttpClient httpClient = new DefaultHttpClient();
                String URL = "http://" + MainActivity.getDireccionIP() + ":8083/userServices/insertUserServices?username=";
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
                            "Se ha creado correctamente un usuario " + user.getUsername() + " para SmartTourism.", Toast.LENGTH_SHORT);
                    toast1.show();
                }else{
                    Toast toast1 = Toast.makeText(getApplicationContext(),
                            "No se ha podido crear un usuario " + user.getUsername() + " para SmartTourism.", Toast.LENGTH_SHORT);
                    toast1.show();
                }
                Intent i = new Intent(CrearPerfil.this,MainActivity.class);
                startActivity(i);
            }
        }
    }
