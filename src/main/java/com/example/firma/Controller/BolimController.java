package com.example.firma.Controller;

import com.example.firma.PayLoad.ApiRespons;
import com.example.firma.PayLoad.BolimDto;
import com.example.firma.Service.BolimService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    @PutMapping("/editBolim/{id}")
    public HttpEntity<?> BolimTahrirlash(@PathVariable Integer id, @RequestBody BolimDto bolimDto){
        ApiRespons apiRespons=bolimService.editBolim(id, bolimDto);
        return ResponseEntity.status(apiRespons.isHolat() ? HttpStatus.OK : HttpStatus.NOT_FOUND).body(apiRespons.getXabar());
    }
    @GetMapping("/readBolim/{id}")
    public HttpEntity<?> BolimOqishId(@PathVariable Integer id){
        ApiRespons apiRespons=bolimService.readIdBolim(id);
        return ResponseEntity.status(apiRespons.isHolat() ? HttpStatus.OK : HttpStatus.NOT_FOUND).body(apiRespons.getXabar());
    }
    @GetMapping("/readBolim")
    public HttpEntity<?> BolimOqish(){
        ApiRespons apiRespons=bolimService.readBolim();
        return ResponseEntity.status(apiRespons.isHolat() ? HttpStatus.OK : HttpStatus.NOT_FOUND).body(apiRespons.getXabar());
    }
    @DeleteMapping("/deletBolim/{id}")
    public HttpEntity<?> BolimOchirish(@PathVariable Integer id){
        ApiRespons apiRespons=bolimService.delBolim(id);
        return ResponseEntity.status(apiRespons.isHolat() ? HttpStatus.OK : HttpStatus.NOT_FOUND).body(apiRespons.getXabar());
    }
}
