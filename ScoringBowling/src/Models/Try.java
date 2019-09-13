package Models;

public class Try 
{
    private int knockedDownPins;
    public boolean done = false;
    public int tryNumber =0;

    public int getKnockedDownPins() {
        return knockedDownPins;
    }

    public void setKnockedDownPins(int knockedDownPins) {
        this.knockedDownPins = knockedDownPins;
    }
    
    public Integer getKnockedDownPinsAsInteger(){
        return knockedDownPins;
    }
}
