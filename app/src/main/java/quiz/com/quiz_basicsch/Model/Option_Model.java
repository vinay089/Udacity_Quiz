package quiz.com.quiz_basicsch.Model;

/**
 * Created by Vinay Gupta on 24-03-2018.
 */

public class Option_Model {

    private static Option_Model optionModel = new Option_Model();

    private int selectedPosition= -1;
    private Option_Model(){

    }

    public static Option_Model getInstance(){
        if(optionModel == null)
            optionModel = new Option_Model();

        return optionModel;
    }

    public int getSelectedPosition() {
        return selectedPosition;
    }

    public void setSelectedPosition(int selectedPosition) {
        this.selectedPosition = selectedPosition;
    }

}
