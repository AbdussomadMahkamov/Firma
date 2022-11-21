package com.example.firma.Controller;

import com.example.firma.PayLoad.ApiRespons;
import com.example.firma.PayLoad.FirmaDto;
import com.example.firma.Service.FirmaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/Firma")
public class FirmaController {
    @Autowired
    FirmaService firmaService;
    @PostMapping("/addFirma")
    public HttpEntity<?> FirmaQosh(@RequestBody FirmaDto firmaDto){
        ApiRespons apiRespons=firmaService.addFirma(firmaDto);
        return ResponseEntity.status(apiRespons.isHolat() ? HttpStatus.OK : HttpStatus.ALREADY_REPORTED).body(apiRespons.getXabar());
    }
    @PutMapping("/editFirma/{id}")
    public HttpEntity<?> FirmaTahrirlash(@PathVariable Integer id, @RequestBody FirmaDto firmaDto){
        ApiRespons apiRespons=firmaService.editFirma(id, firmaDto);
        return ResponseEntity.status(apiRespons.isHolat() ? HttpStatus.OK : HttpStatus.NOT_FOUND).body(apiRespons.getXabar());
    }
    @DeleteMapping("/deletFirma/{id}")
    public HttpEntity<?> FirmaOchirish(@PathVariable Integer id){
        ApiRespons apiRespons=firmaService.delFirma(id);
        return ResponseEntity.status(apiRespons.isHolat() ? HttpStatus.OK : HttpStatus.NOT_FOUND).body(apiRespons.getXabar());
    }
    @GetMapping("/readFirma")
    public HttpEntity<?> FirmaOqish(){
        ApiRespons apiRespons=firmaService.readFirma();
        return ResponseEntity.status(apiRespons.isHolat() ? HttpStatus.OK : HttpStatus.NOT_FOUND).body(apiRespons.getXabar());
    }
    @GetMapping("/readIdFirma/{id}")
    public HttpEntity<?> FirmaIdOqish(@PathVariable Integer id){
        ApiRespons apiRespons=firmaService.readIdFirma(id);
        return ResponseEntity.status(apiRespons.isHolat() ? HttpStatus.OK : HttpStatus.NOT_FOUND).body(apiRespons.getXabar());
    }
}
