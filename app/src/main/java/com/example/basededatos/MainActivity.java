package com.example.basededatos;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private EditText et_codigo, et_descripcion, et_precio;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        et_codigo = (EditText) findViewById(R.id.txt_codigo);
        et_descripcion = (EditText) findViewById(R.id.txt_descripcion);
        et_precio = (EditText) findViewById(R.id.txt_precio);
    }

    public void registrar(View view) {
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "administracion", null, 1);
        SQLiteDatabase BaseDeDatos = admin.getWritableDatabase();
        String codigo = et_codigo.getText().toString();
        String descripcion = et_descripcion.getText().toString();
        String precio = et_precio.getText().toString();

        if (!codigo.isEmpty() && !descripcion.isEmpty() && !precio.isEmpty()) {
            ContentValues registro = new ContentValues();
            registro.put("codigo", codigo);
            registro.put("descripcion", descripcion);
            registro.put("precio", precio);

            BaseDeDatos.insert("articulos", null, registro);
            BaseDeDatos.close();
            et_codigo.setText("");
            et_precio.setText("");
            et_descripcion.setText("");

        } else {
            Toast.makeText(this, "debes llenar todos los espacios wn", Toast.LENGTH_SHORT).show();

        }
    }

    //método para consultar un artículo
    public void buscar(View view) {
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper
                (this, "administracion", null, 1);
        SQLiteDatabase BaseDeDatos = admin.getWritableDatabase();

        String codigo = et_codigo.getText().toString();

        if (!codigo.isEmpty()) {
            Cursor fila = BaseDeDatos.rawQuery
                    ("SELECT descripcion, precio FROM articulos WHERE codigo=" + codigo, null);

            if (fila.moveToFirst()) {
                et_descripcion.setText(fila.getString(0));
                et_precio.setText(fila.getString(1));
                BaseDeDatos.close();
            } else {
                Toast.makeText(this, "no existe el articulo", Toast.LENGTH_SHORT).show();
            }


        } else {
            Toast.makeText(this, "debes ingresar el codigo weon ql", Toast.LENGTH_SHORT).show();
        }

    }

    //método para eliminar
    public void eliminar(View view) {
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper
                (this, "administracion", null, 1);
        SQLiteDatabase BaseDeDatos = admin.getWritableDatabase();

        String codigo = et_codigo.getText().toString();

        if (!codigo.isEmpty()) {
            int cantidad = BaseDeDatos.delete("articulos", "codigo=" + codigo, null);
            BaseDeDatos.close();
            et_codigo.setText("");
            et_precio.setText("");
            et_descripcion.setText("");

            if (cantidad == 1) {
                Toast.makeText(this, "articulo eliminado exitosamente", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "la wea no existe", Toast.LENGTH_SHORT).show();
            }


        } else {
            Toast.makeText(this, "debes ingresar el codigo weon ql", Toast.LENGTH_SHORT).show();
        }

    }

    //Método para modificar
    public void modificar(View view) {
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper
                (this, "administracion", null, 1);
        SQLiteDatabase BaseDeDatos = admin.getWritableDatabase();
        String codigo = et_codigo.getText().toString();
        String descripcion = et_descripcion.getText().toString();
        String precio = et_precio.getText().toString();

        if (!codigo.isEmpty() && !descripcion.isEmpty() && !precio.isEmpty()) {
            ContentValues registro = new ContentValues();
            registro.put("codigo", codigo);
            registro.put("descripcion", descripcion);
            registro.put("precio", precio);

            int cantidad = BaseDeDatos.update("articulos", registro, "codigo", null);
            BaseDeDatos.close();
            et_codigo.setText("");
            et_precio.setText("");
            et_descripcion.setText("");

            if (cantidad == 1) {
                Toast.makeText(this, "articulo modificado exitosamente", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "la wea no existe", Toast.LENGTH_SHORT).show();
            }

        } else {
            Toast.makeText(this, "debes llenar todos los espacios wn", Toast.LENGTH_SHORT).show();


        }
    }
}