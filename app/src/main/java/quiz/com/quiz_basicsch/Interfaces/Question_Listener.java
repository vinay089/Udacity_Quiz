package quiz.com.quiz_basicsch.Interfaces;

import java.util.ArrayList;

import quiz.com.quiz_basicsch.Model.Question_Model;

/**
 * Created by swordpc07 on 3/22/2018.
 */

public interface Question_Listener {

    void questionLoad(ArrayList<Question_Model> arrayList);

    void questionError(Exception ex);
}
