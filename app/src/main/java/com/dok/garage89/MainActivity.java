package com.dok.garage89;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.cardview.widget.CardView;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.yandex.mobile.ads.common.AdRequest;
import com.yandex.mobile.ads.common.InitializationListener;
import com.yandex.mobile.ads.common.MobileAds;

//*********************************************************************************
import com.yandex.mobile.ads.instream.MobileInstreamAds;
import com.yandex.mobile.ads.nativeads.NativeAd;
import com.yandex.mobile.ads.nativeads.NativeAdAssets;
import com.yandex.mobile.ads.nativeads.NativeAdException;
import com.yandex.mobile.ads.nativeads.NativeAdEventListener;
import com.yandex.mobile.ads.nativeads.NativeAdLoader;
import com.yandex.mobile.ads.nativeads.NativeAdRequestConfiguration;
import com.yandex.mobile.ads.nativeads.NativeAdType;
// Дополнительные классы для интерфейса пользователя и управления рекламой
import android.widget.FrameLayout;
import android.app.Activity;
import android.content.Context;
// Дополнительные классы для обработки ошибок
import java.io.IOException;


public class MainActivity extends AppCompatActivity {


    ImageView garage89;
    public int count = 999999;//счетчик касаний, обьявляем переменную, для хранения в себе общего значение оставшихся нажатий
    ProgressBar ProgressBar;//укажем,что в макете есть готовый ProgressBar
    public int score_gold = 0;//создадим переменную, в которой будет храниться количество золотых монет *1
    private SharedPreferences count_pref;  //обьявили переменную для сохранения данных в SharedPreferences для счетчика нажатий
    private SharedPreferences gold_pref;  //обьявили переменную для сохранения данных в SharedPreferences для золотых монет
    int btn_count_cv;//хранить значение выбранного инструмента
    private SharedPreferences tool_pref; //добавим переменную tool_pref , через которую будем обращаться к файлу SharedPreferences с сохраненными данными




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Инициализация Mobile Ads SDK - начало
        MobileAds.initialize(this, () -> {
        // Включаем предзагрузку рекламы до ее показа
        MobileInstreamAds.setAdGroupPreloading(true);
        // Включаем логирование, чтобы следить за состоянием рекламы
        MobileAds.enableLogging(true);});
        //Инициализация Mobile Ads SDK - конец

        //Реклама Яндекс- начало

        //Реклама Яндекс- конец

/////////////////////////////////////////////////////////////////////////////////////////////////////

