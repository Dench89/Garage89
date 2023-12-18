package com.dok.garage89;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;



public class Shop extends AppCompatActivity {


    private SharedPreferences x2x5x10x50_pref;// Переменная x2x5x10x50_pref для обращения к файлу SharedPreferences , в котором хранятся значения инструментов.
    private SharedPreferences tool_pref;//Переменная tool_pref для обращения к файлу SharedPreferences , в котором находятся данные о том, какой инструмент выбран.
    private SharedPreferences gold_pref;// Переменная gold_pref, для обращения к файлу SharedPreferences, в котором хранятся значения золотых монет.
    int btn_count_cv; // переменная , через которую будем вытаскивать значение из файла SharedPreferences tool_pref о том, какой инструмент выбран.


    int x2 = 0;             // создание переменных , через которые вытаскиваются значения инструментов из файла x2x5x10x50_pref;
    int x5 = 0;
    int x10 = 0;
    int x50 = 0;

    /*нужно добавить счетчик золотых монет. При покупке инструмента будем отнимать от счетчика касаний цену инструмента.
    По умолчанию счетчик касаний равен -1. При покупке инструмента счетчик касаний увеличится на цену инструмента. Купили инструмент за 50, отнимается счетчиком
    касаний уже тоже 50.*/
    int score_gold = 0; // счетчик золотых монет


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Window w = getWindow();
        w.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        );
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        setContentView(R.layout.activity_shop);

        InnerClass innerClass = new InnerClass(); //создали экземпляр класса с помощью слова new
//тип - InnerClass, переменная innerClass =(оператор присваивания), new InnerClass(); - тут выделение памяти для нового экземпляра класса и инициализация экземпляра.
//оператор присваивания присваивает переменной ссылку на только что созданный обьект.

//напишем код, который обратится к внутреннему классу из главного и вызовет наш метод styleCardView, созданный во внутреннем классе InnerClass.

        innerClass.styleCardView(); //через переменную innerClass, обращаемся к классу InnerClass, к методу styleCardView.

        //напишем из какого файла брать данные, для переменных x2 x5 x10 x50 (создание файла c параметром MODE_PRIVATE)
        x2x5x10x50_pref = getSharedPreferences("X2X5X10X50_pref", MODE_PRIVATE);
        //напишем из какого файла брать данные, для переменной score_gold (создание файла c параметром MODE_PRIVATE)
        gold_pref = getSharedPreferences("Gold_pref", MODE_PRIVATE);

        tool_pref = getSharedPreferences("Tool_pref", MODE_PRIVATE);
        btn_count_cv = tool_pref.getInt("save_key_tool", 1); /*переменная btn_count_cv будет получать данные от переменной tool_pref, которая
        получает данные о выбранном инструменте из файла SharedPreferences tool_pref по ключу "save_key_tool"*/

        //теперь присвоим значения с файла нашим переменным, пока они еще не созданы, появятся в процессе игры.
        x2 = x2x5x10x50_pref.getInt("save_key_x2", 0);
        x5 = x2x5x10x50_pref.getInt("save_key_x5", 0);
        x10 = x2x5x10x50_pref.getInt("save_key_x10", 0);
        x50 = x2x5x10x50_pref.getInt("save_key_x50", 0);
        //теперь присвоим значения с файла нашей переменной, пока они еще не созданы, появятся в процессе игры.

        score_gold = gold_pref.getInt("save_key_gold", 0); /*здесь счетчик золотых монет score_gold получает данные от переменной gold_pref,
        которая получает данные о количестве золотых монет, из файла Gold_pref по ключу save_key_gold и уходят на строку 83*/

