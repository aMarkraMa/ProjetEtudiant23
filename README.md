# ProjetEtudiant23
Il s'agit du projet Java pour la session 2023-2024 du MIAGE M1. Ce projet est conçu pour que les enseignants puissent gérer les projets des étudiants, y compris les informations sur les étudiants, les groupes d'étudiants, les notes des projets, etc.

## Getting Started

### Prerequisites
- Java JDK 17
- JavaFX SDK
- Maven

### Installation


MacOS & Linux:

To configure the project:
```sh
git clone git@github.com:your_user_name/ProjetEtudiant23.git
cd ProjetEtudiant23.git
vim src/main/resources/jdbc.properties
```
replace 
```sh
jdbc.url=jdbc:sqlite:testDB.sqlite
```
with
```sh
jdbc.url=jdbc:sqlite:src/main/resources/testDB.sqlite
```

To run the project:
```sh
mvn compile
mvn javafx:run
```

To run the jar package:
```sh
cd ProjetEtudiant23/jar
java --module-path path-to-your-sdk/javafx-sdk-version/lib --add-modules javafx.controls,javafx.fxml -jar Projet-jar-with-dependencies.jar
```


## Function

1. Registration and login
2. Student Management
3. Project Management
4. Marks Management

## Authors
Shengqi MA
Yingxuan LI
