package com.example.kaarthiha.finaltreasurehunt;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Environment;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

public class youhunt extends AppCompatActivity {

    String name;
    String json_str,JSON_STRING="",ftrs;
    ContactAdapter2 contactAdapter2;
    JSONObject jsonObject;
    JSONArray jsonArray;

    ListView listView,list1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_youhunt);
        name=getIntent().getExtras().getString("name");

        listView=(ListView)findViewById(R.id.listview2);
        contactAdapter2=new ContactAdapter2(this,R.layout.row2_layout);

        listView.setAdapter(contactAdapter2);



        final SwipeRefreshLayout swipeRefreshLayout=(SwipeRefreshLayout)findViewById(R.id.swipe2);

        swipeRefreshLayout.setColorSchemeResources(R.color.refresh, R.color.refresh2, R.color.refresh3);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(true);
                (new Handler()).postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        swipeRefreshLayout.setRefreshing(false);

                        getdata();
                        parselist();


                    }
                }, 3000);

            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String cername = ((TextView) view.findViewById(R.id.tx_name)).getText().toString();
                Toast.makeText(getApplication(), cername, Toast.LENGTH_LONG).show();
                dhamo(cername);
                kedi(cername);


            }
        });


    }

    public void kedi(String eventname) {


        bgtsk bg = new bgtsk();
        bg.execute(eventname);

    }


    class bgtsk extends AsyncTask<String,Void,String>
    {
        String toturl;
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            String contra=s;
            Toast.makeText(getApplication(),s,Toast.LENGTH_LONG).show();
            Intent i = new Intent(youhunt.this,disp.class);
            i.putExtra("ans",contra);
            startActivity(i);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            toturl="http://thunt.96.lt/kaarthiha/total.php";
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }



        @Override
        protected String doInBackground(String... params) {

            String names = params[0];
            try {
                URL url = new URL(toturl);
                HttpURLConnection htpcon = (HttpURLConnection) url.openConnection();
                htpcon.setRequestMethod("POST");
                htpcon.setDoOutput(true);
                htpcon.setDoInput(true);
                OutputStream outputStream=htpcon.getOutputStream();
                BufferedWriter bufferedWriter=new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));
                String data_string = URLEncoder.encode("name", "UTF-8")+"="+URLEncoder.encode(names,"UTF-8");
                bufferedWriter.write(data_string);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();
                InputStream is = htpcon.getInputStream();
                BufferedReader bfr = new BufferedReader(new InputStreamReader(is,"iso-8859-1"));

                StringBuilder sbr = new StringBuilder();

                while ((ftrs = bfr.readLine()) != null) {
                    sbr.append(ftrs + "\n");
                }

                bfr.close();
                is.close();
                htpcon.disconnect();
                return sbr.toString().trim();



            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;



        }
    }

    public void dhamo(String cername) {
        Document doc = new Document();
        String dc="Treasure Hunt ";
        String cont = "This is to certify that Mr./Ms  "+name+"  has participated successfully in "+cername+" treasure hunt";
        String path = Environment.getExternalStorageDirectory()+"/certificate.pdf";
        try {
            PdfWriter.getInstance(doc, new FileOutputStream(path));
            doc.open();
            Paragraph preface = new Paragraph();
            preface.setAlignment(Element.ALIGN_CENTER);
            preface.add(dc);

            doc.add(preface);

            Paragraph conta = new Paragraph();
            conta.setAlignment(Element.ALIGN_LEFT);
            conta.add(cont);
            doc.add(conta);
            doc.close();
        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }




    void getdata()
    {
        background bg = new background();
        bg.execute(name);
    }

    class background extends AsyncTask<String,Void,String> {
        String returl;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            returl = "http://thunt.96.lt/kaarthiha/regicont.php";
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            JSON_STRING=s;
            Toast.makeText(getApplication(),JSON_STRING,Toast.LENGTH_LONG).show();
        }

        @Override
        protected String doInBackground(String... params) {

            String nm=params[0];
            try {
                URL url = new URL(returl);
                HttpURLConnection htpcon = (HttpURLConnection) url.openConnection();
                htpcon.setRequestMethod("POST");
                htpcon.setDoOutput(true);
                htpcon.setDoInput(true);
                OutputStream outputStream=htpcon.getOutputStream();
                BufferedWriter bufferedWriter=new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));
                String data_string = URLEncoder.encode("name", "UTF-8")+"="+URLEncoder.encode(nm,"UTF-8");
                bufferedWriter.write(data_string);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();
                InputStream is = htpcon.getInputStream();
                BufferedReader bfr = new BufferedReader(new InputStreamReader(is,"iso-8859-1"));

                StringBuilder sbr = new StringBuilder();

                while ((json_str = bfr.readLine()) != null) {
                    sbr.append(json_str + "\n");
                }

                bfr.close();
                is.close();
                htpcon.disconnect();
                return sbr.toString().trim();



            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    void parselist()
    {
        contactAdapter2.list.clear();


        try {
            jsonObject=new JSONObject(JSON_STRING);
            int count=0;
            jsonArray=jsonObject.getJSONArray("server_response");
            String name;
            while (count<jsonArray.length())
            {
                JSONObject jo = jsonArray.getJSONObject(count);
                name=jo.getString("name");
                Contacts2 contacts= new Contacts2(name);
                contactAdapter2.add(contacts);
                count++;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}
