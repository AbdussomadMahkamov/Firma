package com.example.firma.Repository;

import com.example.firma.Entity.Firma;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FirmaRepository extends JpaRepository<Firma,Integer> {
    boolean existsByFirmaNomi(String firmaNomi);
//    boolean existsByDirektorNomi(String direktorNomi);
}
