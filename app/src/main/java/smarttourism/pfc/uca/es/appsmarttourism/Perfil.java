package smarttourism.pfc.uca.es.appsmarttourism;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.text.DateFormatSymbols;

import org.w3c.dom.Text;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


/**
 * Created by AnaMari on 16/03/2016.
 */
public class Perfil extends AppCompatActivity {
    User user = new User();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_perfil);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Bundle extras = getIntent().getExtras();
        user.setUsername(extras.getString("username"));
        user.setName(extras.getString("name"));
        user.setSurname(extras.getString("surname"));
        user.setPassword(extras.getString("password"));
        user.setSeed(extras.getString("seed"));
        user.setAge(extras.getInt("age"));

        //String stringiso = convertUTF8ToISO(extras.getString("countrySource"));
        //user.setCountrySource(stringiso);

        user.setCountrySource(extras.getString("countrySource"));
        String dateInString = extras.get("dateBorn").toString();
        Date date = convertStringToDate(dateInString);
        user.setDateBorn(date);
        actualizarVista();
    }

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

    public void actualizarVista(){
        TextView nombre = (TextView) findViewById(R.id.txtNombre);
        nombre.setText(user.getName());
        TextView apellidos = (TextView) findViewById(R.id.txtApellidos);
        apellidos.setText(user.getSurname());
        TextView edad = (TextView) findViewById(R.id.txtEdad);
        edad.setText(Integer.toString(user.getAge()));

        TextView paisOrigen= (TextView) findViewById(R.id.txtPaisOrigen);
        paisOrigen.setText(user.getCountrySource());

        String dateFormateada = formatearFecha(user.getDateBorn().toString());
        TextView fechaNacimiento = (TextView) findViewById(R.id.txtFechaNacimiento);
        fechaNacimiento.setText(dateFormateada);
    }


    private String formatearFecha(String fecha){
        String[] fechaArray = fecha.split(" ");
        int mes = obtenerMes(fechaArray[1])+1;
        String mesNumero = String.format("%02d", mes) ;
        return fechaArray[2]+"/"+mesNumero+"/"+fechaArray[5];
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_user, menu);
        return true;  /** true -> el men� ya est� visible */
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.accion_inicio:
                Intent i = new Intent(this,MainActivity.class);
                startActivityForResult(i,0);
                return true;
            case R.id.accion_editar:
                i = new Intent(this,UpdatePerfil.class);
                i.putExtra("username",user.getUsername());
                i.putExtra("name", user.getName());
                i.putExtra("surname", user.getSurname());
                i.putExtra("password", user.getPassword());
                i.putExtra("seed", user.getSeed());
                i.putExtra("age", user.getAge());
                i.putExtra("dateBorn", user.getDateBorn());
                i.putExtra("countrySource", user.getCountrySource());
                startActivity(i);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}