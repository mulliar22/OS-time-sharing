public class Process {

    int id;
    int tTime;
    int eTime;
    int wTime;

    public Process(int name, int totalTime, int elapsedTime, int waitTime){
        this.id = name;
        this.tTime = totalTime;
        this.eTime = elapsedTime;
        this.wTime = waitTime;


        System.out.println("Process ID: " + id + " totalTime: " + tTime + " elapsedTime: " + eTime + "  waitTime: " + wTime);
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
