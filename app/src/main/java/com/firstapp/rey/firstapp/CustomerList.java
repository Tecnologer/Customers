package com.firstapp.rey.firstapp;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CustomerList extends AppCompatActivity {

    private ListView lv;
    private static final String TEXT1 = "text1";
    private static final String TEXT2 = "text2";
    private ImageButton floatButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_list);

        lv = (ListView) findViewById(R.id.lvCustomerList);

        floatButton = (ImageButton) findViewById(R.id.imageButton);
        floatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OpenCustomersNewView(v);
            }
        });

        getCustomerList();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.itemCustomers:
                OpenCustomersNewView(null);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void getCustomerList() {
        String[] fromMapKey = new String[] {TEXT1, TEXT2};
        int[] toLayoutId = new int[] {android.R.id.text1, android.R.id.text2};
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this,"MexicanFood", null, 1);
        SQLiteDatabase bd = admin.getWritableDatabase(); //Create and/or open a database that will be used for reading and writing.
        Cursor row = bd.rawQuery("select sCustomerName,sCustomerPhone  from Customers", null);

        List<Map<String,String>> customerList = new ArrayList<Map<String,String>>();

        if (row.moveToFirst()) {  //si ha devuelto 1 fila, vamos al primero (que es el unico)
            Map<String, String> customerItem = new HashMap<String, String>();
            customerItem.put(TEXT1,row.getString(row.getColumnIndex(row.getColumnName(0))));
            customerItem.put(TEXT2,row.getString(row.getColumnIndex(row.getColumnName(1))));
            customerList.add(Collections.unmodifiableMap(customerItem));

            while (row.moveToNext()){
                customerItem = new HashMap<String, String>();
                customerItem.put(TEXT1,row.getString(row.getColumnIndex(row.getColumnName(0))));
                customerItem.put(TEXT2,row.getString(row.getColumnIndex(row.getColumnName(1))));
                customerList.add(Collections.unmodifiableMap(customerItem));
            }

            ListAdapter listAdapter = new SimpleAdapter(this, customerList,
                    android.R.layout.simple_list_item_2,
                    fromMapKey, toLayoutId);;


            // This is the array adapter, it takes the context of the activity as a
            // first parameter, the type of list view as a second parameter and your
            // array as a third parameter.
//            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,customerList );

            lv.setAdapter(listAdapter);
        } else
            Toast.makeText(this, R.string.msgCustomer_NoRecordFound ,Toast.LENGTH_SHORT).show();

        bd.close();
    }

    public void OpenCustomersNewView(View v){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
