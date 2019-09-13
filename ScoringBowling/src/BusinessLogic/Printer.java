package BusinessLogic;

import Models.Frame;
import Models.Try;
import java.util.ArrayList;

public class Printer {

    public void Print(ArrayList<Frame> frames) {
        PrintAllFrames(frames);
    }

    private void PrintAllFrames(ArrayList<Frame> frames) {
        for (Frame frame : frames) {
            System.out.print("#####     ");
        }
        System.out.println();
        for (Frame frame : frames) {
            System.out.print(" " + frame.totalScore + "       ");
        }
        System.out.println();
        for (Frame frame : frames) {
            for (Try currentTry : frame.getTries()) {
                System.out.print("## ");
            }
            System.out.print("    ");
        }
        System.out.println();
        for (Frame frame : frames) {
            for (Try currentTry : frame.getTries()) {
                System.out.print(currentTry.getKnockedDownPins() + " ");
            }
            System.out.print("     ");
        }
        System.out.println();
        System.out.println();
    }

}
