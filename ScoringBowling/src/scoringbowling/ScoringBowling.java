package scoringbowling;

import BusinessLogic.Controller;
import java.io.IOException;

public class ScoringBowling {

    public static Controller m_Controller = new Controller();

    public static void main(String[] args) throws IOException {
        m_Controller.PlayGame();
    }
}
