import org.jetbrains.annotations.Nullable;

import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;
import java.util.ArrayList;


public class Main {

    //initialize dynamic length array for processes
    static ArrayList<Process> processes = null;

    public static void main(String[] args) {
        loadProcesses();

    }

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

    @Nullable
    //needs empty array check before
    public static Process roundRobin(){
        if (processes[1] = null) return ;
        Process selection = processes.get(0);
        processes.remove(0);
        for (int i = 1; i < processes.size() - 1; i++) {

            proceses[i - 1] = processes[i]
        }
        return selection;
    }

    public static


}
