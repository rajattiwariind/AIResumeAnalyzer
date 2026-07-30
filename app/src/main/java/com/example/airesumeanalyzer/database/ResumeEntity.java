package com.example.airesumeanalyzer.database;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "history")
public class ResumeEntity {


    @PrimaryKey(autoGenerate = true)

    public int id;



    public String filename;


    public int score;



    public long timestamp;



}