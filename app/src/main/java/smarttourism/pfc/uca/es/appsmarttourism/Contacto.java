package smarttourism.pfc.uca.es.appsmarttourism;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by AnaMari on 26/03/2016.
 */

/*-----------------------------------------------------------------------------------------------*/
/* Clase que crea la actividad Contacto. Esta actividad crea una pantalla para el apartado de    */
/* Ayuda, el cual permite enviar un correo electrónico a la cuenta de soporte técnico de la      */
/* aplicación.                                                                                   */
/*-----------------------------------------------------------------------------------------------*/

public class Contacto extends AppCompatActivity {
    TextView etxSubject;
    TextView etxMessage;
    String username = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contact);
        Bundle extras = getIntent().getExtras();
        String username = extras.getString("username");
        final TextView etxUsername = (TextView) findViewById(R.id.correousuario);
        etxUsername.setText(username);
        etxSubject = (TextView)findViewById(R.id.etxAsunto);
        etxMessage = (TextView)findViewById(R.id.etxMensaje);

        Button btnCancelar = (Button)findViewById(R.id.btnCancelarEmail);
        btnCancelar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                etxSubject.setText("");
                etxMessage.setText("");
            }
        });

        Button btnAceptar = (Button)findViewById(R.id.btnAceptarEmail);
        btnAceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                if(etxSubject.getText().toString().trim().equals("")) {
                    Toast.makeText(Contacto.this, "Introduzca el asunto del correo.", Toast.LENGTH_SHORT).show();
                }else{
                    if(etxMessage.getText().toString().trim().equals("")){
                        Toast.makeText(Contacto.this, "Introduzca el mensaje del correo.", Toast.LENGTH_SHORT).show();
                    }else{
                        enviarEmail();
                    }
                }
            }
        });
    }

    //Precondiciones: Ninguno.
    //Postcondiciones: Construye el mensaje y envía el correo electrónico.
    private void enviarEmail(){
        //Instanciamos un Intent del tipo ACTION_SEND
        Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);
        //Definimos la tipologia de datos del contenido dle Email en este caso text/html
        emailIntent.setType("text/html");
        // Indicamos con un Array de tipo String las direcciones de correo a las cuales
        //queremos enviar el texto
        String correoTo = getResources().getString(R.string.email);
        emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{correoTo});
        // Definimos un Asunto para el Email
        etxSubject = (TextView)findViewById(R.id.etxAsunto);
        String subject = etxSubject.getText().toString();
        emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, subject);
        // Obtenemos la referencia al texto y lo pasamos al Email Intent
        etxMessage = (TextView)findViewById(R.id.etxMensaje);
        String message = etxMessage.getText().toString();
        emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, message);
        try {
            //Enviamos el Correo iniciando una nueva Activity con el emailIntent.
            startActivity(Intent.createChooser(emailIntent, "Seleccione..."));
            //Llevar la pantalla al inicio
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(Contacto.this, "No hay ninguna aplicación de correo instalada.", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_help, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.accion_inicio:
                Intent i = new Intent(this, MainActivity.class);
                startActivityForResult(i, 0);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
