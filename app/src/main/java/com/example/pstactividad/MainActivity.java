package com.example.pstactividad;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;
import com.example.pstactividad.AdminSQLiteOpenHelper;

public class MainActivity extends AppCompatActivity {
    EditText matricula, nombres, apellidos, fecha, idCarrera;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        matricula = (EditText) findViewById(R.id.editTextMatricula);
        nombres = (EditText) findViewById(R.id.editTextTextNombres);
        apellidos = (EditText) findViewById(R.id.editTextTextApellidos);
        fecha = (EditText) findViewById(R.id.editTextTextFecha);
        idCarrera = (EditText) findViewById(R.id.editTextTextIDcarrera);
    }

    public void registro(View view) {
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "administracion", null, 1);
        SQLiteDatabase bd = admin.getWritableDatabase();

        String matriculaText = matricula.getText().toString();
        String nombresText = nombres.getText().toString();
        String apellidosText = apellidos.getText().toString();
        String fechaText = fecha.getText().toString();
        String idCarreraText = idCarrera.getText().toString();

        if (!matriculaText.isEmpty() || !nombresText.isEmpty() || !apellidosText.isEmpty() || fechaText.isEmpty() || !idCarreraText.isEmpty()) {
            String tabla = "insert into citas (id_estudiante,nombres,apellidos,fecha,id_carrera)"+"values ("+matriculaText+","+nombresText+","+apellidosText+","+fechaText+","+idCarreraText+")";
            bd.execSQL(tabla);
            bd.close();
            matricula.setText("");
            nombres.setText("");
            apellidos.setText("");
            fecha.setText("");
            idCarrera.setText("");
            Toast.makeText(this, "Se cargaron los datos de la cita", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Por favor, llene todos los campos.", Toast.LENGTH_SHORT).show();
        }
    }

    public void consultaMatricula(View view) {
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "administracion", null, 1);
        SQLiteDatabase bd = admin.getReadableDatabase();
        String matriculaText = matricula.getText().toString();
        if (!matriculaText.isEmpty()) {
            Cursor fila = bd.rawQuery("select nombres, apellidos, fecha, id_carrera from citas where " + "id_estudiante=" + matriculaText, null);
            if (fila.moveToFirst()) {
                nombres.setText(fila.getString(0));
                apellidos.setText(fila.getString(1));
                fecha.setText(fila.getString(2));
                idCarrera.setText(fila.getString(3));
                Toast.makeText(this, "Consulta exitosa.", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "No existe un estudiante con dicha matrícula", Toast.LENGTH_SHORT).show();
            }
            bd.close();

        }else{
            Toast.makeText(this, "ingrese numero de matrícula", Toast.LENGTH_SHORT).show();
        }
    }
}