package com.example.airesumeanalyzer;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.airesumeanalyzer.adapters.SuggestionAdapter;
import com.example.airesumeanalyzer.utils.ATSCalculator;
import com.example.airesumeanalyzer.utils.KeywordExtractor;
import com.example.airesumeanalyzer.utils.PdfUtils;

import java.util.List;

public class MainActivity extends AppCompatActivity {


    Button upload, analyze;

    TextView fileName, score;

    EditText jobDescription;

    ProgressBar progress;

    RecyclerView recycler;

    String resumeText = "";

    ActivityResultLauncher<String> picker;



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);



        upload = findViewById(R.id.upload);
        analyze = findViewById(R.id.analyze);

        fileName = findViewById(R.id.fileName);

        score = findViewById(R.id.score);

        progress = findViewById(R.id.progress);

        recycler = findViewById(R.id.recycler);

        jobDescription = findViewById(R.id.job);



        recycler.setLayoutManager(

                new LinearLayoutManager(this));



        picker = registerForActivityResult(

                new ActivityResultContracts.GetContent(),

                uri -> {


                    if (uri != null) {


                        resumeText =

                                PdfUtils.extract(

                                        this,

                                        uri);



                        fileName.setText(

                                getName(uri));

                    }


                });



        upload.setOnClickListener(v -> {

            picker.launch(

                    "application/pdf");

        });




        analyze.setOnClickListener(v -> {


            String jd =

                    jobDescription.getText()

                            .toString()

                            .trim();



            if (resumeText.isEmpty()) {

                fileName.setText(

                        "Please upload resume");

                return;

            }



            if (jd.isEmpty()) {


                jobDescription.setError(

                        "Enter Job Description");

                return;

            }




            int ats = ATSCalculator.calculate(

                    resumeText,

                    jd);



            score.setText(

                    "ATS Score : "

                            + ats

                            + "%");




            progress.setProgress(

                    ats);




            List<String> skills =


                    KeywordExtractor.missingSkills(

                            resumeText,

                            jd);




            SuggestionAdapter adapter =


                    new SuggestionAdapter(

                            skills);



            recycler.setAdapter(

                    adapter);


            Intent intent = new Intent(

                    MainActivity.this,

                    DashboardActivity.class);



            intent.putExtra(

                    "score",

                    ats);



            intent.putExtra(

                    "resume",

                    resumeText);



            startActivity(intent);


        });


    }





    private String getName(Uri uri) {


        Cursor cursor =

                getContentResolver()

                        .query(

                                uri,

                                null,

                                null,

                                null,

                                null);



        if (cursor != null) {


            cursor.moveToFirst();



            @SuppressLint("Range")

            String name =

                    cursor.getString(


                            cursor.getColumnIndex(

                                    OpenableColumns.DISPLAY_NAME));



            cursor.close();



            return name;


        }



        return "Resume.pdf";


    }


}