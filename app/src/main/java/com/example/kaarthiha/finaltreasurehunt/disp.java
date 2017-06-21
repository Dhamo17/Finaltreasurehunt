package com.example.kaarthiha.finaltreasurehunt;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import static android.support.v4.app.ActivityCompat.startActivity;

public class disp extends AppCompatActivity {

    String listdata;
    JSONObject jsonObject;
    JSONArray jsonArray;
    ContactAdapter3 contactAdapter3;
    ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_disp);
        listdata=getIntent().getExtras().getString("ans");
        listView = (ListView)findViewById(R.id.listVie);
        contactAdapter3 = new ContactAdapter3(this,R.layout.row_layout3);
        listView.setAdapter(contactAdapter3);
        try {
            jsonObject = new JSONObject(listdata);
            jsonArray =jsonObject.getJSONArray("server_response");
            int count=0;
            String name,mobile;
            while (count<jsonArray.length())
            {
                JSONObject JO = jsonArray.getJSONObject(count);
                name=JO.getString("name");
                mobile=JO.getString("mobile");

                Contacts3 contacts3  = new Contacts3(name,mobile);

                contactAdapter3.add(contacts3);

                count++;
            }



        } catch (JSONException e) {
            e.printStackTrace();
        }

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String cername = ((TextView) view.findViewById(R.id.tx_mobile)).getText().toString();
                Toast.makeText(getApplication(), cername, Toast.LENGTH_LONG).show();

                Intent i = new Intent(Intent.ACTION_CALL);
                i.setData(Uri.parse("tel:"+cername));
                //noinspection ResourceType
                startActivity(i);


            }
        });

    }
}
