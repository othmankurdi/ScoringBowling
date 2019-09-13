package Models;

import java.util.ArrayList;

public class Bowler {
    public String Name;
    public String age;
    public ArrayList<Frame> frames= new ArrayList<Frame>();

    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public ArrayList<Frame> getFrames() {
        return frames;
    }

    public void setFrames(ArrayList<Frame> frames) {
        this.frames = frames;
    }
}
