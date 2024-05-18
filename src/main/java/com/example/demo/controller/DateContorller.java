package com.example.demo.controller;
import com.example.demo.models.Datas;
import com.example.demo.models.DatasDB;
import com.example.demo.rnn.Rnn_class;
import com.example.demo.service.DatasService;
import com.example.demo.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import org.jfree.chart.ChartUtils;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.Period;
import java.util.*;

@Controller
public class DateContorller {
    @Autowired
    private DatasService datasService;
    @Autowired
    private UserService userService;


    @GetMapping(path = "/datas/{id}")
    public String showdata(@PathVariable(name="id") Long id, Model model){
        DatasDB d=datasService.get(id);
        model.addAttribute("datasDBS", d);
        return "showdata";
    }

    @GetMapping(path = "/preshows")
    public String showPage2(Model model){
        Datas d = new Datas();
        model.addAttribute("Datas", d);
        return "datas";
    }
    @PostMapping(path = "/eeer")
    public String preshow(@ModelAttribute @Valid Datas datas, HttpServletRequest request, Errors errors, Model model ) throws SQLException {
        if(errors.hasErrors()){
            return  "datas";
        }
        if (request.getParameter("waterLevel") != null) {
            datas.setWaterLevel("true");
        } else {
            datas.setWaterLevel("false");
        }
        if (request.getParameter("airTemperature") != null) {
            datas.setAirTemperature("true");
        } else {
            datas.setAirTemperature("false");
        }
        if (request.getParameter("pressure") != null) {
            datas.setPressure("true");
        } else {
            datas.setPressure("false");
        }
        if (request.getParameter("snow") != null) {
            datas.setSnow("true");
        } else {
            datas.setSnow("false");
        }
        if (request.getParameter("windspeed") != null) {
            datas.setWindspeed("true");
        } else {
            datas.setWindspeed("false");
        }
        if (request.getParameter("precipation") != null) {
            datas.setPrecipation("true");
        } else {
            datas.setPrecipation("false");
        }
        ArrayList<Integer> arrayList = new ArrayList<>();
        String waterLevel = Boolean.parseBoolean(datas.getWaterLevel()) ? "Используется" :"Не используется";
        String airTemperature = (datas.getAirTemperature()=="true") ? "Используется" :"Не используется";
        if(airTemperature.equals("Используется")){arrayList.add(1);}
        String winds = Boolean.parseBoolean(datas.getWindspeed()) ? "Используется" :"Не используется";
        if(winds.equals("Используется")){arrayList.add(2);}
        String precipation = Boolean.parseBoolean(datas.getPrecipation()) ? "Используется" :"Не используется";
        if(precipation.equals("Используется")){arrayList.add(3);}
        String pressure = Boolean.parseBoolean(datas.getPressure()) ? "Используется" :"Не используется";
        if(pressure.equals("Используется")){arrayList.add(4);}
        String snow = Boolean.parseBoolean(datas.getSnow()) ? "Используется" :"Не используется";
        if(snow.equals("Используется")){arrayList.add(5);}
        int kod = Integer.parseInt(datas.getKod());
        String dates=datas.getDate();
        String dates1=datas.getDate1();
        LocalDate date = LocalDate.parse(dates);
        LocalDate date1 = LocalDate.parse(dates1);
        Period diff = Period.between(date, date1);
        int[] stolb_num = arrayList.stream().mapToInt(Integer::intValue).toArray();
        int neiron = 7;
        int epoh = 2;
        double learnRate = 0.95;
        int input = 10;
        int size = stolb_num.length;
        long years = diff.getYears();
        long months = diff.getMonths();
        long days = diff.getDays();
        long totalDays = years * 365 + months * 30 + days+1;
        Rnn_class Rnn = new Rnn_class(neiron, size, input, (int) totalDays);
        MeteoDataProcessor Metos = new MeteoDataProcessor(datasService);
        double[][] inputs_do = Metos.processMeteoData(kod,date);
        double[][] inputs = new double[inputs_do.length][stolb_num.length];
        for (int i = 0; i < inputs_do.length; i++){
            for (int y = 0; y < stolb_num.length; y++){
                inputs[i][y] = inputs_do[i][stolb_num[y] - 1];
            }
        }
        double[] targets = Metos.targets;
        Metos.globalMaxMinElement(targets);
        double[][] detargets = Metos.convert(targets);
        inputs = Metos.MinMaxScaller(inputs);
        detargets = Metos.MinMaxScaller(detargets);
        for (int i = 0; i < detargets.length; i++){
            targets[i] = detargets[i][0];
        }
        Rnn.fit(inputs, targets, epoh, learnRate);
        double[] predict_result = Rnn.predict(inputs, (int) totalDays);
        double[] results=Metos.deNormal(predict_result);
        XYSeries series = new XYSeries("Уровень воды");
        for(int j = 0; j < results.length; j++) {
            series.add(j, results[j]); // 'j' - это день, 'results[j]' - уровень воды
        }
        XYDataset dataset = new XYSeriesCollection(series);
        JFreeChart chart = ChartFactory.createXYLineChart(
                "Уровень воды",
                "День из промежутка",
                "Уровень",
                dataset,
                PlotOrientation.VERTICAL,
                true,
                true,
                false
        );
        XYPlot plot = chart.getXYPlot();
        NumberAxis xAxis = (NumberAxis) plot.getDomainAxis();
        xAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
        XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
        renderer.setSeriesLinesVisible(0, true);
        renderer.setSeriesShapesVisible(0, true);
        renderer.setSeriesPaint(0, Color.YELLOW);
        double sizes = 5.0;
        double delta = sizes / 2.0;
        Shape dot = new Ellipse2D.Double(-delta, -delta, sizes, sizes);
        renderer.setSeriesShape(0, dot);
        plot.setRenderer(renderer);
        BufferedImage image = chart.createBufferedImage(400, 300);
        File outputFile = new File("C:\\Users\\kosta\\IdeaProjects\\demo\\src\\main\\resources\\static\\chart.png");
        try {
            ChartUtils.saveChartAsPNG(outputFile, chart, 400, 300);
        } catch (IOException e) {
            System.out.println("Произошла ошибка при сохранении графика: " + e.getMessage());
            e.printStackTrace();
        }
        model.addAttribute("water",waterLevel);
        model.addAttribute("air",airTemperature);
        model.addAttribute("winds",winds);
        model.addAttribute("precipation",precipation);
        model.addAttribute("snow",snow);
        model.addAttribute("kod",kod);
        model.addAttribute("pressure",pressure);
        model.addAttribute("date1",date1);
        model.addAttribute("results",results);
        model.addAttribute("image",image);
        return "results";
    }
}