package com.deltaappdev.inductions18.infinitystonequest;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;




public class MainActivity extends AppCompatActivity {

    //SharedPreferences sharedPref = this.getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);

    int stoneCount=-1;
    int rand, stoneNumber=-1;
    int[] stoneNumbers= {-1,-1,-1,-1,-1,-1,-1};


    boolean repeated=false;

    int stoneNo, stoneImageID, stoneColour;
    String stoneName;

    Button buttonGet, buttonReset;
    RelativeLayout relativeLayoutMain;

    View view;
    TextView textView,textViewNotification,textViewStoneObtained;

    ListView stoneList;


    private ListViewAdaptor mAdapter;
    private RecyclerView mRecyclerView;
    private List<stonesData> infinityStones= new ArrayList<>();

    int stoneCountOld_backup;
    static final String STATE_STONE_COUNT = "stoneCount";
    static final String STATE_STONE_NUMBER = "stoneNumber";
    static final String STATE_STONE_NUMBERS = "stoneNumbers";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initialise();

        SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);

        // Check whether we're recreating a previously destroyed instance
        if (savedInstanceState != null) {

            // Restore value of members from saved state
            stoneCountOld_backup = savedInstanceState.getInt(STATE_STONE_COUNT);
            stoneNumber = savedInstanceState.getInt(STATE_STONE_NUMBER);
            stoneNumbers = savedInstanceState.getIntArray(STATE_STONE_NUMBERS);

            restoreAllData();

        }
        else if((sharedPref.getInt(getString(R.string.saved_stone_count),-1))!=-1){

            Log.d("debug.net","stoneNumber!=-1");

            stoneCountOld_backup = sharedPref.getInt(getString(R.string.saved_stone_count), 0);
            stoneNumber = sharedPref.getInt(getString(R.string.saved_stone_count), -1);
            stoneNumbers[0] = sharedPref.getInt(getString(R.string.saved_stone_numbers0), -1);
            stoneNumbers[1] = sharedPref.getInt(getString(R.string.saved_stone_numbers1), -1);
            stoneNumbers[2] = sharedPref.getInt(getString(R.string.saved_stone_numbers2), -1);
            stoneNumbers[3] = sharedPref.getInt(getString(R.string.saved_stone_numbers3), -1);
            stoneNumbers[4] = sharedPref.getInt(getString(R.string.saved_stone_numbers4), -1);
            stoneNumbers[5] = sharedPref.getInt(getString(R.string.saved_stone_numbers5), -1);

            restoreAllData();

        }
        else {
            Toast.makeText(this.getBaseContext(),"Welcome",Toast.LENGTH_SHORT).show();

        }



            buttonGet.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    stoneSelect();
                }
            });

            buttonReset.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    resetAll();
                }
            });

        }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {

        // Save the user's current game state
        savedInstanceState.putInt(STATE_STONE_COUNT, stoneCount);
        savedInstanceState.putInt(STATE_STONE_NUMBER, stoneNumber);
        savedInstanceState.putIntArray(STATE_STONE_NUMBERS, stoneNumbers);

        super.onSaveInstanceState(savedInstanceState);
    }

    @Override
    protected void onPause() {

        SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor saver = sharedPref.edit();

        saver.putInt(getString(R.string.saved_stone_count), stoneCount);
        saver.putInt(getString(R.string.saved_stone_number), stoneNumber);
        saver.putInt(getString(R.string.saved_stone_numbers0), stoneNumbers[0]);
        saver.putInt(getString(R.string.saved_stone_numbers1), stoneNumbers[1]);
        saver.putInt(getString(R.string.saved_stone_numbers2), stoneNumbers[2]);
        saver.putInt(getString(R.string.saved_stone_numbers3), stoneNumbers[3]);
        saver.putInt(getString(R.string.saved_stone_numbers4), stoneNumbers[4]);
        saver.putInt(getString(R.string.saved_stone_numbers5), stoneNumbers[5]);

        saver.apply();

        super.onPause();
    }

    @Override
    protected void onDestroy() {

        SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor saver = sharedPref.edit();

        saver.putInt(getString(R.string.saved_stone_count), stoneCount);
        saver.putInt(getString(R.string.saved_stone_number), stoneNumber);
        saver.putInt(getString(R.string.saved_stone_numbers0), stoneNumbers[0]);
        saver.putInt(getString(R.string.saved_stone_numbers1), stoneNumbers[1]);
        saver.putInt(getString(R.string.saved_stone_numbers2), stoneNumbers[2]);
        saver.putInt(getString(R.string.saved_stone_numbers3), stoneNumbers[3]);
        saver.putInt(getString(R.string.saved_stone_numbers4), stoneNumbers[4]);
        saver.putInt(getString(R.string.saved_stone_numbers5), stoneNumbers[5]);

        saver.apply();

        super.onPause();
        super.onDestroy();
    }



    public void randomNum(){
        rand=new Random().nextInt(6);
    }

    public void stoneSelect(){
        randomNum();
        stoneNumber = rand+1;

        for(int i=0; i<(stoneCount+1); i++){
            if (stoneNumbers[i]==stoneNumber) {
                repeated=true;
            }
        }

        if(stoneCount<5&&(!repeated)){
            stoneCount++;
            dataPrepare();

            textViewNotification.setVisibility(View.VISIBLE);
            textViewNotification.setText("Congrats, You have obtained " + stoneName);

            view.setVisibility(View.VISIBLE);
            view.setBackgroundResource(stoneColour);

            textViewStoneObtained.setText("Infinity Stones obtained: "+ String.valueOf(stoneCount+1)+"/6");

            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                public void run() {
                    textViewNotification.setVisibility(View.INVISIBLE);
                    view.setVisibility(View.INVISIBLE);
                }
            }, 1000);

        }
        else if(stoneCount>5){ //must never happen
           Toast.makeText(this.getBaseContext(),"Error: stoneCount>5",Toast.LENGTH_SHORT).show();
            Toast.makeText(this.getBaseContext(),"All reset",Toast.LENGTH_SHORT).show();
            resetAll();
        }
        else if(repeated&(!(stoneCount+1==6))){
            textViewNotification.setVisibility(View.VISIBLE);
            textViewNotification.setTextColor(getResources().getColor(R.color.red));
            textViewNotification.setText("Sorry, No stones obtained this time!");

            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                public void run() {
                    textViewNotification.setVisibility(View.INVISIBLE);
                    textViewNotification.setTextColor(getResources().getColor(R.color.notificationColour));
                }
            }, 450);

            view.setVisibility(View.INVISIBLE);

        }
        else {
            Toast.makeText(this.getBaseContext(),"???" + "",Toast.LENGTH_SHORT).show();
            textViewNotification.setVisibility(View.INVISIBLE);
            view.setVisibility(View.INVISIBLE);
        }


        // When all Infinity Stones Obtained
        if(stoneCount+1==6){

            textViewStoneObtained.setText("All Infinity Stones obtained");

            buttonGet.setEnabled(false);
            buttonReset.setEnabled(false);

            Toast.makeText(getBaseContext(),"You have attained your destiny!",Toast.LENGTH_SHORT).show();

            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                public void run() {

                    if(getResources().getConfiguration().orientation==Configuration.ORIENTATION_PORTRAIT)
                    {
                        relativeLayoutMain.setBackgroundResource(R.drawable.background_portrait);
                    }
                    else{
                        relativeLayoutMain.setBackgroundResource(R.drawable.background_landscape);
                    }

                    textViewNotification.setVisibility(View.VISIBLE);
                    textViewNotification.setText("Snap Now");

                    Toast.makeText(getBaseContext(),"Its time to fulfill the purpose!!!",Toast.LENGTH_SHORT).show();
                    Toast.makeText(getBaseContext(),"***Snap Now***",Toast.LENGTH_SHORT).show();

                    buttonGet.setVisibility(View.INVISIBLE);
                    mRecyclerView.setVisibility(View.INVISIBLE);
                    textView.setVisibility(View.INVISIBLE);
                    buttonReset.setEnabled(true);
                }
            }, 2500);
        }

        repeated=false;
    }

    public void resetAll(){
        stoneNumber=10;
        for(int i=0; i<7; i++){
            stoneNumbers[i]=-1;
        }
        stoneCount=-1;


        textViewNotification.setVisibility(View.INVISIBLE);
        relativeLayoutMain.setBackgroundResource(R.color.black);
        textViewStoneObtained.setText("Infinity Stones obtained:");
        buttonGet.setEnabled(true);
        buttonGet.setVisibility(View.VISIBLE);
        mRecyclerView.setVisibility(View.VISIBLE);
        textView.setVisibility(View.INVISIBLE);

        stoneDataReset();
    }

    public void initialise(){

        relativeLayoutMain=(RelativeLayout)findViewById(R.id.relativeLayoutMain);

        buttonGet=(Button)findViewById(R.id.buttonGet);
        buttonReset=(Button)findViewById(R.id.buttonReset);

        textView=(TextView)findViewById(R.id.textView);
        textViewNotification=(TextView)findViewById(R.id.textViewNotification);
        textViewStoneObtained=(TextView)findViewById(R.id.textViewStoneObtained);

        view=(View)findViewById(R.id.view);

        stoneList=(ListView)findViewById(R.id.stoneList);
        //from api 27 "stoneList=findViewById(R.id.stoneList);" also works

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        mAdapter = new ListViewAdaptor(infinityStones);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setHasFixedSize(true);

        mRecyclerView.setAdapter(mAdapter);

    }

    public void dataPrepare(){
        textView.setVisibility(View.VISIBLE);
        mRecyclerView.setAdapter(mAdapter);
        stoneDataPrepare();

        mRecyclerView.scrollToPosition(infinityStones.size()-1);
        //mRecyclerView.scrollToPosition(stoneCount+1);

    }

    public void  stoneDataPrepare(){

        if(stoneNumber==1) {
            stoneNo=1;
            stoneImageID=R.drawable.infinity_stone_power;
            stoneName="Power Stone";
            stoneColour=R.color.purple;
            stoneNumbers[stoneCount]=1;
        }
        else if (stoneNumber==2){
            stoneNo=2;
            stoneImageID=R.drawable.infinity_stone_space;
            stoneName="Space Stone";
            stoneColour=R.color.blue;
            stoneNumbers[stoneCount]=2;
        }
        else if (stoneNumber==3){
            stoneNo=3;
            stoneImageID=R.drawable.infinity_stone_time;
            stoneName="Time Stone";
            stoneColour=R.color.green;
            stoneNumbers[stoneCount]=3;
        }
        else if (stoneNumber==4){
            stoneNo=4;
            stoneImageID=R.drawable.infinity_stone_reality;
            stoneName="Reality Stone";
            stoneColour=R.color.red;
            stoneNumbers[stoneCount]=4;
        }
        else if (stoneNumber==5){
            stoneNo=5;
            stoneImageID=R.drawable.infinity_stone_soul;
            stoneName="Soul Stone";
            stoneColour=R.color.orange;
            stoneNumbers[stoneCount]=5;
        }
        else if (stoneNumber==6) {
            stoneNo=6;
            stoneImageID=R.drawable.infinity_stone_mind;
            stoneName="Mind Stone";
            stoneColour=R.color.yellow;
            stoneNumbers[stoneCount]=6;
        }

        if(stoneNumber!=-1){
            infinityStones.add(new stonesData(stoneNo,stoneImageID, stoneName, stoneColour));
        }

    }

    public void  stoneDataReset(){

        infinityStones.clear();

        mRecyclerView.setAdapter(mAdapter);
        mAdapter.notifyItemRangeRemoved(0,5);
       // mAdapter.notifyDataSetChanged();
    }



    public void restoreAllData(){


        for(int i=0; stoneNumbers[i]!=-1; i++){
            stoneNumber = stoneNumbers[i];
            stoneCount++;
            dataPrepare();
        }
        if(stoneCount!=stoneCountOld_backup){
            Toast.makeText(getBaseContext(),"Fatal Error",Toast.LENGTH_SHORT).show();
            onDestroy();
        }

        if(stoneCount!=0){
            textViewStoneObtained.setText("Infinity Stones obtained: "+ String.valueOf(stoneCount+1)+"/6");
        }


        if(stoneCount+1==6){
            textViewStoneObtained.setText("All Infinity Stones obtained");

            Toast.makeText(getBaseContext(),"You have already attained your destiny!",Toast.LENGTH_SHORT).show();

            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                public void run() {

                    if(getResources().getConfiguration().orientation==Configuration.ORIENTATION_PORTRAIT)
                    {
                        relativeLayoutMain.setBackgroundResource(R.drawable.background_portrait);
                    }
                    else{
                        relativeLayoutMain.setBackgroundResource(R.drawable.background_landscape);
                    }

                    textViewNotification.setText("Snap Now");
                    textViewNotification.setVisibility(View.VISIBLE);
                    buttonGet.setVisibility(View.INVISIBLE);

                    mRecyclerView.setVisibility(View.INVISIBLE);
                    textView.setVisibility(View.INVISIBLE);
                    buttonReset.setEnabled(true);
                }
            }, 2500);
        }

    }

}

