import org.jetbrains.annotations.Nullable;

import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;
import java.util.ArrayList;


public class Main {

    //initialize dynamic length array for processes
    static ArrayList<Process> processes;

    public static void main(String[] args) {
        loadProcesses();

        Process currentProcess = null;
        int timeSclice = 5;
        int delay = 2;

        while (processes.get(0) != null) {

            if (currentProcess.eTime == currentProcess.tTime) {
                currentProcess = null;
            }

            if (currentProcess == null) {

                currentProcess = roundRobin();

                processes.trimToSize();
            }
            else{
                Process temp = currentProcess;
                currentProcess = roundRobin();
                backToQueue(temp);
            }

            currentProcess.addWTime(delay);
            currentProcess.addETime(timeSclice);
            currentProcess.addWTime(delay);

            for (int i = 1; i < processes.size() - 1; i++) {
                processes.get(i).addWTime(delay);
                processes.get(i).addWTime(timeSclice);
                processes.get(i).addWTime(delay);

            }

        }

    }
    Process test = new Process(1, 2, 3, 4);
    public static void loadProcesses() {
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
                processes.add(new Process(Integer.parseInt(data[0]), Integer.parseInt(data[1]), Integer.parseInt(data[2]), Integer.parseInt(data[3])));
            }

            myReader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    public static void updateWaitTimes(){
        for (Process process : processes) {
            process.addWTime(7);
        }
    }


    //needs empty array check before
    public static Process roundRobin(){
        if (processes.get(1) == null){
            return null;
        }
        Process selection = processes.get(0);
        processes.remove(0);
        for (int i = 1; i < processes.size() - 1; i++) {

            processes.add(i - 1, processes.get(i));
        }
        return selection;
    }

    //Send the working process back to the end of the queue
    public static void backToQueue(Process lastRun){
        processes.trimToSize();
        processes.add(lastRun);
    }


}
