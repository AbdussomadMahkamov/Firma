package com.example.firma.Service;

import com.example.firma.Entity.Bolim;
import com.example.firma.Entity.Firma;
import com.example.firma.PayLoad.ApiRespons;
import com.example.firma.PayLoad.BolimDto;
import com.example.firma.Repository.BolimRepository;
import com.example.firma.Repository.FirmaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BolimService {
    @Autowired
    BolimRepository bolimRepository;
    @Autowired
    FirmaRepository firmaRepository;

    public ApiRespons addBolim(BolimDto bolimDto) {
        boolean b = firmaRepository.existsByFirmaNomi(bolimDto.getFirmaNomi());
        if (b){
            List<Firma> list=firmaRepository.findAll();
            for (Firma firma : list) {
                boolean b1 = bolimRepository.existsByNomiAndFirma_Id(bolimDto.getNomi(), firma.getId());
                if (!b1){
                    if (firma.getFirmaNomi().equals(bolimDto.getFirmaNomi())){
                        Bolim bolim=new Bolim();
                        bolim.setNomi(bolimDto.getNomi());
                        bolim.setFirma(firma);
                        bolimRepository.save(bolim);
                        return new ApiRespons("Ma'lumotlar muvoffaqiyatli bazaga saqlani", true);
                    }
                }
            }
        }
        return new ApiRespons("Bazadan firma nomi topilmadi", false);
    }
}
