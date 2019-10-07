package com.example.inmobiliaria.ui.contratos;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.inmobiliaria.MainActivity;
import com.example.inmobiliaria.Principal;
import com.example.inmobiliaria.R;
import com.example.inmobiliaria.model.Contrato;

import java.util.ArrayList;

public class ContratosFragment extends Fragment {
    private ArrayList<Contrato> myList;
    private Spinner casas;
    private ListView list;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_contratos, container, false);
        ((Principal) getActivity()).setActionBarTitle("Contratos");

        casas=root.findViewById(R.id.places);
        list=root.findViewById(R.id.otraLista);

        ArrayAdapter<CharSequence> adapter=ArrayAdapter.createFromResource(getContext(),R.array.direcciones,android.R.layout.simple_spinner_item);
        casas.setAdapter(adapter);
        casas.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                cargarContratos(position+1);
                generarListView(list);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                casas.setSelection(0);
                cargarContratos(1);
                generarListView(list);
            }
        });

        return root;
    }

    public void cargarContratos(int casa){
        myList=new ArrayList<>();

        for (Contrato c: MainActivity.contratos) {
            if(c.getPropiedad().getId()==casa) {
                myList.add(c);
            }
        }
    }

    public void generarListView(ListView root){
        ArrayAdapter<Contrato> adapter=new ContratosAdapter(getContext(),R.layout.contratos,myList,getLayoutInflater());
        root.setAdapter(adapter);
    }
}