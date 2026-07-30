package com.example.airesumeanalyzer.utils;

import java.util.HashSet;
import java.util.Set;

public class ATSCalculator {


    public static int calculate(String resume,
                                String jd){


        resume = resume.toLowerCase();

        jd = jd.toLowerCase();



        String[] words = jd.split("\\W+");


        Set<String> jdWords = new HashSet<>();


        for(String s : words){

            if(s.length()>2)

                jdWords.add(s);

        }


        int matched=0;


        for(String s : jdWords){


            if(resume.contains(s))

                matched++;


        }



        return (matched*100)/jdWords.size();



    }


}
