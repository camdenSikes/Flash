package hu.ait.onetwelve.flash.model;

/**
 * Created by Brendan on 12/11/16.
 */

public class ViewCardsData {

    private static ViewCardsData instance = null;

    private ViewCardsData() {
    }

    public static ViewCardsData getInstance() {
        if (instance == null) {
            instance = new ViewCardsData();
        }
        return instance;
    }

    private int listPos;
    private int score;
    private boolean complete;

    public static void setInstance(ViewCardsData instance) {
        ViewCardsData.instance = instance;
    }

    public int getListPos() {
        return listPos;
    }

    public void setListPos(int listPos) {
        this.listPos = listPos;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public boolean isComplete() {
        return complete;
    }

    public void setComplete(boolean complete) {
        this.complete = complete;
    }

    public void incrementListPos() {
        listPos++;
    }

    public void incrementScore() {
        score++;
    }
}
