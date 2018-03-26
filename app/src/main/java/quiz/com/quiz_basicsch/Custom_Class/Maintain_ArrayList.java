package quiz.com.quiz_basicsch.Custom_Class;

import java.util.ArrayList;

import quiz.com.quiz_basicsch.Model.Question_Model;

/**
 * Created by swordpc07 on 3/22/2018.
 */

public class Maintain_ArrayList {

    static Maintain_ArrayList maintain_arrayList;

    public static ArrayList<Question_Model> allQuestions_ArrayList;
    static ArrayList<Question_Model> user_Questions_ArrayList;

    /*private static Maintain_ArrayList getInstance(){

        if(maintain_arrayList == null)
            maintain_arrayList = new Maintain_ArrayList();

        return maintain_arrayList;
    }*/

    public static ArrayList<Question_Model> getAllQuestionList(){
        return allQuestions_ArrayList;
    }

    public static void saveAllQuestionList(ArrayList<Question_Model> list){
        allQuestions_ArrayList = list;
    }

}
