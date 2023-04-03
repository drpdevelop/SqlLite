package com.clase1.sqllite;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    public EditText cedula,name,numero, edt_consultar;
    public Button register, search;

    public TextView result_cedula, result_num;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        cedula = findViewById(R.id.cedula);
        name = findViewById(R.id.name);
        numero = findViewById(R.id.numero);
        register = findViewById(R.id.register);
        search = findViewById(R.id.consultar);
        edt_consultar = findViewById(R.id.edt_consultar);
        result_cedula = findViewById(R.id.result_cedula);
        result_num = findViewById(R.id.result_num);


        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                registrar(view);
            }
        });

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                consultar(view);
            }
        });

    }


    public void registrar(View view){

        AdminBd admin = new AdminBd(this, "BdUsers", null, 1);
        SQLiteDatabase sqLiteDatabase = admin.getWritableDatabase();
        String cedulaT = cedula.getText().toString();
        String nombreT = name.getText().toString();
        String numeroT = numero.getText().toString();

        if (!cedulaT.isEmpty() && !nombreT.isEmpty() && !numeroT.isEmpty()) {
            ContentValues registro = new ContentValues();
            registro.put("id_user", cedulaT);
            registro.put("name", nombreT);
            registro.put("tel", numeroT);

            sqLiteDatabase.insert("user", null, registro);
            sqLiteDatabase.close();
            cedula.setText("");
            name.setText("");
            numero.setText("");
            Toast.makeText(this, "Registro exitoso", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "Todos los campos son obligatorios.", Toast.LENGTH_LONG).show();
        }
    }


    public void consultar(View view){
        AdminBd admin = new AdminBd(this, "BdUsers", null, 1);
        SQLiteDatabase sqLiteDatabase = admin.getWritableDatabase();
        String cedulaSearch = edt_consultar.getText().toString();

        Cursor c = sqLiteDatabase.rawQuery("select id_user, name, tel from user where id_user = "+ cedulaSearch,null);

        if (c != null ) {
            if  (c.moveToFirst()) {
                do {
                    String name = !c.getString(1).isEmpty() ? c.getString(1) :  "Sin nombre.";
                    String num = !c.getString(2).isEmpty() ? c.getString(2) :  "Sin telefono.";
                    Log(name);
                    if(!name.isEmpty()){
                        result_cedula.setText(name);
                        result_num.setText(num);
                    }else{
                        Toast.makeText(this, "No se encontro el usuario.", Toast.LENGTH_LONG).show();
                    }
                    sqLiteDatabase.close();
                }while (c.moveToNext());
            }
        }
        
    }


    public static void Log(Object obj){
        System.out.println(obj);
    }



}