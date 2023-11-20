package com.projet.controller;

import com.projet.entity.Binome;
import com.projet.entity.Etudiant;
import com.projet.entity.Note;
import com.projet.entity.Projet;
import com.projet.mapper.BinomeMapper;
import com.projet.mapper.EtudiantMapper;
import com.projet.mapper.NoteMapper;
import com.projet.mapper.ProjetMapper;
import com.projet.utils.MyBatisUtils;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import org.apache.ibatis.session.SqlSession;

import java.util.ArrayList;
import java.util.List;

public class AddNoteController {

    @FXML
    private ChoiceBox<String> projetChoiceBox;
    @FXML
    private ChoiceBox<Integer> binomeChoiceBox;
    @FXML
    private ChoiceBox<String> etudiantChoiceBox;
    @FXML
    private TextField noteSoutenance;
    @FXML
    private Button addNote;

    public void initialize(){

        noteSoutenance.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d+")) {
                noteSoutenance.setText(newValue.replaceAll("[^\\d]+", ""));
            }
        });



//        projetChoiceBox.getItems().clear();
//        binomeChoiceBox.getItems().clear();
//        etudiantChoiceBox.getItems().clear();
        SqlSession sqlSession = MyBatisUtils.getSqlSession();
        ProjetMapper projetMapper = sqlSession.getMapper(ProjetMapper.class);
        BinomeMapper binomeMapper = sqlSession.getMapper(BinomeMapper.class);
        EtudiantMapper etudiantMapper = sqlSession.getMapper(EtudiantMapper.class);

        List<Projet> projets = projetMapper.selectAll();
        List<String> infoProjets = new ArrayList<>();

        for (int i = 0; i < projets.size(); i++) {
            Projet infoProjet = projets.get(i);
            Integer idProjet = infoProjet.getIdProjet();
            String nomMatiere = infoProjet.getNomMatiere();
            String sujet = infoProjet.getSujet();
            infoProjets.add(idProjet + " " + nomMatiere + " " + sujet);
        }

        List<Binome> binomes = binomeMapper.selectAll();
        List<Integer> infoBinomes = new ArrayList<>();

        for (int i = 0; i < binomes.size(); i++) {
            Integer idBinome = binomes.get(i).getIdBinome();
            infoBinomes.add(idBinome);
        }


        List<Integer> idsEtudiant = etudiantMapper.getIdsEtudiant();
        List<String> nomsEtudiant = etudiantMapper.getNomsEtudiant();
        List<String> prenomsEtudiant = etudiantMapper.getPrenomsEtudiant();

        List<String> infoEtudiants = new ArrayList<>();
        infoEtudiants.add("");
        for (int i = 0; i < idsEtudiant.size(); i++) {
            infoEtudiants.add(idsEtudiant.get(i) + " " + nomsEtudiant.get(i) + " " + prenomsEtudiant.get(i));
        }

        projetChoiceBox.getItems().addAll(infoProjets);
        binomeChoiceBox.getItems().addAll(infoBinomes);
        etudiantChoiceBox.getItems().addAll(infoEtudiants);

        sqlSession.close();
    }


    public void addNote(){
        SqlSession sqlSession = MyBatisUtils.getSqlSession();
        NoteMapper noteMapper = sqlSession.getMapper(NoteMapper.class);

        binomeChoiceBox.getValue();

        Binome binome = new Binome();
        binome.setIdBinome(binomeChoiceBox.getValue());
        Projet projet = new Projet();
        Note note = new Note();
//        note.setBinome();
//        note.setEtudiant();
//        note.setNoteSoutenance();

        noteMapper.insertNote(note);
    }

    public void showErr(String msg){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error Dialog");
        alert.setHeaderText("Oups");
        alert.setContentText(msg);

        alert.showAndWait();
    }
}
