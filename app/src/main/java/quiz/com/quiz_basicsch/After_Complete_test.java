package quiz.com.quiz_basicsch;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.plattysoft.leonids.ParticleSystem;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import quiz.com.quiz_basicsch.Custom_Class.Maintain_ArrayList;
import quiz.com.quiz_basicsch.Interfaces.Question_Listener;
import quiz.com.quiz_basicsch.Model.Question_Model;

public class After_Complete_test extends AppCompatActivity {

    @BindView(R.id.tv_view_history)
    TextView viewHistory;

    @BindView(R.id.id_view)
    View el;

    @BindView(R.id.tv_correct_attempts)
    TextView Tv_Correct_Attempts;

    @BindView(R.id.tv_wrong_attempts)
    TextView Tv_wrong_attempts;

    @BindView(R.id.tv_message)
    TextView Tv_message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_after__complete_test);

        ButterKnife.bind(this);

        String name = getSharedPreferences("Prefrences", MODE_PRIVATE).getString("name", "");

        getSupportActionBar().setTitle("Hey, "+name);


        new Thread(){

            @Override
            public void run(){

                final int correct_attempts = getCorrectQuestionCount();
                final int wrong_attempts = 5 - correct_attempts;

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        Tv_Correct_Attempts.setText(""+correct_attempts);
                        Tv_wrong_attempts.setText(""+wrong_attempts);

                        switch (correct_attempts){
                            case 0:
                                Tv_message.setText("You may get better after re-attempt");
                                break;

                            case 1:
                                Tv_message.setText("You may get better after re-attempt");
                                break;

                            case 2:
                                Tv_message.setText("You may get better after re-attempt");
                                break;

                            case 3:
                                Tv_message.setText("Good");
                                break;

                            case 4:
                                Tv_message.setText("Very Good");
                                break;

                            case 5:
                                Tv_message.setText("Excellent");
                                break;
                        }
                    }
                });
            }
        }.start();


        new ParticleSystem(this, 80, R.drawable.ripple, 5000)
                .setSpeedModuleAndAngleRange(0f, 0.3f, 0, 0)
                .setRotationSpeed(144)
                .setAcceleration(0.00005f, 90)
                .emit(el, 8);

        new ParticleSystem(this, 80, R.drawable.rippleredred, 5000)
                .setSpeedModuleAndAngleRange(0f, 0.3f, 0, 0)
                .setRotationSpeed(144)
                .setAcceleration(0.00005f, 90)
                .emit(el, 8);

        new ParticleSystem(this, 80, R.drawable.rippleorange, 5000)
                .setSpeedModuleAndAngleRange(0f, 0.3f, 0, 0)
                .setRotationSpeed(144)
                .setAcceleration(0.00005f, 90)
                .emit(el, 8);



    }

    @OnClick({R.id.tv_view_history, R.id.tv_restart_test, R.id.tv_rename})
    void viewHistoryClick(View v){

        switch (v.getId()) {

            case R.id.tv_view_history:

                startActivity(new Intent(this, History_Activity.class));
                return;

            case R.id.tv_restart_test:
                Maintain_ArrayList.allQuestions_ArrayList.clear();

                MainQuestionActivity.user_answers.clear();
                MainQuestionActivity.user_question_id.clear();

//                new LoadQuestionFromFile(this, question_listener);
                Intent intent = new Intent(this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);

                return;

            case R.id.tv_rename:

                Maintain_ArrayList.allQuestions_ArrayList.clear();

                MainQuestionActivity.user_answers.clear();
                MainQuestionActivity.user_question_id.clear();

                SharedPreferences.Editor edit = getSharedPreferences("Prefrences", MODE_PRIVATE).edit();
                edit.putString("name", null);
//        edit.clear();
                edit.commit();
                MainQuestionActivity.user_answers.clear();
                MainQuestionActivity.user_question_id.clear();

                intent = new Intent(this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                return;
        }


    }

    private int getCorrectQuestionCount() {
        ArrayList<Integer> id_list = MainQuestionActivity.user_question_id;
        ArrayList<Integer> user_ans = MainQuestionActivity.user_answers;

        int correct_count = 0;

        ArrayList<Question_Model> final_list = new ArrayList<>();

        String answer = "";
        for (int i = 0; i < id_list.size(); i++) {

            Question_Model question_model = Maintain_ArrayList.allQuestions_ArrayList.get(id_list.get(i));

            switch (user_ans.get(i)) {
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

            if(question_model.getAnswer().trim().equalsIgnoreCase(answer.trim()))
                correct_count++;
        }
        return correct_count;

    }
    Question_Listener question_listener = new Question_Listener() {
        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        @Override
        public void questionLoad(ArrayList<Question_Model> arrayList) {


            Intent intent = new Intent(After_Complete_test.this, MainQuestionActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
//            startActivity(intent, makeSceneTransitionAnimation(MainActivity.this).toBundle());
//            overridePendingTransition( R.anim.slide_in, R.anim.slide_out );
        }

        @Override
        public void questionError(Exception ex) {

        }
    };
}
