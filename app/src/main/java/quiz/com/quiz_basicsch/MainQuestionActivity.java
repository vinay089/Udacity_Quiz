package quiz.com.quiz_basicsch;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemLongClick;
import butterknife.Unbinder;
import quiz.com.quiz_basicsch.Adapter.Option_Adapter;
import quiz.com.quiz_basicsch.Custom_Class.Maintain_ArrayList;
import quiz.com.quiz_basicsch.Interfaces.Time_Complete_Listener;
import quiz.com.quiz_basicsch.Model.Option_Model;
import quiz.com.quiz_basicsch.Model.Question_Model;

public class MainQuestionActivity extends AppCompatActivity {

    @BindView(R.id.listview)
    ListView Option_Lv;

    @BindViews({R.id.tv_question, R.id.tv_skip_question})
    List<TextView> question_tv;

    @BindView(R.id.tv_timer_question)
    ProgressBar progressBarCircular;

    Unbinder unbinder;

    Option_Adapter customAdapter;
    ArrayList<Question_Model> allQuestionList;

    static ArrayList<Integer> user_question_id = new ArrayList<>();
    CountDownTimer countDownTimer;
    long timeDuraton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_question);

        unbinder = ButterKnife.bind(this);

        String name = getSharedPreferences("Prefrences", MODE_PRIVATE).getString("name", "");

        getSupportActionBar().setTitle("Hey, "+name);

        Maintain_ArrayList maintain_arrayList = new Maintain_ArrayList();
        allQuestionList = maintain_arrayList.getAllQuestionList();

        loadQuestionToShow();

        /*Option_Lv.setOnItemClickedListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(MainQuestionActivity.this, "You clicked: " *//*+ adapter.getItem(position)*//*, Toast.LENGTH_SHORT).show();
            }
        });*/
    }

    @OnItemLongClick(R.id.listview)
    boolean onItemClick(AdapterView<?> parent, int position) {

        Toast.makeText(MainQuestionActivity.this, "You clicked: " /*+ adapter.getItem(position)*/, Toast.LENGTH_SHORT).show();

        return false;
    }

    @OnClick(R.id.tv_next_question)
    void onClick(){

        long duration = 30 - timeDuraton;
        countDownTimer.cancel();
        progressBarCircular.setProgress(0);
        int pos = Option_Model.getInstance().getSelectedPosition();

        if(pos == -1){
            Toast.makeText(MainQuestionActivity.this, "Select an Option.", Toast.LENGTH_LONG).show();
            return;
        }

        int ques = user_question_id.get(user_question_id.size() - 1);

        Question_Model question_model = allQuestionList.get(ques);

        String answer = "";
        switch (pos){
            case 0:
                answer = question_model.getOption1();
                break;

            case 1:
                answer = question_model.getOption2();
                break;

            case 2:
                answer = question_model.getOption3();
                break;

            case 3:
                answer = question_model.getOption4();
                break;

        }
        question_model.setUser_answer(answer);
        question_model.setTimeTaken((int) duration);
        Log.d("time_quest"+ques, ""+duration);
        allQuestionList.set(pos, question_model);

//        user_answers.add(pos);
        Option_Model.getInstance().setSelectedPosition(-1);

        if(user_question_id.size() == 5){

            Maintain_ArrayList.allQuestions_ArrayList = allQuestionList;

            Intent intent = new Intent(this, After_Complete_test.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            return;
        }

        loadQuestionToShow();
    }

    private void loadQuestionToShow(){

        new Thread(){
            @Override
            public void run(){
                int randomno = showRandomInteger(1, 20, new Random());

                if(user_question_id.contains(randomno)){
                    loadQuestionToShow();
                    return;
                }

                Question_Model question_model = allQuestionList.get(randomno-1);

                user_question_id.add(randomno-1);

                final String Question = question_model.getQuestion();
                String option_1 = question_model.getOption1();
                String option_2 = question_model.getOption2();
                String option_3 = question_model.getOption3();
                String option_4 = question_model.getOption4();

                final List<String> list = new ArrayList<>();
                list.add(option_1);
                list.add(option_2);
                list.add(option_3);
                list.add(option_4);


                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        question_tv.get(0).setText(Question);
                        customAdapter = new Option_Adapter(MainQuestionActivity.this,
                                list);
                        Option_Lv.setAdapter(customAdapter);

                        countDownTimer = new CountDownTimer(30*1000, 1000) {

                            public void onTick(long millisUntilFinished) {

                                /*timeDuraton = (millisUntilFinished)/1000;
                                long sec = timeDuraton %60;
                                question_tv.get(1).setText(String.format("%02d", sec));*/

                                timeDuraton = (millisUntilFinished)/1000;
                                long seconds = timeDuraton %60;
                                int barVal= (100) - ((int)(seconds/60*100)+(int)(seconds%60));

                                progressBarCircular.setProgress((int) (barVal));

                            }

                            public void onFinish() {
                                /*Option_Lv.setEnabled(false);
                                question_tv.get(1).setText("Time Out!");
                                time_complete_listener.timeComplete();*/
                                time_complete_listener.timeComplete();

                            }

                        };

                        countDownTimer.start();
                    }
                });

            }
        }.start();
    }

    @OnClick(R.id.tv_skip_question)
    void onSkipClick(View v){

        skipQuestionOnTomeCompleteOtButton();
    }

    private static int showRandomInteger(int aStart, int aEnd, Random aRandom){
        if (aStart > aEnd) {
            throw new IllegalArgumentException("Start cannot exceed End.");
        }
        //get the range, casting to long to avoid overflow problems
        long range = (long)aEnd - (long)aStart + 1;
        // compute a fraction of the range, 0 <= frac < range
        long fraction = (long)(range * aRandom.nextDouble());
        int randomNumber =  (int)(fraction + aStart);
        Log.d("Generated : " , String.valueOf(randomNumber));
        return randomNumber;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item){

        switch(item.getItemId()){

            case R.id.action_new_user :
                SharedPreferences prefs = getSharedPreferences("Prefrences", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = prefs.edit();
                editor.clear().commit();


                startActivity(new Intent(this, MainActivity.class));
                finish();

                break;

            case R.id.action_restart :

//                user_answers.clear();
                user_question_id.clear();

                Maintain_ArrayList.allQuestions_ArrayList.clear();
                startActivity(new Intent(this, MainActivity.class));
                finish();
                break;

        }
        return true;
    }

    Time_Complete_Listener time_complete_listener = new Time_Complete_Listener() {
        @Override
        public void timeComplete() {
//            loadQuestionToShow();
            skipQuestionOnTomeCompleteOtButton();
        }

        @Override
        public void onError() {

        }
    };

    private void skipQuestionOnTomeCompleteOtButton(){

        countDownTimer.cancel();
        int ques = user_question_id.get(user_question_id.size() - 1);

        Question_Model question_model = allQuestionList.get(ques);
        question_model.setUser_answer("n u l l");
        question_model.setTimeTaken(-1);

        allQuestionList.set(ques, question_model);

        loadQuestionToShow();
    }
}
