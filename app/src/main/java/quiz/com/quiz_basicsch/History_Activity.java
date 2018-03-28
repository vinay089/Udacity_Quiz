package quiz.com.quiz_basicsch;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import quiz.com.quiz_basicsch.Adapter.History_Adapter;
import quiz.com.quiz_basicsch.Custom_Class.Maintain_ArrayList;
import quiz.com.quiz_basicsch.Model.Question_Model;

public class History_Activity extends AppCompatActivity {

    @BindView(R.id.lv_history)
    ListView Lv_History;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_);

        ButterKnife.bind(this);

        String name = getSharedPreferences("Prefrences", MODE_PRIVATE).getString("name", "");

        getSupportActionBar().setTitle("Hey, "+name);

        ArrayList<Question_Model> final_list = getList();

        History_Adapter adapter = new History_Adapter(this, final_list);
        Lv_History.setAdapter(adapter);

    }

    private ArrayList<Question_Model> getList(){
        ArrayList<Integer> id_list = MainQuestionActivity.user_question_id;
//        ArrayList<Integer> user_ans = MainQuestionActivity.user_answers;
        ArrayList<Integer> user_ans = new ArrayList<>();

        ArrayList<Question_Model> final_list = new ArrayList<>();

        String answer= "";
        for(int i=0;i<id_list.size();i++){

            Question_Model question_model = Maintain_ArrayList.allQuestions_ArrayList.get(id_list.get(i));
            final_list.add(question_model);
        }

        return final_list;
    }

}
