package com.projet.mapper;

import com.projet.entity.Binome;
import com.projet.entity.Etudiant;

import java.util.ArrayList;
import java.util.List;

public interface BinomeMapper {
    
    List<Binome> selectAll();
    int insertBinome(Binome binome);
    int deleteBinome(Binome binome);
    int updateBinome(Binome binome);
    ArrayList<Binome> selectBinome(Binome binome);
   Binome selectByIdBinomeAndIdProjet(Binome binome);
    
    void deleteById(Integer idBinome, Integer idProjet);
    
    List<Binome> selectByCondition(Binome binome);
}
