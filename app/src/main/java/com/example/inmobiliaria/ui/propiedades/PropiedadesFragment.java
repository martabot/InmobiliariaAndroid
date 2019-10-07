package com.example.inmobiliaria.ui.propiedades;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.inmobiliaria.MainActivity;
import com.example.inmobiliaria.Principal;
import com.example.inmobiliaria.R;
import com.example.inmobiliaria.model.Propiedad;

public class PropiedadesFragment extends Fragment {
    private EditText direccion,ambientes,precio;
    private Spinner propiedad,tipo,uso;
    private Switch disponible;
    private Button guardar;
    private int lastSelection;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_propiedades, container, false);
        ((Principal) getActivity()).setActionBarTitle("Propiedades");

        propiedad=root.findViewById(R.id.propiedad);
        direccion=root.findViewById(R.id.direccion);
        ambientes=root.findViewById(R.id.ambientes);
        precio=root.findViewById(R.id.precio);
        tipo=root.findViewById(R.id.tipo);
        uso=root.findViewById(R.id.uso);
        disponible=root.findViewById(R.id.disponible);

        guardar=root.findViewById(R.id.guardar);

        ArrayAdapter<CharSequence> adapter=ArrayAdapter.createFromResource(getContext(),R.array.direcciones,android.R.layout.simple_spinner_item);
        propiedad.setAdapter(adapter);
        propiedad.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                fijarDatos(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                propiedad.setSelection(0);
                fijarDatos(0);
            }
        });

        guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(getContext()).setTitle("").setMessage("Desea cambiar la disponibilidad?").setPositiveButton("SI", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        aceptar();
                    }
                }).setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Propiedad elegida=MainActivity.propiedades.get(lastSelection);
                        disponible.setChecked(elegida.isDisponible());
                    }
                }).show();
            }
        });

        return root;
    }

    public void fijarDatos(int position){
        Propiedad elegida=MainActivity.propiedades.get(position);
        lastSelection=position;

        direccion.setText(elegida.getDireccion());
        ambientes.setText(String.valueOf(elegida.getAmbientes()));
        precio.setText(String.valueOf(elegida.getPrecio()));

        ArrayAdapter<CharSequence> adapterTipo=ArrayAdapter.createFromResource(getContext(),R.array.tipos,android.R.layout.simple_spinner_item);
        tipo.setAdapter(adapterTipo);
        tipo.setSelection(adapterTipo.getPosition(elegida.getTipo()));
        tipo.setEnabled(false);

        ArrayAdapter<CharSequence> adapterUso=ArrayAdapter.createFromResource(getContext(),R.array.usos,android.R.layout.simple_spinner_item);
        uso.setAdapter(adapterUso);
        uso.setSelection(adapterUso.getPosition(elegida.getUso()));
        uso.setEnabled(false);

        disponible.setChecked(elegida.isDisponible());

    }

    public void aceptar(){
        Propiedad elegida=MainActivity.propiedades.get(lastSelection);
         elegida.setDisponible(disponible.isChecked());
    }
}