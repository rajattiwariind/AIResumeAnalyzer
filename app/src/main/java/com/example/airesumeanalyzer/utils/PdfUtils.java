package com.example.airesumeanalyzer.utils;

import android.content.Context;
import android.net.Uri;

import com.tom_roush.pdfbox.android.PDFBoxResourceLoader;
import com.tom_roush.pdfbox.pdmodel.PDDocument;
import com.tom_roush.pdfbox.text.PDFTextStripper;

import java.io.InputStream;

public class PdfUtils {


    public static String extract(Context context,
                                 Uri uri){


        try{

            PDFBoxResourceLoader.init(context);


            InputStream inputStream =

                    context.getContentResolver()
                            .openInputStream(uri);



            PDDocument document =
                    PDDocument.load(inputStream);


            PDFTextStripper stripper =
                    new PDFTextStripper();


            String text =
                    stripper.getText(document);


            document.close();

            return text;


        }

        catch (Exception e){

            e.printStackTrace();

        }


        return "";


    }


}
