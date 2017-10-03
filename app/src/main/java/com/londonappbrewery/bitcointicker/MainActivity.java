/*
* Created by TEK RAJ CHHETRI on 10/3/2017.
* */
package com.londonappbrewery.bitcointicker;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Toast;


import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;


public class MainActivity extends AppCompatActivity {

    // Constants:
    // TODO: Create the base URL
    //https://apiv2.bitcoinaverage.com/indices/global/ticker/short?crypto=BTC&fiat=AUD
    private final String BASE_URL = "https://apiv2.bitcoinaverage.com/indices/global/ticker/short?crypto=BTC";

    // Member Variables:
    TextView mPriceTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mPriceTextView = (TextView) findViewById(R.id.priceLabel);
        Spinner spinner = (Spinner) findViewById(R.id.currency_spinner);

        // Create an ArrayAdapter using the String array and a spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.currency_array, R.layout.spinner_item);

        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);

        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);

        // TODO: Set an OnItemSelected listener on the spinner

        spinner.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.d("Bitcoin","Item Pos: "+parent.getItemAtPosition(position));
                RequestParams params = new RequestParams();
                params.put("fiat",parent.getItemAtPosition(position));

                letsDoSomeNetworking(params, (String)parent.getItemAtPosition(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

   //  TODO: complete the letsDoSomeNetworking() method
    private void letsDoSomeNetworking(final RequestParams params, final String baseObject) {

       // Log.d("pARAMS:",params.toString());
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(BASE_URL,params,  new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                // called when response HTTP status is "200 OK"
                BitCoinDataModel bitCoinDataModel = BitCoinDataModel.fromJSON(response , "BTC"+baseObject);
                //Log.d("resp",response.toString());
               // Log.d("AVG","avg:"+bitCoinDataModel.getAverage());
                updateAVG(bitCoinDataModel.getAverage());


            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable e, JSONObject response) {
             //   Log.d("BITCOIN","FAILURE");
               Toast.makeText(MainActivity.this, "Request Failed", Toast.LENGTH_SHORT).show();
            }
        });


    }
    private void updateAVG(double avg){

        mPriceTextView.setText(new Double(avg).toString());
    }


}
