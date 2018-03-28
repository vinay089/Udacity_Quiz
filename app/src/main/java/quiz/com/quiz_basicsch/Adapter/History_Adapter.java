package quiz.com.quiz_basicsch.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

import quiz.com.quiz_basicsch.Model.Question_Model;
import quiz.com.quiz_basicsch.R;

/**
 * Created by Vinay Gupta on 24-03-2018.
 */

public class History_Adapter extends BaseAdapter {

    ArrayList<Question_Model> list;
    WeakReference<Context> _context;
    public History_Adapter(Context context, ArrayList<Question_Model> list){
        _context = new WeakReference<Context>(context);
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        Holder holder = null;

        if(view == null){

            LayoutInflater inflater = (LayoutInflater) _context.get().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            view =inflater.inflate(R.layout.history_layout, null);
            holder = new Holder(view);
            view.setTag(holder);
        } else {
            holder = (Holder) view.getTag();
        }

        Question_Model question_model = list.get(i);

        holder.tv_question.setText(question_model.getQuestion());
        holder.tv_useranswer.setText(question_model.getUser_answer());
        holder.tv_correcctanswer.setText(question_model.getAnswer());
        holder.tv_timer_duration.setText(""+question_model.getTimeTaken()+ " sec.");

        if(question_model.getUser_answer().trim().equalsIgnoreCase(question_model.getAnswer().trim()))
            holder.iv_user_choice.setImageResource(R.drawable.checked);
        else
            holder.iv_user_choice.setImageResource(R.drawable.error);

        return view;
    }

    class Holder{
        TextView tv_question, tv_useranswer, tv_correcctanswer, tv_timer_duration;
        ImageView iv_user_choice;

        public Holder(View view){
            tv_question = view.findViewById(R.id.tv_user_uestion);
            tv_useranswer = view.findViewById(R.id.tv_user_answer);
            tv_correcctanswer = view.findViewById(R.id.tv_correcct_answer);
            tv_timer_duration = view.findViewById(R.id.tv_time_taken_by_user);

            iv_user_choice = view.findViewById(R.id.iv_user_choice_image);

        }
    }
}
