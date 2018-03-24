package quiz.com.quiz_basicsch;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_after__complete_test);

        ButterKnife.bind(this);

        String name = getSharedPreferences("Prefrences", MODE_PRIVATE).getString("name", "");

        getSupportActionBar().setTitle("Hey, "+name);

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
