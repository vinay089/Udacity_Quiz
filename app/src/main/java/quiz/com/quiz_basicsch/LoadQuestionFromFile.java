package quiz.com.quiz_basicsch;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.ref.WeakReference;
import java.util.ArrayList;

import quiz.com.quiz_basicsch.Custom_Class.Maintain_ArrayList;
import quiz.com.quiz_basicsch.Interfaces.Question_Listener;
import quiz.com.quiz_basicsch.Model.Question_Model;

/**
 * Created by swordpc07 on 3/22/2018.
 */

public class LoadQuestionFromFile {


    WeakReference<Context> context;
    Question_Listener question_listener;

    public LoadQuestionFromFile(Context _context, Question_Listener question_listener) {

        context = new WeakReference<Context>(_context);
        this.question_listener = question_listener;

        //run asynctaksk to lad all questions from file.
        LoadQuestion_AsyncTask loadQuestion_asyncTask = new LoadQuestion_AsyncTask();
        loadQuestion_asyncTask.execute();
    }



    public class LoadQuestion_AsyncTask extends AsyncTask<Void, Void, Void>{

        @Override
        protected Void doInBackground(Void... voids) {
            InputStream inputStream = context.get().getResources().openRawResource(R.raw.questions);
            ArrayList<Question_Model> question_Arraylist = new ArrayList<Question_Model>();

            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            try {
                String csvLine;
                while ((csvLine = reader.readLine()) != null) {

                    Question_Model question_get_set = new Question_Model();

                    String[] row = csvLine.split(",");

                    question_get_set.setId(Integer.parseInt(row[0]));
                    question_get_set.setQuestion(row[1]);
                    question_get_set.setOption1(row[2]);
                    question_get_set.setOption2(row[3]);
                    question_get_set.setOption3(row[4]);
                    question_get_set.setOption4(row[5]);
                    question_get_set.setAnswer(row[6]);


                    question_Arraylist.add(question_get_set);

                    Log.d("LoadQuestionFromFIle", question_get_set.getQuestion());

                }

                //call listner complete method
                question_listener.questionLoad(question_Arraylist);

                Maintain_ArrayList maintain_arrayList = new Maintain_ArrayList();
                maintain_arrayList.saveAllQuestionList(question_Arraylist);
            } catch (IOException ex) {
                //error occur while loading CSV file.
                question_listener.questionError(ex);
                throw new RuntimeException("Error in reading CSV file: " + ex);
            }

            finally {

                try {
                    inputStream.close();
                }

                catch (IOException e) {
                    e.printStackTrace();
                }

            }
            return null;
        }
    }
}
