package smarttourism.pfc.uca.es.appsmarttourism;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by AnaMari on 03/02/2016.
 */

/*-----------------------------------------------------------------------------------------------*/
/* Clase que crea la actividad AdaptadorRestaurantes, la cual es un adaptador. Un adaptador es un*/
/* objeto que comunica a un ListView los datos necesarios para crear las filas de la lista, asi  */
/* como genera los Views para cada elemento de la lista. Este adaptador crea la vista de         */
/* "Mi cuenta".                                                                                  */
/*-----------------------------------------------------------------------------------------------*/

public class AdaptadorRestaurantes extends ArrayAdapter<Restaurant>{

    //Precondiciones: Recibe el contexto y la lista de items a imprimir en este apartado.
    //Postcondiciones: Crea el adaptador.
    public AdaptadorRestaurantes(Context context, List<Restaurant> restaurantList) {
        super(context, R.layout.restaurante_item, restaurantList);
    }

    //Precondiciones: Recibe la posicion del item.
    //Postcondiciones: Crea la vista del adaptador.
    public View getView(int posicion, View convertView, ViewGroup parent) {
        Restaurant restaurant = getItem(posicion);
        convertView = LayoutInflater.from(getContext()).inflate(R.layout.restaurante_item, null);

        TextView lblNombre = (TextView) convertView.findViewById(R.id.nombre);
        lblNombre.setText(restaurant.getName());

        return convertView;
    }
}

