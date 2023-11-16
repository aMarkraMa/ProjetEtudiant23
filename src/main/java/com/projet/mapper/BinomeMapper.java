package com.projet.mapper;

import com.projet.entity.Binome;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDate;
import java.util.List;

public interface BinomeMapper {
	
	List<Binome> selectAll();
	
	int insertBinomeStep1(Binome binome);
	int insertOrUpdateBinomeStep2(Binome binome);
	int insertOrUpdateBinomeStep3(Binome binome);
	
	int deleteBinome(@Param("idBinome") Integer idBinome, @Param("idProjet") Integer idProjet);
	int deleteAppartenir(@Param("idBinome") Integer idBinome, @Param("idProjet") Integer idProjet);
	
	int updateBinomeStep1(@Param("noteRapport") Double noteRapport, @Param("dateReelleRemise") LocalDate dateReelleRemise, @Param("idBinome") Integer idBinome, @Param("idProjet") Integer idProjet);
	
	List<Binome> selectByCondition(Binome binome);
	Binome selectByIdBinomeAndIdProjet(Binome binome);
	
	Binome getBinomeByIdProjetAndIdEtudiant(@Param("idEtudiant") Integer idEtudiant, @Param("idProjet") Integer idProjet);
	
	Integer getMaxIdBinome();
	
	Integer getNombreBinomeByIdProjet(@Param("idProjet") Integer idProjet);
	
	List<Binome> getBinomesByIdProjet(@Param("idProjet") Integer idProjet);
}
