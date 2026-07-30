package com.example.airesumeanalyzer;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import com.example.airesumeanalyzer.networks.GeminiService;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;

import org.json.JSONArray;
import org.json.JSONObject;
import java.util.ArrayList;

public class DashboardActivity extends AppCompatActivity {

    PieChart chart;
    TextView atsScore;
    TextView feedback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        chart = findViewById(R.id.chart);
        atsScore = findViewById(R.id.txtScore);
        feedback = findViewById(R.id.feedback);

        int score = getIntent().getIntExtra("score", 0);
        String resume = getIntent().getStringExtra("resume");

        atsScore.setText(score + "%");
        loadChart(score);

        // Safety check: Make sure resume text actually arrived from the previous activity
        if (resume == null || resume.trim().isEmpty()) {
            feedback.setText("Error: No resume text provided for analysis.");
            return;
        }

        feedback.setText("Analyzing your resume with AI...");

        GeminiService.analyzeResume(resume, new GeminiService.GeminiCallback() {
            @Override
            public void onSuccess(String response) {
                // Parse the clean text out of Google's raw JSON response
                String cleanAnalysis = parseGeminiResponse(response);

                runOnUiThread(() -> {
                    feedback.setText(cleanAnalysis);
                });
            }

            @Override
            public void onFailure(String error) {
                runOnUiThread(() -> {
                    feedback.setText("Failed to get analysis:\n" + error);
                });
            }
        });
    }


    private String parseGeminiResponse(String rawJson) {
        try {
            JSONObject root = new JSONObject(rawJson);
            JSONArray candidates = root.getJSONArray("candidates");
            if (candidates.length() > 0) {
                JSONObject firstCandidate = candidates.getJSONObject(0);
                JSONObject content = firstCandidate.getJSONObject("content");
                JSONArray parts = content.getJSONArray("parts");
                if (parts.length() > 0) {
                    return parts.getJSONObject(0).getString("text");
                }
            }
            return "Could not extract analysis text from response.";
        } catch (Exception e) {
            Log.e("JSON_PARSE_ERROR", "Error parsing Gemini response: " + e.getMessage());

            return rawJson;
        }
    }

    private void loadChart(int score) {
        ArrayList<PieEntry> entries = new ArrayList<>();
        entries.add(new PieEntry(score, "Matched"));
        entries.add(new PieEntry(100 - score, "Missing"));

        PieDataSet dataSet = new PieDataSet(entries, "");
        dataSet.setSliceSpace(3f);

        PieData data = new PieData(dataSet);
        data.setValueTextSize(16);

        chart.setData(data);
        chart.setDrawHoleEnabled(true);
        chart.setHoleRadius(60);
        chart.getDescription().setEnabled(false);
        Legend legend = chart.getLegend();
        legend.setTextSize(14);
        chart.animateY(1200);
        chart.invalidate();
    }
}