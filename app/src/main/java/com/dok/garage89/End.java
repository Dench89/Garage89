package com.dok.garage89;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Window;

public class End extends AppCompatActivity {

    private SharedPreferences count_pref;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // скрываем нижнюю панель навигации - начало

        Window w = getWindow();
        w.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION //скрываем нижнюю панель
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY  //появляется поверх игры и исчезает - иммерсивный режим
        );
        // скрываем нижнюю панель навигации - конец
        //включим ночную тему - начало
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        //включим ночную тему - конец
        setContentView(R.layout.activity_end);

        //пишем код кнопки
        CardView start_over = findViewById(R.id.start_over);
        //добавим слушатель кнопки start_over

        start_over.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //возврат начальных данных для счетчика касаний -начало
                count_pref = getSharedPreferences("Count_pref", MODE_PRIVATE);
                SharedPreferences.Editor editor = count_pref.edit();// чтобы редактировать данные , нужен объект edit
                editor.putInt("save_key_count", 999999);             //метод put.Int - поместить целое число
                editor.apply();                                    //чтобы весь код заработал ,надо написать commit (фиксация)

                //возврат начальных данных для счетчика касаний -конец

                // пишем намерение, для возврата в activity_main.xml-начало
                Intent intent = new Intent(End.this, MainActivity.class);
                // пишем намерение, для возврата в activity_main.xml-конец

                //команда на переход
                startActivity(intent);
                finish();
            }

        });
    }


}