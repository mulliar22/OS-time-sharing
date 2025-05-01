import java.util.ArrayList;

public class Process {

    int id;
    int tTime;
    int eTime;
    int wTime;
    int turnaroundTime;
    int priority;
    int entranceTime;

    public Process(int name, int totalTime, int elapsedTime, int waitTime, int turnaroundTime, int entranceTime) {
        this.id = name;
        this.tTime = totalTime;
        this.eTime = elapsedTime;
        this.wTime = waitTime;
        this.turnaroundTime = turnaroundTime;



        System.out.println("Process ID: " + id + " totalTime: " + tTime + " elapsedTime: " + eTime + "  waitTime: " + wTime);
    }

    public Process(int name, int totalTime, int elapsedTime, int waitTime, int turnaroundTime) {
        this.id = name;
        this.tTime = totalTime;
        this.eTime = elapsedTime;
        this.wTime = waitTime;
        this.priority = priority;
    }


    public void addETime(int cycles){
        eTime += cycles;
    }

    public void addWTime(int cycles){
        wTime += cycles;
    }

    public void addTurnTime(int cycle) {
        turnaroundTime += cycle;
    }


}
