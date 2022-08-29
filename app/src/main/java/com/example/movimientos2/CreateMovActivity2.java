package com.example.movimientos2;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class CreateMovActivity2 extends AppCompatActivity implements View.OnClickListener {
    Button btn_addMov;
    EditText no, area, tiemposMuertos, fe, capacidadInstalada, cantidadFabricada, horaHombre, horaMaquina, capacidadReal;
    TextInputLayout til;
    AutoCompleteTextView actv;
    private FirebaseFirestore mFirestore;
    private int dia, mes, ano;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_mov2);

        this.setTitle("Registrar Movimiento");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mFirestore = FirebaseFirestore.getInstance();
        no = findViewById(R.id.pt_nome);
        area = findViewById(R.id.pt_Area);
        tiemposMuertos = findViewById(R.id.Tiempos_Muertos);
        capacidadInstalada = findViewById(R.id.capacidad_instalada);
        cantidadFabricada = findViewById(R.id.cantidad_fabricada);
        horaHombre = findViewById(R.id.hora_hombre);
        horaMaquina = findViewById(R.id.hora_maquina);
        capacidadReal = findViewById(R.id.cap_Real);
        fe = findViewById(R.id.dp_fecha);
        fe.setOnClickListener(this);
        til = findViewById(R.id.textInputHorario);
        actv = findViewById(R.id.autoCompleteHorario);
        btn_addMov = findViewById(R.id.btn_addMov);

        String[] horarios = new String[]{
                "06:00 AM - 02:00 PM",
                "02:00 PM - 10:00 PM",
                "10:00 PM - 06:00 AM",
                "OTRO"
        };

        ArrayAdapter<String> adapter =new ArrayAdapter<String>(
                this,
                com.google.android.material.R.layout.support_simple_spinner_dropdown_item,
                horarios
        );
        actv.setAdapter(adapter);

        btn_addMov.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String namePer = no.getText().toString().trim();
                String areaPer = area.getText().toString().trim();
                String tmPer = tiemposMuertos.getText().toString().trim();
                String ciPer = capacidadInstalada.getText().toString().trim();
                String cfPer = cantidadFabricada.getText().toString().trim();
                String hhPer = horaHombre.getText().toString().trim();
                String hmPer = horaMaquina.getText().toString().trim();
                String crPer = capacidadReal.getText().toString().trim();
                String fecPer = fe.getText().toString().trim();
                String autoHora = actv.getText().toString().trim();
                if(namePer.isEmpty() && areaPer.isEmpty() && tmPer.isEmpty()){
                    Toast.makeText(getApplicationContext(),"Campos Vacios", Toast.LENGTH_SHORT).show();

                }else{
                    postPer(namePer,areaPer,tmPer,fecPer,autoHora,ciPer, cfPer, hhPer, hmPer, crPer);
                }
            }
        });
    }

    private void postPer(String namePer, String areaPer, String tmPer, String fecPer, String autoHora, String ciPer, String cfPer, String hhPer, String hmPer, String crPer) {
        Map<String, Object> map =new HashMap<>();
        map.put("Nombre", namePer);
        map.put("Area", areaPer);
        map.put("TiemposMuertos", tmPer);
        map.put("Fecha", fecPer);
        map.put("Horario", autoHora);
        map.put("CapacidadInstalada", ciPer);
        map.put("CantidadFabricada", cfPer);
        map.put("HoraHombre", hhPer);
        map.put("horaMaquina", hmPer);
        map.put("CapacidadReal", crPer);
        mFirestore.collection("persona").add(map).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                Toast.makeText(getApplicationContext(),"Registro Exitoso", Toast.LENGTH_SHORT).show();
                finish();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(),"Registro Incorrecto", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return false;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onClick(View view) {
        if(view == fe){
            final Calendar ca = Calendar.getInstance();
            dia = ca.get(Calendar.DAY_OF_MONTH);
            mes = ca.get(Calendar.MONTH);
            ano = ca.get(Calendar.YEAR);

            DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                    fe.setText(day+"/"+(month+1)+"/"+year);
                }
            }
            ,ano,mes,dia);
            datePickerDialog.show();
        }
    }
}