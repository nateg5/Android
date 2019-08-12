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
            add("397\nBlack Lightning\n80178687\nS02E01\nThe Book of Consequences: Chapter One: Rise of the Green Light Babies\nOctober 09, 2018");
            add("398\nThe Flash\n80027042\nS05E01\nNora\nOctober 09, 2018");
            add("399\nSupergirl\n80065386\nS04E01\nAmerican Alien\nOctober 14, 2018");
            add("400\nArrow\n70242081\nS07E01\nInmate 4587\nOctober 15, 2018");
            add("401\nBlack Lightning\n80178687\nS02E02\nThe Book of Consequences: Chapter Two: Black Jesus Blues\nOctober 16, 2018");
            add("402\nThe Flash\n80027042\nS05E02\nBlocked\nOctober 16, 2018");
            add("403\nSupergirl\n80065386\nS04E02\nFallout\nOctober 21, 2018");
            add("404\nArrow\n70242081\nS07E02\nThe Longbow Hunters\nOctober 22, 2018");
            add("405\nDC's Legends of Tomorrow\n80066080\nS04E01\nThe Virgin Gary\nOctober 22, 2018");
            add("406\nBlack Lightning\n80178687\nS02E03\nThe Book of Consequences: Chapter Three: Master Lowry\nOctober 23, 2018");
            add("407\nThe Flash\n80027042\nS05E03\nThe Death of Vibe\nOctober 23, 2018");
            add("408\nSupergirl\n80065386\nS04E03\nMan of Steel\nOctober 28, 2018");
            add("409\nArrow\n70242081\nS07E03\nCrossing Lines\nOctober 29, 2018");
            add("410\nDC's Legends of Tomorrow\n80066080\nS04E02\nWitch Hunt\nOctober 29, 2018");
            add("411\nBlack Lightning\n80178687\nS02E04\nThe Book of Consequences: Chapter Four: Translucent Freak\nOctober 30, 2018");
            add("412\nThe Flash\n80027042\nS05E04\nNews Flash\nOctober 30, 2018");
            add("413\nSupergirl\n80065386\nS04E04\nAhimsa\nNovember 04, 2018");
            add("414\nArrow\n70242081\nS07E04\nLevel Two\nNovember 05, 2018");
            add("415\nDC's Legends of Tomorrow\n80066080\nS04E03\nDancing Queen\nNovember 05, 2018");
            add("416\nSupergirl\n80065386\nS04E05\nParasite Lost\nNovember 11, 2018");
            add("417\nArrow\n70242081\nS07E05\nThe Demon\nNovember 12, 2018");
            add("418\nDC's Legends of Tomorrow\n80066080\nS04E04\nWet Hot American Bummer\nNovember 12, 2018");
            add("419\nBlack Lightning\n80178687\nS02E05\nThe Book of Blood: Chapter One: Requiem\nNovember 13, 2018");
            add("420\nThe Flash\n80027042\nS05E05\nAll Doll'd Up\nNovember 13, 2018");
            add("421\nSupergirl\n80065386\nS04E06\nCall to Action\nNovember 18, 2018");
            add("422\nArrow\n70242081\nS07E06\nDue Process\nNovember 19, 2018");
            add("423\nDC's Legends of Tomorrow\n80066080\nS04E05\nTagumo Attacks!!!\nNovember 19, 2018");
            add("424\nBlack Lightning\n80178687\nS02E06\nThe Book of Blood: Chapter Two: The Perdi\nNovember 20, 2018");
            add("425\nThe Flash\n80027042\nS05E06\nThe Icicle Cometh\nNovember 20, 2018");
            add("426\nSupergirl\n80065386\nS04E07\nRather the Fallen Angel\nNovember 25, 2018");
            add("427\nArrow\n70242081\nS07E07\nThe Slabside Redemption\nNovember 26, 2018");
            add("428\nDC's Legends of Tomorrow\n80066080\nS04E06\nTender is the Nate\nNovember 26, 2018");
            add("429\nBlack Lightning\n80178687\nS02E07\nThe Book of Blood: Chapter Three: The Sange\nNovember 27, 2018");
            add("430\nThe Flash\n80027042\nS05E07\nO Come, All Ye Thankful\nNovember 27, 2018");
            add("431\nSupergirl\n80065386\nS04E08\nBunker Hill\nDecember 02, 2018");
            add("432\nArrow\n70242081\nS07E08\nUnmasked\nDecember 03, 2018");
            add("433\nDC's Legends of Tomorrow\n80066080\nS04E07\nHell No, Dolly!\nDecember 03, 2018");
            add("434\nBlack Lightning\n80178687\nS02E08\nThe Book of Rebellion: Chapter One: Exodus\nDecember 04, 2018");
            add("435\nThe Flash\n80027042\nS05E08\nWhat's Past is Prologue\nDecember 04, 2018");
            add("436\nThe Flash\n80027042\nS05E09\nElseworlds, Part 1\nDecember 09, 2018");
            add("437\nArrow\n70242081\nS07E09\nElseworlds, Part 2\nDecember 10, 2018");
            add("438\nDC's Legends of Tomorrow\n80066080\nS04E08\nLegends of To-Meow-Meow\nDecember 10, 2018");
            add("439\nSupergirl\n80065386\nS04E09\nElseworlds, Part 3\nDecember 11, 2018");
            add("440\nBlack Lightning\n80178687\nS02E09\nThe Book of Rebellion: Chapter Two: Gift of Magi\nDecember 11, 2018");
            add("441\nThe Flash\n80027042\nS05E10\nThe Flash\n80027042 & The Furious\nJanuary 15, 2019");
            add("442\nSupergirl\n80065386\nS04E10\nSuspicious Minds\nJanuary 20, 2019");
            add("443\nArrow\n70242081\nS07E10\nMy Name is Emiko Queen\nJanuary 21, 2019");
            add("444\nBlack Lightning\n80178687\nS02E10\nThe Book of Rebellion: Chapter Three: Angelitos Negros\nJanuary 21, 2019");
            add("445\nThe Flash\n80027042\nS05E11\nSeeing Red\nJanuary 22, 2019");
            add("446\nSupergirl\n80065386\nS04E11\nBlood Memory\nJanuary 27, 2019");
            add("447\nArrow\n70242081\nS07E11\nPast Sins\nJanuary 28, 2019");
            add("448\nBlack Lightning\n80178687\nS02E11\nThe Book of Secrets: Chapter One: Prodigal Son\nJanuary 28, 2019");
            add("449\nThe Flash\n80027042\nS05E12\nMemorabilia\nJanuary 29, 2019");
            add("450\nArrow\n70242081\nS07E12\nEmerald Archer\nFebruary 04, 2019");
            add("451\nBlack Lightning\n80178687\nS02E12\nThe Book of Secrets: Chapter Two: Just and Unjust\nFebruary 04, 2019");
            add("452\nThe Flash\n80027042\nS05E13\nGoldfaced\nFebruary 05, 2019");
            add("453\nArrow\n70242081\nS07E13\nStar City Slayer\nFebruary 11, 2019");
            add("454\nBlack Lightning\n80178687\nS02E13\nThe Book of Secrets: Chapter Three: Pillar of Fire\nFebruary 11, 2019");
            add("455\nThe Flash\n80027042\nS05E14\nCause and XS\nFebruary 12, 2019");
            add("456\nSupergirl\n80065386\nS04E12\nMenagerie\nFebruary 17, 2019");
            add("457\nSupergirl\n80065386\nS04E13\nWhat's So Funny About Truth, Justice, and the American Way?\nMarch 03, 2019");
            add("458\nArrow\n70242081\nS07E14\nBrothers & Sisters\nMarch 04, 2019");
            add("459\nBlack Lightning\n80178687\nS02E14\nThe Book of Secrets: Chapter Four: Original Sin\nMarch 04, 2019");
            add("460\nThe Flash\n80027042\nS05E15\nKing Shark vs. Gorilla Grodd\nMarch 05, 2019");
            add("461\nSupergirl\n80065386\nS04E14\nStand and Deliver\nMarch 10, 2019");
            add("462\nArrow\n70242081\nS07E15\nTraining Day\nMarch 11, 2019");
            add("463\nBlack Lightning\n80178687\nS02E15\nThe Book of the Apocalypse: Chapter One: The Alpha\nMarch 11, 2019");
            add("464\nThe Flash\n80027042\nS05E16\nFailure is an Orphan\nMarch 12, 2019");
            add("465\nSupergirl\n80065386\nS04E15\nO Brother, Where Art Thou?\nMarch 17, 2019");
            add("466\nArrow\n70242081\nS07E16\nStar City 2040\nMarch 18, 2019");
            add("467\nBlack Lightning\n80178687\nS02E16\nThe Book of the Apocalypse: Chapter Two: The Omega\nMarch 18, 2019");
            add("468\nThe Flash\n80027042\nS05E17\nTime Bomb\nMarch 19, 2019");
            add("469\nSupergirl\n80065386\nS04E16\nThe House of L\nMarch 24, 2019");
            add("470\nArrow\n70242081\nS07E17\nInheritance\nMarch 25, 2019");
            add("471\nSupergirl\n80065386\nS04E17\nAll About Eve\nMarch 31, 2019");
            add("472\nDC's Legends of Tomorrow\n80066080\nS04E09\nLucha De Apuestas\nApril 01, 2019");
            add("473\nDC's Legends of Tomorrow\n80066080\nS04E10\nThe Getaway\nApril 08, 2019");
            add("474\nArrow\n70242081\nS07E18\nLost Canary\nApril 15, 2019");
            add("475\nDC's Legends of Tomorrow\n80066080\nS04E11\nSéance and Sensibility\nApril 15, 2019");
            add("476\nThe Flash\n80027042\nS05E18\nGodspeed\nApril 16, 2019");
            add("477\nSupergirl\n80065386\nS04E18\nCrime and Punishment\nApril 21, 2019");
            add("478\nArrow\n70242081\nS07E19\nSpartan\nApril 22, 2019");
            add("479\nDC's Legends of Tomorrow\n80066080\nS04E12\nThe Eggplant, The Witch & The Wardrobe\nApril 22, 2019");
            add("480\nThe Flash\n80027042\nS05E19\nSnow Pack\nApril 23, 2019");
            add("481\nSupergirl\n80065386\nS04E19\nAmerican Dreamer\nApril 28, 2019");
            add("482\nArrow\n70242081\nS07E20\nConfessions\nApril 29, 2019");
            add("483\nDC's Legends of Tomorrow\n80066080\nS04E13\nEgg MacGuffin\nApril 29, 2019");
            add("484\nThe Flash\n80027042\nS05E20\nGone Rogue\nApril 30, 2019");
            add("485\nSupergirl\n80065386\nS04E20\nWill The Real Miss Tessmacher Please Stand Up?\nMay 05, 2019");
            add("486\nArrow\n70242081\nS07E21\nLiving Proof\nMay 06, 2019");
            add("487\nDC's Legends of Tomorrow\n80066080\nS04E14\nNip/Stuck\nMay 06, 2019");
            add("488\nThe Flash\n80027042\nS05E21\nThe Girl With The Red Lightning\nMay 07, 2019");
            add("489\nSupergirl\n80065386\nS04E21\nRed Dawn\nMay 12, 2019");
            add("490\nArrow\n70242081\nS07E22\nYou Have Saved This City\nMay 13, 2019");
            add("491\nDC's Legends of Tomorrow\n80066080\nS04E15\nTerms of Service\nMay 13, 2019");
            add("492\nThe Flash\n80027042\nS05E22\nLegacy\nMay 14, 2019");
            add("493\nSupergirl\n80065386\nS04E22\nThe Quest for Peace\nMay 19, 2019");
            add("494\nDC's Legends of Tomorrow\n80066080\nS04E16\nHey, World!\nMay 20, 2019");
        }};

        final TextView textView = (TextView)findViewById(R.id.textView);

        textView.setText(getEpisode());

        final Button buttonTitle = (Button)findViewById(R.id.buttonTitle);

        buttonTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String episode = getEpisode();
                String[] episodeParts = episode.split("\n");
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.netflix.com/title/" + episodeParts[2]));
                startActivity(intent);
            }
        });

        final Button buttonWatch = (Button)findViewById(R.id.buttonWatch);

        buttonWatch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String episode = getEpisode();
                String[] episodeParts = episode.split("\n");
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.netflix.com/watch/" + episodeParts[2]));
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
