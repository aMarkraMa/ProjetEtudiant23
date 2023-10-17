package com.projet.utils;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;

public class MyBatisUtils {

    private static SqlSessionFactory sqlSessionFactory;

    static {
       try {
           InputStream is = Resources.getResourceAsStream("mybatis-config.xml");
           sqlSessionFactory = new SqlSessionFactoryBuilder().build(is);
       } catch (IOException e) {
           e.printStackTrace();
       }
    }

    public static SqlSession getSqlSession() {
        return sqlSessionFactory.openSession(true);
    }
}
