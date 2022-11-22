package com.example.firma.Controller;

import com.example.firma.PayLoad.ApiRespons;
import com.example.firma.PayLoad.BolimDto;
import com.example.firma.Service.BolimService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/Bolim")
public class BolimController {
    @Autowired
    BolimService bolimService;
    @PostMapping("/addBolim")
    public HttpEntity<?> BolimJoylash(@RequestBody BolimDto bolimDto){
        ApiRespons apiRespons=bolimService.addBolim(bolimDto);
        return ResponseEntity.status(apiRespons.isHolat() ? HttpStatus.OK : HttpStatus.NOT_FOUND).body(apiRespons.getXabar());
    }
}
