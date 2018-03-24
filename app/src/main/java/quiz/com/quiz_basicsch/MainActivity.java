package quiz.com.quiz_basicsch;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import quiz.com.quiz_basicsch.Interfaces.Question_Listener;
import quiz.com.quiz_basicsch.Model.Question_Model;

public class MainActivity extends AppCompatActivity {

    Unbinder unbinder;
    @BindView(R.id.et_name)
    EditText name_edittext;

    private boolean isNameEmpty(){

        if(name_edittext.getText().toString().trim().isEmpty())
            return true;
        else
            return false;

    }

    final ButterKnife.Action<EditText> FOR_FILL_DETAILS = new ButterKnife.Action<EditText>() {
        @Override
        public void apply(@NonNull EditText view, int index) {
            view.setError("Empty nick name");
        }
    };

    private void saveNameIntoSharedPrefrences(final String name){

        SharedPreferences.Editor editor = getSharedPreferences("Prefrences", MODE_PRIVATE).edit();
        editor.putString("name", name);
        editor.commit();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        unbinder = ButterKnife.bind(this);

        if(getSharedPreferences("Prefrences", MODE_PRIVATE).getString("name", null) != null){

            new LoadQuestionFromFile(this, question_listener);
        }

    }

    @OnClick(R.id.tv_next)
    void OnClick(View view){

        if(isNameEmpty()){
            ButterKnife.apply(name_edittext, FOR_FILL_DETAILS);
            return;
        }

        final String name = name_edittext.getText().toString().trim();
        new LoadQuestionFromFile(this, question_listener);
        new Thread(){
            @Override
            public void run(){

                saveNameIntoSharedPrefrences(name);
            }
        }.start();

    }

    Question_Listener question_listener = new Question_Listener() {
        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        @Override
        public void questionLoad(ArrayList<Question_Model> arrayList) {


            Intent intent = new Intent(MainActivity.this, MainQuestionActivity.class);
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
