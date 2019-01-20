package com.example.progressbar;

import android.animation.ObjectAnimator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

  private static final int INITIAL_HP = 100;

  private int hp = INITIAL_HP;
  private int healValue = 10;
  private int attackValue = 2;
  private int attackSpeed = 1000; //milliseconds

  private Timer timer;
  private ProgressBar hpBar;
  private Button btnHeal;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    btnHeal = findViewById(R.id.btnHeal);
    hpBar = findViewById(R.id.hpBar);

    btnHeal.setOnClickListener(lesserHeal);

    timer = new Timer();
    timer.scheduleAtFixedRate(new TimerTask() {
      @Override
      public void run() {
        runTimer();
      }
    }, 0, attackSpeed);
  }

  private void runTimer() {
    this.runOnUiThread(timerTick);
  }

  private Runnable timerTick = new Runnable() {
    @Override
    public void run() {
      attack();
    }
  };

  View.OnClickListener lesserHeal = new View.OnClickListener() {
    @Override
    public void onClick(View v) {
      if (hp + healValue < INITIAL_HP) {
        hp = hp + healValue;
      } else {
        hp = INITIAL_HP;
      }
      ObjectAnimator progressAnimator = ObjectAnimator.ofInt(hpBar, "progress", hpBar.getProgress(), hp);
      progressAnimator.setDuration(250);
      progressAnimator.setInterpolator(new LinearInterpolator());
      progressAnimator.start();
    }
  };

  private void attack() {
    if (hp - attackValue > 0) {
      hp = hp - attackValue;
    } else {
      hp = 0;
      timer.cancel();
      Toast toast = Toast.makeText(getApplicationContext(), "Game Over!", Toast.LENGTH_LONG);
      toast.show();
    }
    ObjectAnimator progressAnimator = ObjectAnimator.ofInt(hpBar, "progress", hpBar.getProgress(), hp);
    progressAnimator.setDuration(150);
    progressAnimator.setInterpolator(new LinearInterpolator());
    progressAnimator.start();
  }

}
