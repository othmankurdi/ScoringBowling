package BusinessLogic;

import Models.Frame;
import Models.Try;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import Models.Bowler;

public final class Controller {

    BufferedReader reader ;
    public Bowler m_Bowler;
    public ArrayList<Frame> frames;
    private Printer m_Printer;

    public Controller() {
        m_Bowler = new Bowler();
        m_Bowler.setFrames(CreateNewScoresTable());
        frames = m_Bowler.getFrames();
        m_Printer = new Printer();
        reader = new BufferedReader(new InputStreamReader(System.in));
    }

    public void PlayGame() throws IOException {
        RegisterPlayer();
        MakeTry();
        CalculateResults();
    }

    private void RegisterPlayer() throws IOException {
        System.out.println("Enter Player Name:");
        m_Bowler.Name = reader.readLine();
        System.out.println("Enter Player Age:");
        m_Bowler.age = reader.readLine();
        System.out.println("Let's bowling...");
        System.out.println("please start inserting values...");

    }

    private void MakeTry() throws IOException {
        try {
            int tenthFrameTriesCounter = 0;
            for (int i = 0; i <= 9; i++) {
                Frame currentFrame = frames.get(i);
                for (Try thisTry : currentFrame.getTries()) {
                    if (!thisTry.done) {
                        if (i == 9) {
                            tenthFrameTriesCounter++;
                            thisTry = MakeLastFrameTry(tenthFrameTriesCounter, thisTry, currentFrame);
                        } else {
                            thisTry.setKnockedDownPins(CheckInsertedValue(Integer.parseInt(reader.readLine()), currentFrame, thisTry, false));
                        }
                        currentFrame.score += thisTry.getKnockedDownPins();
                        currentFrame = CheckStrikeOrSpare(i, currentFrame, thisTry);
                    }
                }
                currentFrame.done = true;
                CalculateResults();
            }
        } catch (Exception ex) {
            System.out.println("Opppppssss...!!!!IOException has been occured! now you need to start over :/  line 79");
            PlayGame();
        }
    }

    private void CalculateResults() {
        for (int i = 0; i <= 9; i++) {
            Frame currentFrame = frames.get(i);
            if (i == 9) {
                currentFrame.spare = false;
                currentFrame.strike = false;
            }
            if (CheckFrameCalculability(i, currentFrame)) {
                if (i == 0) {
                    CalculateFirstFrame(i, currentFrame);
                } else {
                    if (!currentFrame.strike && !currentFrame.spare) {
                        currentFrame.totalScore = currentFrame.score + frames.get(i - 1).totalScore;
                    }
                    if (currentFrame.strike && i != 8) {
                        if (frames.get(i + 1).strike) {
                            currentFrame.totalScore = frames.get(i - 1).totalScore + currentFrame.score + frames.get(i + 1).score + frames.get(i + 2).getTries().get(0).getKnockedDownPins();
                        } else {
                            currentFrame.totalScore = frames.get(i - 1).totalScore + currentFrame.score + frames.get(i + 1).score;
                        }
                    }
                    if (currentFrame.strike && i == 8) {
                        currentFrame.totalScore = frames.get(i - 1).totalScore + currentFrame.score + frames.get(i + 1).getTries().get(0).getKnockedDownPins() + frames.get(i + 1).getTries().get(1).getKnockedDownPins();
                    }
                    if (currentFrame.spare) {
                        currentFrame.totalScore = frames.get(i - 1).totalScore + currentFrame.score + frames.get(i + 1).getTries().get(0).getKnockedDownPins();
                    }
                }
            }
        }
        m_Printer.Print(frames);
    }

    private Frame CheckStrikeOrSpare(int i, Frame currentFrame, Try thisTry) {
        if (thisTry.getKnockedDownPins() == 10 && thisTry.tryNumber == 1) {
            currentFrame.strike = true;
            if (i != 9) {
                currentFrame.getTries().get(1).done = true;
            }
        }
        if (thisTry.tryNumber == 2 && currentFrame.score >= 10) {
            currentFrame.spare = true;
        }
        return currentFrame;
    }

    private Try MakeLastFrameTry(int tenthFrameTriesCounter, Try thisTry, Frame currentFrame) throws IOException {
        if (tenthFrameTriesCounter != 3) {
            thisTry.setKnockedDownPins(CheckInsertedValue(Integer.parseInt(reader.readLine()), currentFrame, thisTry, true));
        } else {
            if (currentFrame.spare || currentFrame.strike) {
                thisTry.setKnockedDownPins(CheckInsertedValue(Integer.parseInt(reader.readLine()), currentFrame, thisTry, true));
            }
        }
        return thisTry;
    }

    private int CheckInsertedValue(Integer value, Frame currentFrame, Try currentTry, boolean isLastFrame) {
        if (currentTry.tryNumber == 2 && !isLastFrame) {
            if (value > 10 - currentFrame.getTries().get(0).getKnockedDownPins()) {
                value = 10 - currentFrame.getTries().get(0).getKnockedDownPins();
            }
        }
        if (value >= 10) {
            return 10;
        } else {
            return value;
        }
    }

    private void CalculateFirstFrame(int i, Frame currentFrame) {
        if (!currentFrame.strike && !currentFrame.spare) {
            currentFrame.totalScore = currentFrame.score;
        }
        if (currentFrame.strike) {
            if (frames.get(i + 1).strike) {
                currentFrame.totalScore = currentFrame.score + frames.get(i + 1).score + frames.get(i + 2).getTries().get(0).getKnockedDownPins();
            } else {
                currentFrame.totalScore = currentFrame.score + frames.get(i + 1).score;
            }
        }
        if (currentFrame.spare) {
            currentFrame.totalScore = currentFrame.score + frames.get(i + 1).getTries().get(0).getKnockedDownPins();
        }
    }

    private boolean CheckFrameCalculability(int i, Frame currentFrame) {
        boolean calculable = false;
        if (currentFrame.done && i != 8) {
            if (!currentFrame.strike && !currentFrame.spare) {
                calculable = true;
            }
            if (currentFrame.strike) {
                if (frames.get(i + 1).strike) {
                    if (frames.get(i + 1).done && frames.get(i + 2).done) {
                        calculable = true;
                    }
                } else {
                    if (frames.get(i + 1).done) {
                        calculable = true;
                    } else {
                        calculable = false;
                    }
                }
            }
            if (currentFrame.spare) {
                if (frames.get(i + 1).done) {
                    calculable = true;
                }
            }
        }
        if (currentFrame.done && i == 8) {
            if (!currentFrame.strike && !currentFrame.spare) {
                calculable = true;
            }
            if (currentFrame.strike || currentFrame.spare) {
                if (frames.get(i + 1).done) {
                    calculable = true;
                }
            }
        }
        return calculable;
    }

    private ArrayList<Frame> CreateNewScoresTable() {
        int triesCounter = 0;
        int indexOfTry = 0;
        ArrayList<Frame> createFrames = new ArrayList<Frame>();
        while (createFrames.size() < 10) {
            if (createFrames.size() + 1 == 10) {
                triesCounter = 3;
            } else {
                triesCounter = 2;
            }
            Frame frame = new Frame();
            indexOfTry = 0;
            while (frame.tries.size() < triesCounter) {
                Try newTry = new Try();
                newTry.tryNumber = (++indexOfTry);
                frame.tries.add(newTry);
            }
            createFrames.add(frame);
        }
        return createFrames;
    }
    
}
