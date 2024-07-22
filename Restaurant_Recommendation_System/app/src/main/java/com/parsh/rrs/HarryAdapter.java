package com.parsh.rrs;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class HarryAdapter extends ArrayAdapter<String> {
private Context context;
private String [] arr;
private Integer [] images;
private  double latitude;
private  double longitude;
    public HarryAdapter(@NonNull Context context, int resource, @NonNull String[] arr,@NonNull Integer[] images, double latitude,double longitude) {
        super(context, resource, arr);
        this.arr=arr;
        this.images = images;
        this.context=context;
        this.latitude = latitude;
        this.longitude=longitude;
    }

    @Nullable
    @Override
    public String getItem(int position) {
        return arr[position];
    } // kausne position per kaunsa item hai wo batata hai getItem

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        convertView = LayoutInflater.from(getContext()).inflate(R.layout.my_harry_layout,parent,false);
       TextView t = convertView.findViewById(R.id.textView);
       ImageView im = convertView.findViewById(R.id.imageView);
       im.setImageResource(images[position]);
       t.setText(getItem(position));


      convertView.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
//              Toast.makeText(context, "You click on listview "+position,Toast.LENGTH_SHORT).show();
              Toast.makeText(context,"Latitude:"+latitude+" longitude"+longitude,Toast.LENGTH_SHORT).show();
              Intent intent = new Intent(context, Data.class);
              intent.putExtra("Category",getItem(position));
              intent.putExtra("latitude",latitude);
              intent.putExtra("longitude",longitude);
              intent.putExtra("radius",(float) 200.0);
              try{
                  context.startActivity(intent);
                  Toast.makeText(context,"Searching for result",Toast.LENGTH_SHORT).show();
              }
              catch (Exception e){
                  Toast.makeText(context,"e",Toast.LENGTH_SHORT).show();
              }
          }
      });
        return convertView;
    }


}
