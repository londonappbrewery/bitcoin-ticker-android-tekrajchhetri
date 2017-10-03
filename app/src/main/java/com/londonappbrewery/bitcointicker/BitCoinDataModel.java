package com.londonappbrewery.bitcointicker;

/**
 * Created by TEK RAJ CHHETRI on 10/3/2017.
 */
import android.icu.text.DecimalFormat;
import android.util.Log;

import org.json.JSONObject;

public class BitCoinDataModel {
    private double average;


    public static BitCoinDataModel fromJSON(JSONObject jsonObject, String params){
        BitCoinDataModel bitCoinDataModel = new BitCoinDataModel();
        try{
            bitCoinDataModel.average =  jsonObject.getJSONObject(params.toString()).getJSONObject("averages").getDouble("day");
        }catch (Exception ex){
            Log.d("ERROR",jsonObject.toString());

        }

        return bitCoinDataModel;
    }

    public double getAverage() {
       return  Math.round(average *100.0)/100.0;
    }
}
