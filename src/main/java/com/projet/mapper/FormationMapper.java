package com.projet.mapper;

import com.projet.entity.Etudiant;
import com.projet.entity.Formation;

import java.util.ArrayList;

public interface FormationMapper {

    int insertFormation(Formation formation);
    int deleteFormation(Formation formation);
    int updateFormation(Formation formation);
    ArrayList<Formation> selectFormation(Formation formation);
}
