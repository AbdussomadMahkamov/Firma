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
import java.util.Optional;

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

    public ApiRespons editBolim(Integer id, BolimDto bolimDto) {
        Optional<Bolim> byId = bolimRepository.findById(id);
        if (!byId.isPresent()){
            return new ApiRespons("Bazada bunday idli ma'lumot topilmadi", false);
        }
        Bolim bolim=byId.get();
        bolim.setNomi(bolimDto.getNomi());
        bolimRepository.save(bolim);
        return new ApiRespons("Bazadagi ma'lumot tahrirlandi", true);
    }

    public ApiRespons readIdBolim(Integer id) {
        List<Bolim> all = bolimRepository.findAll();
        for (Bolim bolim : all) {
            if (bolim.getId().equals(id)){
                String[] matn=bolim.toString().split(", ");
                String ss="";
                for (String s : matn) {
                    if (s.indexOf("(")>0){
                        s=s.substring(s.indexOf("(")+1);
                    }
                    if (s.indexOf(")")>0){
                        s=s.substring(0,s.indexOf(")"));
                    }
                    ss+=s+"\n";
                }
                if (bolim.getId()==id){
                    return new ApiRespons(ss, true);
                }
            }
        }
        return new ApiRespons("Bazda bunday idli ma'lumot topilmadi", false);
    }

    public ApiRespons readBolim() {
        List<Bolim> list=bolimRepository.findAll();
        String ss="";
        for (Bolim bolim : list) {
            String[] matn=bolim.toString().split(", ");
            for (String s : matn) {
                if (s.indexOf("(")>0){
                    s=s.substring(s.indexOf("(")+1);
                }
                if (s.indexOf(")")>0){
                    s=s.substring(0,s.indexOf(")"));
                }
                ss+=s+"\n";
            }
            ss+="\n";
        }
        return new ApiRespons(ss,true);
    }

    public ApiRespons delBolim(Integer id) {
        Optional<Bolim> byId = bolimRepository.findById(id);
        if (!byId.isPresent()){
            return new ApiRespons("Bazada bunday idli ma'lumot topiladi", false);
        }
        bolimRepository.deleteById(id);
        return new ApiRespons("Bazdan ma'lumotlar muvoffaqiyatli ochirildi", true);
    }
}
