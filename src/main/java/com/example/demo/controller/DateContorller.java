package com.example.demo.controller;

import com.example.demo.models.Datas;
import com.example.demo.models.DatasDB;
import com.example.demo.repos.DatasDBRepository;
import com.example.demo.service.DatasService;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import javax.validation.Valid;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
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
    public String preshow(@Valid Datas datas, Errors errors, Model model ){
        if(errors.hasErrors()){
            return  "datas";
        }
        String waterLevel = Boolean.parseBoolean(datas.getWaterLevel()) ? "Используется" :"Не используется";
        String waterLevel1 = datas.getWaterLevel();
        String airTemperature = Boolean.parseBoolean(datas.getAirTemperature()) ? "Используется" :"Не используется";
        String airTemperature1 = datas.getAirTemperature();
        String winds = Boolean.parseBoolean(datas.getWindspeed()) ? "Используется" :"Не используется";
        String winds1 = datas.getWindspeed();
        String precipation = Boolean.parseBoolean(datas.getPrecipation()) ? "Используется" :"Не используется";
        String precipation1 = datas.getPrecipation();
        String snow = Boolean.parseBoolean(datas.getSnow()) ? "Используется" :"Не используется";
        String snow1 = datas.getSnow();
        String kod = datas.getKod();
        System.out.println(kod);
        if(datas.getList().contains(Integer.parseInt(kod))){
            ;
        }else {
            kod="Ошибка,такого кода нет";
            return "datas";
        }
        String date=datas.getDate().replace("-",".");
        List<DatasDB> all=datasService.getAll();
        for(int i=0;i<all.size();i++){
            DatasDB el = all.get(i);
            if(Objects.equals(el.getDate(), date)){
                System.out.println("Нейронка работает здесь");
//                rnn.predict(el.getKod_posta(),el.getKolichestvoosadkov())/ result=rnn
            }
        }
        String pressure = datas.getPressure();
        model.addAttribute("water",waterLevel);
        model.addAttribute("air",airTemperature);
        model.addAttribute("winds",winds);
        model.addAttribute("precipation",precipation);
        model.addAttribute("snow",snow);
        model.addAttribute("kod",kod);
        model.addAttribute("pressure",pressure);
        model.addAttribute("date",date);
        model.addAttribute("results","Нейронка отработала");
        return "results";
    }
}