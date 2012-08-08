package com.jeff.zhengduan;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity {
	
	private Button btnStory;
	private Button btnVsPlayer;
	private Button btnVsComputer;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupGameMenu();
    }
/****************************************************************************************
*                     
*****************************************************************************************/
	private void setupGameMenu(){
		setContentView(R.layout.main);
        this.btnStory = (Button)findViewById(R.id.btn_main_story);
        this.btnStory.setOnClickListener(new View.OnClickListener(){
			public void onClick(View v){
				startActivity(new Intent(getApplicationContext(), StoryActivity.class));
			}
		});
        this.btnVsComputer = (Button)findViewById(R.id.btn_main_computer);
        this.btnVsComputer.setOnClickListener(new View.OnClickListener(){
			public void onClick(View v){
				startActivity(new Intent(getApplicationContext(), BattleActivity.class));
			}
		});
        
        this.btnVsPlayer = (Button)findViewById(R.id.btn_main_player);
        this.btnVsPlayer.setOnClickListener(new View.OnClickListener(){
			public void onClick(View v){
				startActivity(new Intent(getApplicationContext(), RenBattleActivity.class));
			}
		});
	}
}