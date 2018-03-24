package quiz.com.quiz_basicsch.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.support.v7.widget.AppCompatRadioButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.lang.ref.WeakReference;
import java.util.List;

import quiz.com.quiz_basicsch.Model.Option_Model;
import quiz.com.quiz_basicsch.R;

/**
 * Created by Vinay Gupta on 24-03-2018.
 */

public class Option_Adapter extends BaseAdapter {

    WeakReference<Context> context;
    List<String> list;
    private int selectedPosition = -1;

    Option_Adapter(Context _context, List<String> list){
        this.list = list;
        context = new WeakReference<Context> (_context);
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

    @SuppressLint("RestrictedApi")
    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        Holder holder = null;

        if(view == null){

            LayoutInflater inflater = (LayoutInflater) context.get().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            view =inflater.inflate(R.layout.question_option_layout, null);
            holder = new Holder(view);
            view.setTag(holder);
        } else {
            holder = (Holder) view.getTag();
        }

        String option = list.get(i);

        holder.tv_optiion.setText(option);

        ColorStateList colorStateList = new ColorStateList(
                new int[][]{
                        new int[]{-android.R.attr.state_checked},
                        new int[]{android.R.attr.state_checked}
                },
                new int[]{ Color.BLACK , Color.WHITE, }
        );
        holder.otion_rb.setSupportButtonTintList(colorStateList);


        if(selectedPosition == -1) {
            holder.otion_rb.setChecked(i==getCount());
        }
        else{
            holder.otion_rb.setChecked(selectedPosition == i);
        }

        holder.otion_rb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getOption_ModelInstance().setSelectedPosition(i);

                selectedPosition = i;
                notifyDataSetChanged();
            }
        });

        holder.tv_optiion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                getOption_ModelInstance().setSelectedPosition(i);

                selectedPosition = i;
                notifyDataSetChanged();
            }
        });
        return view;
    }

    class Holder{
        TextView tv_optiion;
        AppCompatRadioButton otion_rb;

        public Holder(View view){
            tv_optiion = view.findViewById(R.id.tv_option);
            otion_rb = view.findViewById(R.id.option_radiobutton);

        }
    }

    private Option_Model getOption_ModelInstance(){
        return Option_Model.getInstance();
    }
}