/*x2 - это переменная, которая будет содержать значение, полученное из файла настроек (SharedPreferences).
x2x5x10x50_pref - это объект класса SharedPreferences, который был получен или создан с использованием метода getSharedPreferences
 для работы с файлом настроек с именем "X2X5X10X50_pref".
.getInt("save_key_x50", 0) - это вызов метода getInt для получения значения из файла настроек. В данном случае:
"save_key_x50" - это ключ (key), по которому мы ищем значение в файле настроек. Ключи используются для идентификации значений.
0 - это значение по умолчанию, которое будет возвращено, если ключ не найден.
В данном случае, если значение с ключом "save_key_x50" не найдено, то x50 будет установлено в 0.
 Если значение с ключом найдено, оно будет присвоено переменной x50.*/

        //пишем код кнопки
        MaterialButton btn_back_arrow = findViewById(R.id.btn_back_arrow);
        //добавили обработчик клика по кнопке btn_back_arrow (карточка cardview.widget)
        btn_back_arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Shop.this, MainActivity.class);
                startActivity(intent);
                finish(); //finish(); - закрывает после себя прежнее activity
            }
        });

        TextView gold_txt = findViewById(R.id.gold_txt);
        //было раскоментировано. Выведено на экран количество золотых монет. Данные берутся со строки 57.
        gold_txt.setText(getString(R.string.txt_gold) + "" + score_gold);

        FloatingActionButton goto_shop = findViewById(R.id.goto_shop);//поиск
        goto_shop.setOnClickListener(new View.OnClickListener() {           //создали обработчик события
            @Override
            public void onClick(View v) {
                //здесь будет выполняться переход на другое Activity

            }
        });

        CardView tool_a = findViewById(R.id.tool_a);
        tool_a.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //здесь будет условие
                /*btn_count_cv=1;
                innerClass.styleCardView(); проверка отработки стиля карты*/
                if (btn_count_cv == 1) {
                    Toast.makeText(Shop.this, R.string.selected, Toast.LENGTH_SHORT).show();
                } else {
                    //показывается сообщение tost
                    SharedPreferences.Editor editor_tool = tool_pref.edit();
                    editor_tool.putInt("save_key_tool", 1);
                    editor_tool.apply();
                    btn_count_cv = tool_pref.getInt("save_key_tool", 1);

                    innerClass.styleCardView();
                }
            }
        });

        CardView tool_b = findViewById(R.id.tool_b);
        tool_b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (btn_count_cv == 2) {
                    Toast.makeText(Shop.this, R.string.selected, Toast.LENGTH_SHORT).show();
                } else {
                    //показывается сообщение tost
                    if (x2 == 0 & score_gold >= 10000) {
                        score_gold = score_gold - 10000;
                        gold_txt.setText(getString(R.string.txt_gold) + "" + score_gold);
                        SharedPreferences.Editor editor_gold = gold_pref.edit();
                        editor_gold.putInt("save_key_gold", score_gold);
                        editor_gold.apply();

                        SharedPreferences.Editor editor_x2 = x2x5x10x50_pref.edit();
                        editor_x2.putInt("save_key_x2", 1);
                        editor_x2.apply();
                        x2 = x2x5x10x50_pref.getInt("save_key_x2", 0);
                        SharedPreferences.Editor editor_tool = tool_pref.edit();
                        editor_tool.putInt("save_key_tool", 2);
                        editor_tool.apply();
                        btn_count_cv = tool_pref.getInt("save_key_tool", 2);
                        innerClass.styleCardView();
                    }

                    innerClass.styleCardView();
                }
                if (x2 == 1) {
                    SharedPreferences.Editor editor_tool = tool_pref.edit();
                    editor_tool.putInt("save_key_tool", 2);
                    editor_tool.apply();
                    btn_count_cv = tool_pref.getInt("save_key_tool", 2);
                    innerClass.styleCardView();

                }
            }
        });

        CardView tool_c = findViewById(R.id.tool_c);
        tool_c.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (btn_count_cv == 5) {
                    Toast.makeText(Shop.this, R.string.selected, Toast.LENGTH_SHORT).show();
                } else {
                    //показывается сообщение tost
                    if (x5 == 0 & score_gold >= 60000) {
                        score_gold = score_gold - 60000;
                        gold_txt.setText(getString(R.string.txt_gold) + "" + score_gold);
                        SharedPreferences.Editor editor_gold = gold_pref.edit();
                        editor_gold.putInt("save_key_gold", score_gold);
                        editor_gold.apply();

                        SharedPreferences.Editor editor_x5 = x2x5x10x50_pref.edit();
                        editor_x5.putInt("save_key_x5", 1);
                        editor_x5.apply();
                        x5 = x2x5x10x50_pref.getInt("save_key_x5", 0);
                        SharedPreferences.Editor editor_tool = tool_pref.edit();
                        editor_tool.putInt("save_key_tool", 5);
                        editor_tool.apply();
                        btn_count_cv = tool_pref.getInt("save_key_tool", 5);
                        innerClass.styleCardView();
                    }

                    innerClass.styleCardView();
                }
                if (x5 == 1) {
                    SharedPreferences.Editor editor_tool = tool_pref.edit();
                    editor_tool.putInt("save_key_tool", 5);
                    editor_tool.apply();
                    btn_count_cv = tool_pref.getInt("save_key_tool", 5);
                    innerClass.styleCardView();

                }
            }
        });

        CardView tool_d = findViewById(R.id.tool_d);
        tool_d.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (btn_count_cv == 10) {
                    Toast.makeText(Shop.this, R.string.selected, Toast.LENGTH_SHORT).show();
                } else {
                    //показывается сообщение tost
                    if (x10 == 0 & score_gold >= 140000) {
                        score_gold = score_gold - 140000;
                        gold_txt.setText(getString(R.string.txt_gold) + "" + score_gold);
                        SharedPreferences.Editor editor_gold = gold_pref.edit();
                        editor_gold.putInt("save_key_gold", score_gold);
                        editor_gold.apply();

                        SharedPreferences.Editor editor_x10 = x2x5x10x50_pref.edit();
                        editor_x10.putInt("save_key_x10", 1);
                        editor_x10.apply();
                        x10 = x2x5x10x50_pref.getInt("save_key_x10", 0);
                        SharedPreferences.Editor editor_tool = tool_pref.edit();
                        editor_tool.putInt("save_key_tool", 10);
                        editor_tool.apply();
                        btn_count_cv = tool_pref.getInt("save_key_tool", 10);
                        innerClass.styleCardView();
                    }

                    innerClass.styleCardView();
                }
                if (x10 == 1) {
                    SharedPreferences.Editor editor_tool = tool_pref.edit();
                    editor_tool.putInt("save_key_tool", 10);
                    editor_tool.apply();
                    btn_count_cv = tool_pref.getInt("save_key_tool", 10);
                    innerClass.styleCardView();

                }
            }
        });

        CardView tool_e = findViewById(R.id.tool_e);
        tool_e.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (btn_count_cv == 50) {
                    Toast.makeText(Shop.this, R.string.selected, Toast.LENGTH_SHORT).show();
                } else {
                    //показывается сообщение tost
                    if (x50 == 0 & score_gold >= 250000) {
                        score_gold = score_gold - 250000;
                        gold_txt.setText(getString(R.string.txt_gold) + "" + score_gold);
                        SharedPreferences.Editor editor_gold = gold_pref.edit();
                        editor_gold.putInt("save_key_gold", score_gold);
                        editor_gold.apply();

                        SharedPreferences.Editor editor_x50 = x2x5x10x50_pref.edit();
                        editor_x50.putInt("save_key_x50", 1);
                        editor_x50.apply();
                        x50 = x2x5x10x50_pref.getInt("save_key_x50", 0);
                        SharedPreferences.Editor editor_tool = tool_pref.edit();
                        editor_tool.putInt("save_key_tool", 50);
                        editor_tool.apply();
                        btn_count_cv = tool_pref.getInt("save_key_tool", 50);
                        innerClass.styleCardView();
                    }

                    innerClass.styleCardView();
                }
                if (x50 == 1) {
                    SharedPreferences.Editor editor_tool = tool_pref.edit();
                    editor_tool.putInt("save_key_tool", 50);
                    editor_tool.apply();
                    btn_count_cv = tool_pref.getInt("save_key_tool", 50);
                    innerClass.styleCardView();

                }
            }
        });

    }

    //внутренний класс - начало

    //можно теперь работать с контейнерами через переменные, у каждого контейнера своя переменная.
    class InnerClass {
        LinearLayout tool_a_style = findViewById(R.id.tool_a_style);
        LinearLayout tool_b_style = findViewById(R.id.tool_b_style);
        LinearLayout tool_c_style = findViewById(R.id.tool_c_style);
        LinearLayout tool_d_style = findViewById(R.id.tool_d_style);
        LinearLayout tool_e_style = findViewById(R.id.tool_e_style);

        //также пропишем путь к переменным tool_txt_abced в TextView

        TextView tool_txt_a = findViewById(R.id.tool_txt_a);
        TextView tool_txt_b = findViewById(R.id.tool_txt_b);
        TextView tool_txt_c = findViewById(R.id.tool_txt_c);
        TextView tool_txt_d = findViewById(R.id.tool_txt_d);
        TextView tool_txt_e = findViewById(R.id.tool_txt_e);

        TextView selected_a = findViewById(R.id.selected_a);
        TextView selected_b = findViewById(R.id.selected_b);
        TextView selected_c = findViewById(R.id.selected_c);
        TextView selected_d = findViewById(R.id.selected_d);
        TextView selected_e = findViewById(R.id.selected_e);

        //создаем метод, который производит изменения внешнего вида кнопок в зависимости от выбранного инструмента. -начало

        public void styleCardView() {
//оператор switch сравнивать значения переменной с другими значениями переменных
            switch (btn_count_cv) {
                //case - сравнение
                case 1:
                    tool_a_style.setBackgroundResource(R.drawable.style_cardview_selected);
                    tool_b_style.setBackgroundResource(R.drawable.style_cardview5);
                    tool_c_style.setBackgroundResource(R.drawable.style_cardview5);
                    tool_d_style.setBackgroundResource(R.drawable.style_cardview5);
                    tool_e_style.setBackgroundResource(R.drawable.style_cardview5);

                    tool_txt_a.setTextColor(getResources().getColor(R.color.black, null));//null - не использовать тему телефона, для неискажения цвета
                    tool_txt_b.setTextColor(getResources().getColor(R.color.white, null));
                    tool_txt_c.setTextColor(getResources().getColor(R.color.white, null));
                    tool_txt_d.setTextColor(getResources().getColor(R.color.white, null));
                    tool_txt_e.setTextColor(getResources().getColor(R.color.white, null));

                    selected_a.setVisibility(View.VISIBLE); //сделать видимым


                    if (x2 == 1) { //если инструмент куплен, но не выбран, то прячем надпись selected у selected_b
                        selected_b.setVisibility(View.INVISIBLE);
                    }
                    if (x5 == 1) {
                        selected_c.setVisibility(View.INVISIBLE);
                    }
                    if (x10 == 1) {
                        selected_d.setVisibility(View.INVISIBLE);
                    }
                    if (x50 == 1) {
                        selected_e.setVisibility(View.INVISIBLE);
                    }


                    break; //выход с оператора switch
                case 2:
                    tool_a_style.setBackgroundResource(R.drawable.style_cardview5);
                    tool_b_style.setBackgroundResource(R.drawable.style_cardview_selected);
                    tool_c_style.setBackgroundResource(R.drawable.style_cardview5);
                    tool_d_style.setBackgroundResource(R.drawable.style_cardview5);
                    tool_e_style.setBackgroundResource(R.drawable.style_cardview5);
                    tool_txt_a.setTextColor(getResources().getColor(R.color.white, null));
                    tool_txt_b.setTextColor(getResources().getColor(R.color.black, null));
                    tool_txt_c.setTextColor(getResources().getColor(R.color.white, null));
                    tool_txt_d.setTextColor(getResources().getColor(R.color.white, null));
                    tool_txt_e.setTextColor(getResources().getColor(R.color.white, null));
                    selected_a.setVisibility(View.INVISIBLE); //сделать видимым
                    selected_b.setText(R.string.selected);
                    selected_b.setVisibility(View.VISIBLE);
                    selected_b.setTextColor(getResources().getColor(R.color.teal_700, null));


                    if (x5 == 1) {
                        selected_c.setVisibility(View.INVISIBLE);
                    }
                    if (x10 == 1) {
                        selected_d.setVisibility(View.INVISIBLE);
                    }
                    if (x50 == 1) {
                        selected_e.setVisibility(View.INVISIBLE);
                    }

                    break; //выход с оператора switch
                case 5:
                    tool_a_style.setBackgroundResource(R.drawable.style_cardview5);
                    tool_b_style.setBackgroundResource(R.drawable.style_cardview5);
                    tool_c_style.setBackgroundResource(R.drawable.style_cardview_selected);
                    tool_d_style.setBackgroundResource(R.drawable.style_cardview5);
                    tool_e_style.setBackgroundResource(R.drawable.style_cardview5);
                    tool_txt_a.setTextColor(getResources().getColor(R.color.white, null));
                    tool_txt_b.setTextColor(getResources().getColor(R.color.white, null));
                    tool_txt_c.setTextColor(getResources().getColor(R.color.black, null));
                    tool_txt_d.setTextColor(getResources().getColor(R.color.white, null));
                    tool_txt_e.setTextColor(getResources().getColor(R.color.white, null));
                    selected_a.setVisibility(View.INVISIBLE); //сделать видимым
                    selected_c.setText(R.string.selected);
                    selected_c.setVisibility(View.VISIBLE);
                    selected_c.setTextColor(getResources().getColor(R.color.teal_700, null));


                    if (x2 == 1) { //если инструмент куплен, но не выбран, то прячем надпись selected у selected_b
                        selected_b.setVisibility(View.INVISIBLE);
                    }
                    if (x10 == 1) {
                        selected_d.setVisibility(View.INVISIBLE);
                    }
                    if (x50 == 1) {
                        selected_e.setVisibility(View.INVISIBLE);
                    }

                    break; //выход с оператора switch
                case 10:
                    tool_a_style.setBackgroundResource(R.drawable.style_cardview5);
                    tool_b_style.setBackgroundResource(R.drawable.style_cardview5);
                    tool_c_style.setBackgroundResource(R.drawable.style_cardview5);
                    tool_d_style.setBackgroundResource(R.drawable.style_cardview_selected);
                    tool_e_style.setBackgroundResource(R.drawable.style_cardview5);
                    tool_txt_a.setTextColor(getResources().getColor(R.color.white, null));
                    tool_txt_b.setTextColor(getResources().getColor(R.color.white, null));
                    tool_txt_c.setTextColor(getResources().getColor(R.color.white, null));
                    tool_txt_d.setTextColor(getResources().getColor(R.color.black, null));
                    tool_txt_e.setTextColor(getResources().getColor(R.color.white, null));
                    selected_a.setVisibility(View.INVISIBLE); //сделать видимым
                    selected_d.setText(R.string.selected);
                    selected_d.setVisibility(View.VISIBLE);
                    selected_d.setTextColor(getResources().getColor(R.color.teal_700, null));

                    if (x2 == 1) { //если инструмент куплен, но не выбран, то прячем надпись selected у selected_b
                        selected_b.setVisibility(View.INVISIBLE);
                    }
                    if (x5 == 1) {
                        selected_c.setVisibility(View.INVISIBLE);
                    }
                    if (x50 == 1) {
                        selected_e.setVisibility(View.INVISIBLE);
                    }


                    break; //выход с оператора switch
                case 50:
                    tool_a_style.setBackgroundResource(R.drawable.style_cardview5);
                    tool_b_style.setBackgroundResource(R.drawable.style_cardview5);
                    tool_c_style.setBackgroundResource(R.drawable.style_cardview5);
                    tool_d_style.setBackgroundResource(R.drawable.style_cardview5);
                    tool_e_style.setBackgroundResource(R.drawable.style_cardview_selected);
                    tool_txt_a.setTextColor(getResources().getColor(R.color.white, null));
                    tool_txt_b.setTextColor(getResources().getColor(R.color.white, null));
                    tool_txt_c.setTextColor(getResources().getColor(R.color.white, null));
                    tool_txt_d.setTextColor(getResources().getColor(R.color.white, null));
                    tool_txt_e.setTextColor(getResources().getColor(R.color.black, null));
                    selected_a.setVisibility(View.INVISIBLE); //сделать видимым
                    selected_e.setText(R.string.selected);
                    selected_e.setVisibility(View.VISIBLE);
                    selected_e.setTextColor(getResources().getColor(R.color.teal_700, null));

                    if (x2 == 1) { //если инструмент куплен, но не выбран, то прячем надпись selected у selected_d
                        selected_b.setVisibility(View.INVISIBLE);
                    }
                    if (x5 == 1) {
                        selected_c.setVisibility(View.INVISIBLE);
                    }
                    if (x10 == 1) {
                        selected_d.setVisibility(View.INVISIBLE);
                    }


                    break; //выход с оператора switch
                default:
                    break;

            }
        }


        //создаем метод, который производит изменения внешнего вида кнопок в зависимости от выбранного инструмента. -конец
    }
    //внутренний класс - конец


}