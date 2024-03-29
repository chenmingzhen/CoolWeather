package com.example.coolweather.util;

import android.text.TextUtils;

import com.example.coolweather.db.City;
import com.example.coolweather.db.County;
import com.example.coolweather.db.Province;
import com.example.coolweather.gson.Weather;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

public class Utility {
    /* Parse and deal the province data from server returning*/
    public static boolean handleProvinceResponse(String response)
    {
        if(!TextUtils.isEmpty(response))
        {
            try{
                JSONArray allProvinces =new JSONArray(response);
                for(int i=0;i<allProvinces.length();i++)
                {
                    JSONObject provinceObject=allProvinces.getJSONObject(i);
                    Province province=new Province();
                    province.setProvinceName(provinceObject.getString("name"));
                    province.setProvinceCode(provinceObject.getInt("id"));
                    province.save();
                }
                 return true;
            }catch (Exception e)
            {
                e.printStackTrace();
            }
        }
        return false;
    }
    /*Parse and deal the city data from server returning*/
    public static boolean handleCityResponse(String response,int proviceId)
    {
        if(!TextUtils.isEmpty(response)){
            try
            {
                JSONArray allCities =new JSONArray(response);
                for(int i=0;i<allCities.length();i++)
                {
                    JSONObject cityObject =allCities.getJSONObject(i);
                    City city =new City();
                    city.setCityName(cityObject.getString("name"));
                    city.setCityCode(cityObject.getInt("id"));
                    city.setProvinceId(proviceId);
                    city.save();
                }
                return true;
            }catch (Exception e)
            {
                e.printStackTrace();
            }
        }
        return false;
    }
    /*Parse and deal the county data from server returning*/
    public static boolean handleCountyResponse(String response,int cityId)
    {
        if(!TextUtils.isEmpty(response)){
            try{
                 JSONArray allCounties =new JSONArray(response);
                 for(int i=0;i<allCounties.length();i++)
                 {
                     JSONObject countyObject=allCounties.getJSONObject(i);
                     County county =new County();
                     county.setCountyName(countyObject.getString("name"));
                     county.setWeatherId(countyObject.getString("weather_id"));
                     county.setCityId(cityId);
                     county.save();
                 }
                 return true;
            }catch (Exception e)
            {
                 e.printStackTrace();
            }
        }
        return false;
    }

    /*
    * 将返回的Json数据解析未weather实体类*/
    public static Weather handleWeatherResponse(String response)
    {
        try
        {
            JSONObject jsonObject=new JSONObject(response);
            JSONArray jsonArray=jsonObject.getJSONArray("HeWeather");
            String weatherContent=jsonArray.getJSONObject(0).toString();
            return  new Gson().fromJson(weatherContent,Weather.class);
        }catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }
}
