package com.firstapp.rey.firstapp;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private EditText customerName;
    private EditText customerPhone;
    private Button btnCustomerSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        customerName = (EditText) findViewById(R.id.txtCustomerName);
        customerPhone = (EditText) findViewById(R.id.txtCustomerPhone);
        btnCustomerSave = (Button) findViewById(R.id.BtnSaveCustomer);

    }

    public void SaveCustomer(View v){
        String customerName = this.customerName.getText().toString();
        String customerPhone = this.customerPhone.getText().toString();

        if(validateFields(customerName, customerPhone)) {
            try {
                AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "MexicanFood", null, 1);
                SQLiteDatabase bd = admin.getWritableDatabase();
                ContentValues registro = new ContentValues();  //es una clase para guardar datos
                registro.put("sCustomerName", customerName);
                registro.put("sCustomerPhone", customerPhone);
                bd.insert("Customers", null, registro);
                bd.close();
                this.customerName.setText("");
                this.customerPhone.setText("");
                this.customerName.requestFocus();
                Toast.makeText(this, R.string.msgCustomerSaved, Toast.LENGTH_SHORT).show();
            }
            catch (Exception e){
                Toast.makeText(this, e.getMessage() ,Toast.LENGTH_SHORT).show();
            }
        }
    }

    public boolean existsCustomer_ByPhone(String customerPhone) {
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this,"MexicanFood", null, 1);
        SQLiteDatabase bd = admin.getWritableDatabase(); //Create and/or open a database that will be used for reading and writing.
        Cursor fila = bd.rawQuery(  //devuelve 0 o 1 fila //es una consulta
                "select sCustomerName  from Customers where sCustomerPhone=\"" + customerPhone+"\"", null);


        if (fila.getCount()>0) {  //si ha devuelto 1 fila, vamos al primero (que es el unico)
            bd.close();
            return true;
        } else {
            bd.close();
            return false;
        }


    }

    private boolean validateFields(String cn, String cp){
        boolean isOk = true;
        if(cn.isEmpty()){
            Toast.makeText(this, R.string.msgCustomerName_Mandatory ,Toast.LENGTH_SHORT).show();
            this.customerName.requestFocus();
            isOk = false;
        }
        else if(cp.isEmpty()){
            Toast.makeText(this, R.string.msgCustomerPhone_Mandatory ,Toast.LENGTH_SHORT).show();
            this.customerPhone.requestFocus();
            isOk = false;
        }
        else if(existsCustomer_ByPhone(cp)){
            Toast.makeText(this, R.string.msgCustomerPhone_Duplicated ,Toast.LENGTH_SHORT).show();
            this.customerPhone.requestFocus();
            isOk = false;
        }

        return isOk;
    }

    public void OpenCustomersView(View v){
        Intent intent = new Intent(this, CustomerList.class);
        startActivity(intent);
    }
}
