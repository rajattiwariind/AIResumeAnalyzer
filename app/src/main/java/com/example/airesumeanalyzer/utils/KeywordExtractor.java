package com.example.airesumeanalyzer.utils;

import java.util.ArrayList;
import java.util.List;

public class KeywordExtractor {



    public static List<String> missingSkills(String resume,
                                             String jd){


        List<String> list =
                new ArrayList<>();



        resume=resume.toLowerCase();

        jd=jd.toLowerCase();



        String[] words =
                jd.split("\\W+");



        for(String s : words){


            if(s.length()>3){

                if(!resume.contains(s)){

                    list.add(s);

                }

            }


        }



        return list;



    }


}
