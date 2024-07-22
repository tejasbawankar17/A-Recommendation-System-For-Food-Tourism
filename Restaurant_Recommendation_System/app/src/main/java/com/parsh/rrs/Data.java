package com.parsh.rrs;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Data extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();

        double latitude = extras.getDouble("latitude");
        double longitude = extras.getDouble("longitude");
        float radius = extras.getFloat("radius");
        String category = extras.getString("Category");

        TextView textView = findViewById(R.id.textView);
        Button button = findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String lat = Double.toString(latitude);
                String lon = Double.toString(longitude);
                String rad = Float.toString(radius);
                String cat = new String(category);
                String api = "http://10.0.2.2:5000/predict";

                StringRequest stringRequest = new StringRequest(Request.Method.POST, api, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            Log.d(TAG, "onResponse: " + response);
                            textView.setText(response);
                        } catch (Error e) {
                            Toast.makeText(Data.this, "Error in Printing", Toast.LENGTH_SHORT).show();
                            throw new RuntimeException(e);
                        }
                    }
                },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(Data.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }) {
                    @Override
                    protected Map<String, String> getParams() {
                        Map<String, String> params = new HashMap<>();

                        params.put("lat", lat);
                        params.put("lon", lon);
                        params.put("rad", rad);
                        params.put("cat", cat);
                        return params;
                    }
                };

                RequestQueue queue = Volley.newRequestQueue(Data.this);
                queue.add(stringRequest);
            }
        });
    }
}
