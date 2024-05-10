package com.example.demo.models;
import java.util.ArrayList;
import java.util.List;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;


@Data
public class Datas {
    private  ArrayList<Integer> list =new ArrayList<>(
            List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25,
                    26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50,
                    51, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61, 62, 63, 64, 65, 66, 67, 68, 69, 70, 19122, 76270, 76275,
                    76278, 76280, 76282, 76284, 76288, 76289, 76293, 76295, 76318, 76320, 76321, 76324, 76345, 76351,
                    76355, 76367, 76373, 76377, 76380, 76417, 76454, 76456, 76462, 76466, 76490, 76495, 76519, 76941,
                    76943, 76962, 3000014, 4000001,76373));
    private  String date;
    private  String waterLevel;
    private  String airTemperature;
    private  String pressure;
    private  String windspeed;
    private  String snow;
    private  String precipation;
    @NotNull(message = "Код поста обязателен!!")
    @NotBlank
    private  String Kod;

//    public void setList(ArrayList<Integer> list) {
//        this.list = list;
//    }
//
//
//    public static String getDate(){
//        return date;
//    }
//
//
//    public static String getKod() {
//        return Kod;
//    }
//
//    public static void setKod(String kod) {
//        Kod = kod;
//    }
//
//    public static String getPressure() {
//        return pressure;
//    }
//
//
//    public static ArrayList<Integer> getList() {
//        return list;
//    }
//
//
//    public static void setPressure(String pressure) {
//        Datas.pressure = pressure;
//    }
//
//    public static String getWindspeed() {
//        return windspeed;
//    }
//
//    public static void setWindspeed(String windspeed) {
//        Datas.windspeed = windspeed;
//    }
//
//    public static String getSnow() {
//        return snow;
//    }
//
//    public static void setSnow(String snow) {
//        Datas.snow = snow;
//    }
//
//    public static String getPrecipation() {
//        return precipation;
//    }
//
//    public static void setPrecipation(String precipation) {
//        Datas.precipation = precipation;
//    }
//
//    public static void setDate(String date) {
//        Datas.date = date;
//    }
//    public static void setWaterLevel(String waterLevel) {
//        Datas.waterLevel = waterLevel;
//    }
//    public static void setAirTemperature(String temperature) {
//        Datas.airTemperature = temperature;
//    }
//
//    public  static String  getWaterLevel() {
//        return waterLevel;
//    }
//
//    public String  getAirTemperature() {
//        return airTemperature;
//    }



}

