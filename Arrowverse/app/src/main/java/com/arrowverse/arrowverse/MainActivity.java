package com.arrowverse.arrowverse;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedPreferences = getSharedPreferences(getPackageName(), Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        final List<String> episodeList = new ArrayList<String>() {{
            add("20171009_1_SupergirlS03E01_80065386");
            add("20171016_1_SupergirlS03E02_80065386");
            add("20171023_1_SupergirlS03E03_80065386");
            add("20171030_1_SupergirlS03E04_80065386");
            add("20171106_1_SupergirlS03E05_80065386");
            add("20171113_1_SupergirlS03E06_80065386");
            add("20171120_1_SupergirlS03E07_80065386");
            add("20171127_1_SupergirlS03E08_80065386");
            add("20171204_1_SupergirlS03E09_80065386");
            add("20180115_1_SupergirlS03E10_80065386");
            add("20180122_1_SupergirlS03E11_80065386");
            add("20180129_1_SupergirlS03E12_80065386");
            add("20180205_1_SupergirlS03E13_80065386");
            add("20180416_1_SupergirlS03E14_80065386");
            add("20180423_1_SupergirlS03E15_80065386");
            add("20180430_1_SupergirlS03E16_80065386");
            add("20180507_1_SupergirlS03E17_80065386");
            add("20180514_1_SupergirlS03E18_80065386");
            add("20180521_1_SupergirlS03E19_80065386");
            add("20180528_1_SupergirlS03E20_80065386");
            add("20180604_1_SupergirlS03E21_80065386");
            add("20180611_1_SupergirlS03E22_80065386");
            add("20180618_1_SupergirlS03E23_80065386");
            add("20171010_2_FlashS04E01_80027042");
            add("20171017_2_FlashS04E02_80027042");
            add("20171024_2_FlashS04E03_80027042");
            add("20171031_2_FlashS04E04_80027042");
            add("20171107_2_FlashS04E05_80027042");
            add("20171114_2_FlashS04E06_80027042");
            add("20171121_2_FlashS04E07_80027042");
            add("20171128_2_FlashS04E08_80027042");
            add("20171205_2_FlashS04E09_80027042");
            add("20180116_2_FlashS04E10_80027042");
            add("20180123_2_FlashS04E11_80027042");
            add("20180130_2_FlashS04E12_80027042");
            add("20180206_2_FlashS04E13_80027042");
            add("20180227_2_FlashS04E14_80027042");
            add("20180306_2_FlashS04E15_80027042");
            add("20180313_2_FlashS04E16_80027042");
            add("20180410_2_FlashS04E17_80027042");
            add("20180417_2_FlashS04E18_80027042");
            add("20180424_2_FlashS04E19_80027042");
            add("20180501_2_FlashS04E20_80027042");
            add("20180508_2_FlashS04E21_80027042");
            add("20180515_2_FlashS04E22_80027042");
            add("20180522_2_FlashS04E23_80027042");
            add("20180116_3_LightningS01E01_80178687");
            add("20180123_3_LightningS01E02_80178687");
            add("20180130_3_LightningS01E03_80178687");
            add("20180206_3_LightningS01E04_80178687");
            add("20180213_3_LightningS01E05_80178687");
            add("20180227_3_LightningS01E06_80178687");
            add("20180306_3_LightningS01E07_80178687");
            add("20180313_3_LightningS01E08_80178687");
            add("20180320_3_LightningS01E09_80178687");
            add("20180327_3_LightningS01E10_80178687");
            add("20180403_3_LightningS01E11_80178687");
            add("20180410_3_LightningS01E12_80178687");
            add("20180417_3_LightningS01E13_80178687");
            add("20171010_4_LegendS03E01_80066080");
            add("20171017_4_LegendS03E02_80066080");
            add("20171024_4_LegendS03E03_80066080");
            add("20171031_4_LegendS03E04_80066080");
            add("20171107_4_LegendS03E05_80066080");
            add("20171114_4_LegendS03E06_80066080");
            add("20171121_4_LegendS03E07_80066080");
            add("20171128_4_LegendS03E08_80066080");
            add("20171205_4_LegendS03E09_80066080");
            add("20180212_4_LegendS03E10_80066080");
            add("20180219_4_LegendS03E11_80066080");
            add("20180226_4_LegendS03E12_80066080");
            add("20180305_4_LegendS03E13_80066080");
            add("20180312_4_LegendS03E14_80066080");
            add("20180319_4_LegendS03E15_80066080");
            add("20180326_4_LegendS03E16_80066080");
            add("20180402_4_LegendS03E17_80066080");
            add("20180409_4_LegendS03E18_80066080");
            add("20171012_5_ArrowS06E01_70242081");
            add("20171019_5_ArrowS06E02_70242081");
            add("20171026_5_ArrowS06E03_70242081");
            add("20171102_5_ArrowS06E04_70242081");
            add("20171109_5_ArrowS06E05_70242081");
            add("20171116_5_ArrowS06E06_70242081");
            add("20171123_5_ArrowS06E07_70242081");
            add("20171127_5_ArrowS06E08_70242081");
            add("20171207_5_ArrowS06E09_70242081");
            add("20180118_5_ArrowS06E10_70242081");
            add("20180125_5_ArrowS06E11_70242081");
            add("20180201_5_ArrowS06E12_70242081");
            add("20180208_5_ArrowS06E13_70242081");
            add("20180301_5_ArrowS06E14_70242081");
            add("20180308_5_ArrowS06E15_70242081");
            add("20180329_5_ArrowS06E16_70242081");
            add("20180405_5_ArrowS06E17_70242081");
            add("20180412_5_ArrowS06E18_70242081");
            add("20180419_5_ArrowS06E19_70242081");
            add("20180426_5_ArrowS06E20_70242081");
            add("20180503_5_ArrowS06E21_70242081");
            add("20180510_5_ArrowS06E22_70242081");
            add("20180517_5_ArrowS06E23_70242081");
        }};

        Collections.sort(episodeList);

        final TextView textView = (TextView)findViewById(R.id.textView);

        textView.setText(getEpisode());

        final Button buttonTitle = (Button)findViewById(R.id.buttonTitle);

        buttonTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String episode = getEpisode();
                String[] episodeParts = episode.split("_");
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.netflix.com/title/" + episodeParts[3]));
                startActivity(intent);
            }
        });

        final Button buttonWatch = (Button)findViewById(R.id.buttonWatch);

        buttonWatch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String episode = getEpisode();
                String[] episodeParts = episode.split("_");
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.netflix.com/watch/" + episodeParts[3]));
                startActivity(intent);
            }
        });

        final Button buttonFinish = (Button)findViewById(R.id.buttonFinish);

        buttonFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int episodeIndex = episodeList.indexOf(getEpisode());
                episodeIndex++;
                if(episodeIndex == episodeList.size()) {
                    episodeIndex = 0;
                }
                setEpisode(episodeList.get(episodeIndex));
                textView.setText(getEpisode());
            }
        });

        editor.apply();
    }

    private String getEpisode() {
        return sharedPreferences.getString("episode", getString(R.string.default_episode));
    }

    private void setEpisode(String episode) {
        editor.putString("episode", episode);
        editor.commit();
    }
}
