package com.example.airesumeanalyzer.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.airesumeanalyzer.database.ResumeEntity;

@Database(

        entities = {

                ResumeEntity.class},

        version = 1)



public abstract class ResumeDatabase


        extends RoomDatabase {




    public abstract ResumeDao dao();




}