import java.util.ArrayList;

public class Process {

    int id;
    int tTime;
    int eTime;
    int wTime;
    int priority;

    public Process(int name, int totalTime, int elapsedTime, int waitTime, int priority){
        this.id = name;
        this.tTime = totalTime;
        this.eTime = elapsedTime;
        this.wTime = waitTime;
        this.priority = priority;


        System.out.println("Process ID: " + id + " totalTime: " + tTime + " elapsedTime: " + eTime + "  waitTime: " + wTime + " priority: " + priority);
    }
    public void setID(int id){

    }

    public void addETime(int cycles){
        eTime += cycles;
    }

    public void addWTime(int cycles){
        wTime += cycles;
    }


}
