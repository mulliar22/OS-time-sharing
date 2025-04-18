import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.ArrayList;


public class RoundRobinCycle {

    //initialize dynamic length array for processes
    static ArrayList<Process> processes = new ArrayList<>();

    public static void main(String[] args) {
        loadProcesses(); //Loads process.txt into the program

        Process currentProcess = processes.get(0); //sets current process to the first in the queue
        int timeSclice = 49; //number of time slices
        int delay = 2; //delay between process runtimes
        int accumulator = 0; //total runtime of program
        //while (processes.get(0) != null) {
        while(processes.size() != 0){ //rotates through process queue
            accumulator += 2;
            System.out.println("Process# " + currentProcess.id + " Started at clock cycle " + accumulator);
            int remain = currentProcess.tTime - currentProcess.eTime; //amount of time remaining in a process

            if (remain < timeSclice) {  //Checks if time remaining is less then time slice
                currentProcess.addWTime(delay); //adds delay to wait time
                currentProcess.addETime(remain); //adds remaining time to elapsed time

                accumulator +=  remain;

            }
            else {
                currentProcess.addWTime(delay);
                currentProcess.addETime(timeSclice);
                currentProcess.addWTime(delay);
                accumulator += timeSclice;
            }


            for (int i = 1; i < processes.size() - 1; i++) { //adds time to all the remaining processes
                processes.get(i).addWTime(delay);
                processes.get(i).addWTime(timeSclice);
                processes.get(i).addWTime(delay);

            }


            if (currentProcess.eTime >= currentProcess.tTime) { //removes process if it is completed
                currentProcess = null;
            }

            if (currentProcess == null) { //checks if run process is empty

                currentProcess = roundRobin();

                processes.removeLast();
            }
            else{
                Process temp = currentProcess;
                currentProcess = roundRobin();
                backToQueue(temp);
            }
            System.out.println("Process ended at clock cycle " + accumulator);

        }
        System.out.println("All processes ended at clock cycle " + accumulator);
    }

    public static void loadProcesses() {  //Load Processes
        try {
            //create new File and Scanner objects
            File fileObj = new File("processes.txt");
            Scanner myReader = new Scanner(fileObj);

            while (myReader.hasNextLine()) {
                // read a line and split it on commas
                String line = myReader.nextLine();
                String[] data = line.split(",");
                System.out.println(data);

                // create new process instances and add them to the list of processes
                processes.add(new Process(Integer.parseInt(data[0]), Integer.parseInt(data[1]), Integer.parseInt(data[2]), Integer.parseInt(data[3]), Integer.parseInt(data[4])));
            }

            myReader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }



    //needs empty array check before
    public static Process roundRobin(){  //Cycles processes through the arraylist
        for (int i = 1; i <= processes.size() - 1; i++) {

            processes.set(i - 1, processes.get(i));
        }
        return processes.get(0);
    }

    //Send the working process back to the end of the queue
    public static void backToQueue(Process lastRun){ //Sends the last ran process back to the bottom of the queue
        processes.set(processes.size() - 1, lastRun);
    }


}
