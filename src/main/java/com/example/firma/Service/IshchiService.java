package com.example.firma.Service;

import com.example.firma.Entity.Bolim;
import com.example.firma.Entity.Firma;
import com.example.firma.Entity.Ishchi;
import com.example.firma.Entity.Manzil;
import com.example.firma.PayLoad.ApiRespons;
import com.example.firma.PayLoad.IshchiDto;
import com.example.firma.Repository.BolimRepository;
import com.example.firma.Repository.IshchiRepository;
import com.example.firma.Repository.ManzilRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class IshchiService {
    @Autowired
    IshchiRepository ishchiRepository;
    @Autowired
    ManzilRepository manzilRepository;
    @Autowired
    BolimRepository bolimRepository;

    public ApiRespons addIshchi(IshchiDto ishchiDto) {
        boolean b = ishchiRepository.existsByTelRaqam(ishchiDto.getTelRaqam());
        if (b){
            return new ApiRespons("Bunday telefon raqamli ishchi oldindan ro'yxatdan o'tgan",false);
        }else {
            Optional<Bolim> byId = bolimRepository.findById(ishchiDto.getBolimId());
            if (!byId.isPresent()){
                return new ApiRespons("Bazda bunday idda bo'lim mavjud emas", false);
            }
            Ishchi ishchi=new Ishchi();
            ishchi.setIsmi(ishchiDto.getIsmi());
            ishchi.setFamilya(ishchiDto.getFamilya());
            ishchi.setTelRaqam(ishchiDto.getTelRaqam());
            Manzil manzil=new Manzil();
            manzil.setViloyat(ishchiDto.getViloyat());
            manzil.setTuman(ishchiDto.getTuman());
            manzil.setKocha(ishchiDto.getKocha());
            manzil.setUyraqami(ishchiDto.getUyraqami());
            ishchi.setManzil(manzil);
            ishchi.setBolim(byId.get());
            manzilRepository.save(manzil);
            ishchiRepository.save(ishchi);
            return new ApiRespons("Ma'lumotar bazaga saqlandi", true);
        }
    }

    public ApiRespons editIshchi(Integer id, IshchiDto ishchiDto) {
        Optional<Ishchi> byId = ishchiRepository.findById(id);
        if (!byId.isPresent()){
            return new ApiRespons("Bazada bunday idli malumot topilmadi", false);
        }
        Optional<Bolim> byId2 = bolimRepository.findById(ishchiDto.getBolimId());
        if (byId2.isPresent()){
            Ishchi ishchi=byId.get();
            Optional<Manzil> byId1 = manzilRepository.findById(ishchi.getManzil().getId());
            Manzil manzil=byId1.get();
            ishchi.setIsmi(ishchiDto.getIsmi());
            ishchi.setFamilya(ishchiDto.getFamilya());
            ishchi.setTelRaqam(ishchiDto.getTelRaqam());
            manzil.setViloyat(ishchiDto.getViloyat());
            manzil.setTuman(ishchiDto.getTuman());
            manzil.setKocha(ishchiDto.getKocha());
            manzil.setUyraqami(ishchiDto.getUyraqami());
            ishchi.setManzil(manzil);
            ishchi.setBolim(byId2.get());
            manzilRepository.save(manzil);
            ishchiRepository.save(ishchi);
            return new ApiRespons("Ma'lumotlar muvoffaqiyatli tahrirlandi", true);
        }
        return new ApiRespons("Bazada bunday bo'lim mavjud emas", false);
    }

    public ApiRespons readIshchi() {
        List<Ishchi> all = ishchiRepository.findAll();
        String ss="";
        for (Ishchi ishchi : all) {
            String[] matn=ishchi.toString().split(", ");
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

    public ApiRespons readIdIshchi(Integer id) {
        Optional<Ishchi> byId = ishchiRepository.findById(id);
        if (byId.isPresent()){
            List<Ishchi> list=ishchiRepository.findAll();
            for (Ishchi ishchi : list) {
                String[] matn=ishchi.toString().split(", ");
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
                if (ishchi.getId()==id){
                    return new ApiRespons(ss, true);
                }
            }
        }
        return new ApiRespons("Bazada bunday idda ma'lumot mavjud emas",false);
    }

    public ApiRespons delIshchi(Integer id) {
        Optional<Ishchi> byId = ishchiRepository.findById(id);
        if (!byId.isPresent()){
            return new ApiRespons("Bazada bunday id ma'lumot topilmadi", false);
        }
        Integer manzilid=byId.get().getManzil().getId();
        ishchiRepository.deleteById(id);
        manzilRepository.deleteById(manzilid);
        return new ApiRespons("Ma'lumotlar muvoffaqiyatli o'chirildi", true);
    }
}
