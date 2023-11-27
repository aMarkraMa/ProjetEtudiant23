package com.projet.mapper;

import com.projet.entity.Binome;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDate;
import java.util.List;

public interface BinomeMapper {
	
	
	
	int insertBinomeStep1(Binome binome);
	int insertBinomeStep2OrUpdateBinomeStep1(Binome binome);
	int insertBinomeStep3OrUpdateBinomeStep2(Binome binome);
	
	int insertOrUpdateBinomeStep4AndStep5(@Param("idProjet") Integer idProjet, @Param("idEtudiant") Integer idEtudiant, @Param("noteSoutenance") Double noteSoutenance);
	
	int insertNoteSoutenance(@Param("idProjet") Integer idProjet, @Param("idEtudiant") Integer idEtudiant, @Param("noteSoutenance") Double noteSoutenance);
	
	int deleteBinome(@Param("idBinome") Integer idBinome, @Param("idProjet") Integer idProjet);
	int deleteAppartenir(@Param("idBinome") Integer idBinome, @Param("idProjet") Integer idProjet);
	int deleteNotesSoutenance(@Param("idEtudiant") Integer idEtudiant, @Param("idProjet") Integer idProjet);
	int deleteNotesSoutenanceStep2(@Param("idEtudiant") Integer idEtudiant, @Param("idProjet") Integer idProjet);
	int updateBinomeStep3(@Param("noteRapport") Double noteRapport, @Param("dateReelleRemise") LocalDate dateReelleRemise, @Param("idBinome") Integer idBinome, @Param("idProjet") Integer idProjet);
	
	List<Binome> selectByCondition(Binome binome);
	Binome selectByIdBinomeAndIdProjet(Binome binome);
	
	List<Binome> selectByIdProjet(Binome binome);
	
	Binome getBinomeByIdProjetAndIdEtudiant(@Param("idEtudiant") Integer idEtudiant, @Param("idProjet") Integer idProjet);
	
	Integer getMaxIdBinome();
	
	Integer getNombreBinomeByIdProjet(@Param("idProjet") Integer idProjet);
	
	List<Binome> getBinomesByIdProjet(@Param("idProjet") Integer idProjet);
}
