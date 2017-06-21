package com.example.kaarthiha.finaltreasurehunt;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;

public class otp extends AppCompatActivity {

    String mobile,pass1,pas2,op;
    EditText ed1,ed2;
    Button b1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);

        mobile=getIntent().getExtras().getString("num").toString();


        ed1=(EditText)findViewById(R.id.editText7);
        ed2=(EditText)findViewById(R.id.editText14);

        b1=(Button)findViewById(R.id.button6);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pass1=ed1.getText().toString();
                pas2=ed2.getText().toString();


                if(!pass1.equals("")&&!pas2.equals(""))
                {
                    if(pass1.equals(pas2))
                    {
                        backg bg = new backg();
                        System.out.println(mobile+" "+pass1);
                        bg.execute(mobile,pass1);
                    }
                    else
                    {
                        Toast.makeText(getApplication(),"password mismatch",Toast.LENGTH_LONG).show();
                    }
                }
                else
                {
                    Toast.makeText(getApplication(),"fill all details",Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    class backg extends AsyncTask<String,Void,String>
    {
        String pasurl;

        @Override
        protected void onPreExecute() {
           // super.onPreExecute();
            pasurl="http://thunt.96.lt/kaarthiha/updatepas.php";
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if(s.equals("success")) {
                super.onPostExecute(s);
                Toast.makeText(getApplication(), "password updated successfully", Toast.LENGTH_LONG).show();

            }

            Toast.makeText(getApplication(), "password updated successfully", Toast.LENGTH_LONG).show();
            System.out.println(s);
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }



        @Override
        protected String doInBackground(String... params) {

            String numb = params[0];
            String pas=params[1];



            try {
                URL url = new URL(pasurl);

                HttpURLConnection httpURLConnection=(HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream=httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter=new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));
                String data_string= URLEncoder.encode("passw", "UTF-8")+"="+URLEncoder.encode(pas,"UTF-8")+"&"+
                        URLEncoder.encode("numbe","UTF-8")+"="+URLEncoder.encode(numb,"UTF-8");

                bufferedWriter.write(data_string);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();
                InputStream ip=httpURLConnection.getInputStream();
                BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(ip,"iso-8859-1"));
                op=bufferedReader.readLine();

                bufferedReader.close();
                ip.close();

                httpURLConnection.disconnect();
                return op;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (ProtocolException e) {
                e.printStackTrace();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }


            return null;
        }
    }
}
