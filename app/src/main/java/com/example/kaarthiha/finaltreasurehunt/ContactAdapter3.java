package com.example.kaarthiha.finaltreasurehunt;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import static android.support.v4.app.ActivityCompat.startActivity;

/**
 * Created by Dhamo on 30-03-2017.
 */
public class ContactAdapter3 extends ArrayAdapter{
    List list = new ArrayList();
    public ContactAdapter3(Context context, int resource)
    {
        super(context, resource);
    }
    public void add(Contacts3 object) {
        super.add(object);
        list.add(object);
    }
    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View row;
        row=convertView;
        final ContactHolder2 contactHolder;
        if(row==null)
        {
            LayoutInflater layoutInflater=(LayoutInflater)this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row=layoutInflater.inflate(R.layout.row_layout3,parent,false);
            contactHolder=new ContactHolder2();
            contactHolder.tx_name=(TextView) row.findViewById(R.id.tx_namew);
            contactHolder.tx_mobile=(TextView) row.findViewById(R.id.tx_mobile);
            row.setTag(contactHolder);
        }
        else
        {
            contactHolder=(ContactHolder2)row.getTag();
        }
        Contacts3 contacts=(Contacts3)this.getItem(position);
        contactHolder.tx_name.setText(contacts.getName());
        contactHolder.tx_mobile.setText(contacts.getMobile());



        return row;
    }
    static class ContactHolder2
    {
        TextView tx_name,tx_mobile;

    }

}
