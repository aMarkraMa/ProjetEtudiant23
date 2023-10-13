package com.projet.entity;

import com.projet.mapper.EtudiantMapper;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class MybatisDemo {
    public static void main(String[] args) throws IOException {
        //charger les fichiers de configuration xml, obtenir sqlSessionFactory
        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);

        //obtenir Objet de SqlSession pour excuter sql
        SqlSession sqlSession = sqlSessionFactory.openSession();

        //obtenir mapper objet de EtudiantMapper
        EtudiantMapper etudiantMapper = sqlSession.getMapper(EtudiantMapper.class);
        List<Etudiant> etudiants = etudiantMapper.selectAll();

        System.out.println("etudiant:");
        System.out.println(etudiants.toString());


        //librer les ressources
        sqlSession.close();
    }

}
