package com.example.demo.controller;

import com.example.demo.models.DatasDB;
import com.example.demo.service.DatasService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Controller
public class MeteoDataProcessor {

    public double[] targets;
    double Max;
    double Min;
    private final DatasService datasService;
    @Autowired
    public MeteoDataProcessor(DatasService datasService) {
        this.datasService = datasService;
    }

    public double[][] processMeteoData(int postCode, LocalDate date){
        List<DatasDB> all=datasService.getAll();
        List<double[]> dataList = new ArrayList<>();
        List<Double> targetList = new ArrayList<>();
        for(int i=0;i<all.size();i++){
            DatasDB el = all.get(i);
            double[] data = new double[5];
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            LocalDate date1=LocalDate.parse(el.getDate().replace(".","-"), formatter);
            if(Objects.equals(el.getKod_posta(), String.valueOf(Double.parseDouble(String.valueOf(postCode))))
            && date.isBefore(date1)){
                try{
                data[0]=Double.parseDouble(el.getTemperature());
                data[1]=Double.parseDouble(el.getWindspeed());
                data[2]=Double.parseDouble(el.getKolichestvoosadkov());
                data[3]=Double.parseDouble(el.getPressure());
                data[4]=Double.parseDouble(el.getSnowlevel());
                double target = Double.parseDouble(el.getWaterlevel());
                targetList.add(target);
                dataList.add(data);
                }
                catch (Exception e){
                    break;

                }

            }
        }

            double[][] inputs = new double[dataList.size()][4];
            for (int i = 0; i < dataList.size(); i++) {
                inputs[i] = dataList.get(i);
            }

            double[] targetArray = new double[targetList.size()];
            for (int i = 0; i < targetList.size(); i++) {
                targetArray[i] = targetList.get(i);
            }
            this.targets = targetArray;
            return inputs;

    }
    public double MinElement(double[] arr) {
        double min = arr[0];
        for (int i = 1; i < arr.length; i++) {
            if (arr[i] < min) {
                min = arr[i];
            }
        }
        return min;
    }
    public double MaxElement(double[] arr) {
        double max = arr[0];
        for (int i = 1; i < arr.length; i++) {
            if (arr[i] > max) {
                max = arr[i];
            }
        }
        return max;
    }
    public double[][] MinMaxScaller(double[][] arr) {
        for (int y = 0; y < arr[0].length; y++) {
            double[] column = new double[arr.length];
            for (int k = 0; k < arr.length; k++) {
                column[k] = arr[k][y];
            }
            double max = this.MaxElement(column);
            double min = this.MinElement(column);
            for (int i = 0; i < arr.length; i++) {
                arr[i][y] = (arr[i][y] - min) / (max - min);
            }
        }
        return arr;
    }
    public void globalMaxMinElement(double[] arr) {
        this.Max = this.MaxElement(arr);
        this.Min = this.MinElement(arr);
    }
    public double[][] convert(double[] arr){
        double[][] twoDimArray = new double[arr.length][1];

        for (int i = 0; i < arr.length; i++) {
            twoDimArray[i][0] = arr[i];
        }
        return twoDimArray;
    }
    public double[] deNormal(double[] mean){
        double[] result = new double[mean.length];
        for (int i = 0; i < mean.length; i++){
            result[i] = mean[i] * (Max - Min) + Min;
        }
        return result;
    }
    public static double[][] get_last(double[][] inputs, int delema){
        System.out.println(delema);
        double[][] result = new double[delema][inputs[0].length];
        for (int i = 0; i < delema; i++){
            for (int y = 0; y < inputs[0].length; y++) {
                result[i][y] = inputs[inputs.length - delema + i][y];
            }
        }
        return result;
    }

}

