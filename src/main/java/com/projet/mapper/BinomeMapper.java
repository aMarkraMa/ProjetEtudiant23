package com.projet.mapper;

import com.projet.entity.Binome;
import com.projet.entity.Etudiant;

import java.util.ArrayList;

public interface BinomeMapper {
    int insertBinome(Binome binome);
    int deleteBinome(Binome binome);
    int updateBinome(Binome binome);
    ArrayList<Binome> selectBinome(Binome binome);
}
