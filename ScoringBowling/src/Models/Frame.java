package Models;

import java.util.ArrayList;

public class Frame {

    public int score = 0;
    public ArrayList<Try> tries = new ArrayList<>();
    public boolean strike = false;
    public boolean spare = false;
    public int totalScore = 0;
    public boolean done = false;

    public ArrayList<Try> getTries() {
        return tries;
    }

    public void setTries(ArrayList<Try> tries) {
        this.tries = tries;
    }

}
