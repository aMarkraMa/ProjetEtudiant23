package com.projet.mapper;

import com.projet.entity.Etudiant;

import java.util.List;

public interface EtudiantMapper {
    /*cherche tous les attributs*/
    List<Etudiant> selectAll();
    Etudiant selectById(int idEtudiant);
    List<Etudiant> selectByCondition(Etudiant etudiant);
}
