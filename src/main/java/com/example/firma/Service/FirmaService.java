package com.example.firma.Service;

import com.example.firma.Entity.Firma;
import com.example.firma.Entity.Manzil;
import com.example.firma.PayLoad.ApiRespons;
import com.example.firma.PayLoad.FirmaDto;
import com.example.firma.Repository.FirmaRepository;
import com.example.firma.Repository.ManzilRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FirmaService {
    @Autowired
    FirmaRepository firmaRepository;
    @Autowired
    ManzilRepository manzilRepository;

    public ApiRespons addFirma(FirmaDto firmaDto) {
        boolean b = firmaRepository.existsByFirmaNomi(firmaDto.getFirmaNomi());
        if (!b){
            Firma firma=new Firma();
            firma.setFirmaNomi(firmaDto.getFirmaNomi());
            firma.setDirektorNomi(firmaDto.getDirektorNomi());
            Manzil manzil=new Manzil();
            manzil.setViloyat(firmaDto.getViloyat());
            manzil.setTuman(firmaDto.getTuman());
            manzil.setKocha(firmaDto.getKocha());
            manzil.setUyraqami(firmaDto.getUyraqami());
            manzilRepository.save(manzil);
            firma.setManzil(manzil);
            firmaRepository.save(firma);
            return new ApiRespons("Ma'lumotlar saqlandi", true);
        }

        return new ApiRespons("Bunday firma bazada mavjud", false);
    }

    public ApiRespons editFirma(Integer id, FirmaDto firmaDto) {
        Optional<Firma> byId = firmaRepository.findById(id);
        if (!byId.isPresent()){
            return new ApiRespons("Bazada bunday idli ma'lumot topilmadi", false);
        }
        Firma firma=byId.get();
        firma.setFirmaNomi(firmaDto.getFirmaNomi());
        firma.setDirektorNomi(firmaDto.getDirektorNomi());
        Optional<Manzil> byId1 = manzilRepository.findById(firma.getManzil().getId());
        Manzil manzil=byId1.get();
        manzil.setViloyat(firmaDto.getViloyat());
        manzil.setTuman(firmaDto.getTuman());
        manzil.setKocha(firmaDto.getKocha());
        manzil.setUyraqami(firmaDto.getUyraqami());
        manzilRepository.save(manzil);
        firma.setManzil(manzil);
        firmaRepository.save(firma);
        return new ApiRespons("Ma'lumotlar muvoffaqiyatli tahrirlandi", true);
    }

    public ApiRespons delFirma(Integer id) {
        Optional<Firma> byId = firmaRepository.findById(id);
        if (!byId.isPresent()){
            return new ApiRespons("Bazada bunday id ma'lumot topilmadi", false);
        }
        Integer manzilid=byId.get().getManzil().getId();
        firmaRepository.deleteById(id);
        manzilRepository.deleteById(manzilid);
        return new ApiRespons("Ma'lumotlar muvoffaqiyatli o'chirildi", true);
    }

    public ApiRespons readFirma() {
        List<Firma> list=firmaRepository.findAll();
        String ss="";
        for (Firma firma : list) {
            String[] matn=firma.toString().split(", ");
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

    public ApiRespons readIdFirma(Integer id) {
        Optional<Firma> byId = firmaRepository.findById(id);
        if (byId.isPresent()){
            List<Firma> list=firmaRepository.findAll();
            for (Firma firma : list) {
                String[] matn=firma.toString().split(", ");
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
                if (firma.getId()==id){
                    return new ApiRespons(ss, true);
                }
            }
        }

        return new ApiRespons("Bazada bunday idli ma'lumot topilmdai",false);
    }
}