        Window w = getWindow();
        w.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION //скрываем нижнюю панель
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY //появляется поверх игры и исчезает - иммерсивный режим
        );
        // скрываем нижнюю панель навигации - конец
        //включим ночную тему - начало
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        //включим ночную тему - конец

        //созданим файл для хранения данных - начало
        count_pref = getSharedPreferences("Count_pref", MODE_PRIVATE);//здесь будет создан с модификатором MODE_PRIVATE приватный файл с именем "Count_pref" для сохранения результатов.
        //это значит что доступ к файлу "Count_pref" будет только у нашего приложения.
        count = count_pref.getInt("save_key_count", count); //здесь происходит считывание и присваивание данных счетчиком касаний с файла count_pref
        //если файлов данных нет, в начале игры, то при попытке считать первый (один раз при пустых данных)раз, туда запишутся значения счетчика count 999999.
        gold_pref = getSharedPreferences("Gold_pref", MODE_PRIVATE);
        //переменная count получает доступ к файлу "Count_pref"
        score_gold = gold_pref.getInt("save_key_gold", score_gold);//здесь происходит считывание и присваивание счетчика золотых монет с файла Gold_pref
        //созданим файл для хранения данных - конец

        garage89 = findViewById(R.id.garage89); // поиск элемента
        //обработать нажатие на картину - начало
        imgSet();
        TextView text_count = findViewById(R.id.text_count); // поиск в xml TextView text_count
        text_count.setText(count + ""); // устанавливаем (связываем) переменную text_count значение счетчика count

        TextView gold_txt = findViewById(R.id.gold_txt);
        gold_txt.setText(getString(R.string.txt_gold) + " " + score_gold);// устанавливаем (связываем) элемент макета TextView gold_txt к вычислениям score_gold *2

        ImageView tool_img = findViewById(R.id.tool_img);
        TextView tool_txt = findViewById(R.id.tool_txt);
        tool_pref = getSharedPreferences("Tool_pref", MODE_PRIVATE);
        btn_count_cv = tool_pref.getInt("save_key_tool", 1); /*переменная btn_count_cv будет получать данные от переменной tool_pref, которая
        получает данные о выбранном инструменте из файла SharedPreferences tool_pref по ключу "save_key_tool"*/

        switch (btn_count_cv) {  //оператор switch смотрит в файл Tool_pref и смотрит там какое значение . Если там значение 50, то картинка tool_e.
            case 1:
                tool_img.setImageResource(R.drawable.tool_a);
                tool_txt.setText(R.string.tool);
                break;
            case 2:
                tool_img.setImageResource(R.drawable.tool_b);
                tool_txt.setText(R.string.toolx2);
                break;
            case 5:
                tool_img.setImageResource(R.drawable.tool_c);
                tool_txt.setText(R.string.toolx5);
                break;
            case 10:
                tool_img.setImageResource(R.drawable.tool_d);
                tool_txt.setText(R.string.toolx10);
                break;
            case 50:
                tool_img.setImageResource(R.drawable.tool_e);
                tool_txt.setText(R.string.toolx50);
                break;
            default:
                break;
        }

        ProgressBar progressBar = findViewById(R.id.progress_bar); //поиск в xml ProgressBar
        progressBar.setProgress(count);//связали переменную progressBar с переменной count

        FloatingActionButton goto_shop = findViewById(R.id.goto_shop);
        ////добавили обработчик клика по кнопке floatingactionbutton.FloatingActionButton (goto_shop)
        goto_shop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //сохранение данных после нажатия на кнопку покупки в игровом магазине * случай 2
                //сохраняем значения счетчика касаний - начало
                SharedPreferences.Editor editor = count_pref.edit();// чтобы редактировать данные , нужен объект edit
                editor.putInt("save_key_count", count);             //метод put.Int - поместить целое число
                editor.apply();                                    //чтобы весь код заработал ,надо написать commit (фиксация)
                //сохраняем значения счетчика касаний - конец

                //сохраняем значения счетчика золотых монет - начало
                SharedPreferences.Editor editor2 = gold_pref.edit();
                editor2.putInt("save_key_gold", score_gold);
                editor2.apply();
                //сохраняем значения счетчика золотых монет - конец
            }
        });


        garage89.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    //если коснулся этой картинки, сработает этот код (сразу два параметра)
                    count = count - btn_count_cv; /*уменьшение счетчика на единицу или купленный инструмент будет отнимать нужное количество счетчика касаний.*/
                    score_gold = score_gold + btn_count_cv;//проводим вычисления *3 или добавится столько же золота, сколько купили.
                    gold_txt.setText(getString(R.string.txt_gold) + "" + score_gold);//выводим на экран макета*4
                    text_count.setText(count + "");//показать значение счетчика после касания
                    progressBar.setProgress(count);//показать значение progressBar после касания
                    v.animate().scaleX(0.98f).scaleY(0.98f).setDuration(0); //уменьшение анимации
                    imgSet();
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    //если отпустил палец, сработает этот код
                    v.animate().scaleX(1f).scaleY(1f).setDuration(0); //setDuration(0); - картинка уменьшается по времени сразу без анимации
                }
                return true;
            }

        });
        //начининаем сохранять сразу данные в SharedPreferences после касания картинки tool_img в cardview.widget.CardView(tools)
        CardView tools = findViewById(R.id.tools); //обьявили новую переменную tools
        tools.setOnClickListener(new View.OnClickListener() {       //добавили обработчик клика по кнопке инструмента(карточка cardview.widget)
            @Override
            public void onClick(View v) {
                //сохранение данных в файле SharedPreferences случай 1

                //кнопка выбора инстурментов (сохранение счетчика касаний  при касании на кнопку выбора инструментов)-начало
                SharedPreferences.Editor editor = count_pref.edit();// чтобы редактировать данные , нужен объект edit
                editor.putInt("save_key_count", count);             //метод put.Int - поместить целое число
                editor.apply(); // Используйте apply() для применения изменений
                //кнопка выбора инстурментов (сохранение счетчика золотых монет при касании на кнопку выбора инструментов)-конец

                //кнопка выбора инстурментов (сохранение счетчика золотых монет при касании на кнопку выбора инструментов)-начало
                SharedPreferences.Editor editor2 = gold_pref.edit();
                editor2.putInt("save_key_gold", score_gold);
                editor2.apply();

                

                Intent intent = new Intent(MainActivity.this, Shop.class);
                startActivity(intent);
                finish(); //finish(); - закрывает после себя прежнее activity
                //кнопка выбора инстурментов (сохранение счетчика золотых монет при касании на кнопку выбора инструментов)-конец
            }
        });
        //заканчиваем сохранять данные в SharedPreferences после касания tool_img в cardview.widget.CardView(tools)
        //обработать нажатие на картину - конец
        //***
        //счетчик золотых монет - пояснение
        //сначала обьявляем переменные.*1
        //связываем их с макетом*2
        //проводим вычисления*3
        //выводим результат новых вычислений в макет*4
        //***
    }



    // метод с условием смены картинок - начало
    public void imgSet() {
        if (count > 99949) {
            garage89.setImageResource(R.drawable.img_0);
        }
        if (count <= 999849 & count > 996499) {
            garage89.setImageResource(R.drawable.img_2);
        }
        if (count <= 997849 & count > 996499) {
            garage89.setImageResource(R.drawable.img_3);
        }
        if (count <= 996499 & count > 993499) {
            garage89.setImageResource(R.drawable.img_4);
        }
        if (count <= 993499 & count > 991400) {
            garage89.setImageResource(R.drawable.img_5);
        }
        if (count <= 991400 & count > 991099) {
            garage89.setImageResource(R.drawable.img_6);
        }
        if (count <= 991099 & count > 988099) {
            garage89.setImageResource(R.drawable.img_7);
        }
        if (count <= 988099 & count > 968099) {
            garage89.setImageResource(R.drawable.img_8);
        }
        if (count <= 968099 & count > 966499) {
            garage89.setImageResource(R.drawable.img_9);
        }
        if (count <= 966499 & count > 962099) {
            garage89.setImageResource(R.drawable.img_10);
        }
        if (count <= 962099 & count > 960499) {
            garage89.setImageResource(R.drawable.img_11);
        }
        if (count <= 960499 & count > 950899) {
            garage89.setImageResource(R.drawable.img_12);
        }
        if (count <= 950899 & count > 913499) {
            garage89.setImageResource(R.drawable.img_13);
        }
        if (count <= 913499 & count > 910499) {
            garage89.setImageResource(R.drawable.img_14);
        }
        if (count <= 910499 & count > 900099) {
            garage89.setImageResource(R.drawable.img_15);
        }
        if (count <= 900099 & count > 884499) {
            garage89.setImageResource(R.drawable.img_16);
        }
        if (count <= 884499 & count > 881899) {
            garage89.setImageResource(R.drawable.img_17);
        }
        if (count <= 881899 & count > 836999) {
            garage89.setImageResource(R.drawable.img_18);
        }
        if (count <= 836999 & count > 813499) {
            garage89.setImageResource(R.drawable.img_19);
        }
        if (count <= 813499 & count > 746099) {
            garage89.setImageResource(R.drawable.img_20);
        }
        if (count <= 746099 & count > 695499) {
            garage89.setImageResource(R.drawable.img_21);
        }
        if (count <= 695499 & count > 618599) {
            garage89.setImageResource(R.drawable.img_22);
        }
        if (count <= 618599 & count > 539499) {
            garage89.setImageResource(R.drawable.img_23);
        }
        if (count <= 539499 & count > 466499) {
            garage89.setImageResource(R.drawable.img_24);
        }
        if (count <= 466499 & count > 345099) {
            garage89.setImageResource(R.drawable.img_25);
        }
        if (count <= 345099 & count > 246499) {
            garage89.setImageResource(R.drawable.img_26);
        }
        if (count <= 246499 & count > 100) {
            garage89.setImageResource(R.drawable.img_27);
        }
        if (count <= 100 & count > 0) {
            garage89.setImageResource(R.drawable.img_28);
        }
        if (count <= 0) {
            //новое условие окончания игры - начало
            //сохраняем данные, при переходе в End activity
            SharedPreferences.Editor editor = count_pref.edit();
            editor.putInt("save_key_count", 0); // 0 это обнуление счетчика, когда игрок закрывает игру заранее
            SharedPreferences.Editor editor2 = gold_pref.edit();
            editor2.putInt("save_key_gold", score_gold);
            editor2.apply();

            Intent intent = new Intent(MainActivity.this, End.class);
            startActivity(intent);
            finish();
            //новое условие окончания игры - конец
        }
        // метод с условием смены картинок - конец

    }
    //пишем тут вне основного метода(перед последней скобкой)
    //Сохраняем значения счетчика при запуске системной кнопки «Назад».
    //Для вызова метода нажимаем ctrl+ O (en)

    //Системная кнопка назад - начало
    @Override
    public void onBackPressed() {
        SharedPreferences.Editor editor = count_pref.edit();// чтобы редактировать данные , нужен объект edit
        editor.putInt("save_key_count", count);             //метод put.Int - поместить целое число
        editor.apply();                                   //чтобы весь код заработал ,надо написать commit (фиксация)
        //сохранение данных счетчиков касаний и золотых монет при нажатии на системную кнопку НАЗАД в телефоне
        SharedPreferences.Editor editor2 = gold_pref.edit();
        editor2.putInt("save_key_gold", score_gold);
        editor2.apply();
        super.onBackPressed();
    }
    //Системная кнопка назад - конец


}



