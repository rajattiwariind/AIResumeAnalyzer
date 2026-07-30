package com.example.airesumeanalyzer.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;


    @Dao
    public interface ResumeDao {



        @Insert

        void insert(

                ResumeEntity entity);



        @Query(

                "SELECT * FROM history ORDER BY timestamp DESC")
        List<ResumeEntity> getAll();



    }
