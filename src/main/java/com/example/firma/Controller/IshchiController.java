package com.example.firma.Controller;

import com.example.firma.PayLoad.ApiRespons;
import com.example.firma.PayLoad.IshchiDto;
import com.example.firma.Service.IshchiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/Ishchi")
public class IshchiController {
    @Autowired
    IshchiService ishchiService;
    @PostMapping("/IshchiQoshish")
    public HttpEntity<?> IshchiQoshish(@RequestBody IshchiDto ishchiDto){
        ApiRespons apiRespons=ishchiService.addIshchi(ishchiDto);
        return ResponseEntity.status(apiRespons.isHolat() ? HttpStatus.OK : HttpStatus.ALREADY_REPORTED).body(apiRespons.getXabar());
    }
    @PutMapping("/editIshchi/{id}")
    public HttpEntity<?> IshchiTahrirlash(@PathVariable Integer id, @RequestBody IshchiDto ishchiDto){
        ApiRespons apiRespons=ishchiService.editIshchi(id, ishchiDto);
        return ResponseEntity.status(apiRespons.isHolat() ? HttpStatus.OK : HttpStatus.NOT_FOUND).body(apiRespons.getXabar());
    }
    @GetMapping("/readIshchi")
    public HttpEntity<?> IshchiOqish(){
        ApiRespons apiRespons=ishchiService.readIshchi();
        return ResponseEntity.status(apiRespons.isHolat() ? HttpStatus.OK : HttpStatus.NOT_FOUND).body(apiRespons.getXabar());
    }
    @GetMapping("/readIdIshchi/{id}")
    public HttpEntity<?> IshchiIdOqish(@PathVariable Integer id){
        ApiRespons apiRespons=ishchiService.readIdIshchi(id);
        return ResponseEntity.status(apiRespons.isHolat() ? HttpStatus.OK : HttpStatus.NOT_FOUND).body(apiRespons.getXabar());
    }
    @DeleteMapping("/deletIshchi/{id}")
    public HttpEntity<?> IshchiOchirish(@PathVariable Integer id){
        ApiRespons apiRespons=ishchiService.delIshchi(id);
        return ResponseEntity.status(apiRespons.isHolat() ? HttpStatus.OK : HttpStatus.NOT_FOUND).body(apiRespons.getXabar());
    }
}
