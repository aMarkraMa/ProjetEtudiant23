package com.projet.mapper;

import com.projet.entity.Etudiant;

import java.util.ArrayList;

public interface EtudiantMapper {

    int insertEtudiant(Etudiant etudiant);
    int deleteEtudiant(Etudiant etudiant);
    int updateEtudiant(Etudiant etudiant);
    ArrayList<Etudiant> selectEtudiant(Etudiant etudiant);

}
