package com.example.kaarthiha.finaltreasurehunt;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dhamo on 22-03-2017.
 */
public class ContactAdapter2 extends ArrayAdapter {

    List list = new ArrayList();
    public ContactAdapter2(Context context, int resource) {
        super(context, resource);

    }


    public void add(Contacts2 object) {
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
        ContactHolder2 contactHolder;
        if(row==null)
        {
            LayoutInflater layoutInflater=(LayoutInflater)this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row=layoutInflater.inflate(R.layout.row2_layout,parent,false);
            contactHolder=new ContactHolder2();
            contactHolder.tx_name=(TextView) row.findViewById(R.id.tx_name);
            row.setTag(contactHolder);
        }
        else
        {
            contactHolder=(ContactHolder2)row.getTag();
        }
        Contacts2 contacts=(Contacts2)this.getItem(position);
        contactHolder.tx_name.setText(contacts.getName());



        return row;
    }






    static class ContactHolder2
    {
        TextView tx_name;

    }



}
