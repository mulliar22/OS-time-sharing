import java.awt.print.PrinterGraphics;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Comparator;
import java.util.Scanner;
import java.util.ArrayList;


public class ShortestProcess {

    //initialize dynamic length array for processes
    static ArrayList<Process> processes = new ArrayList<>();
    static ArrayList<Process> scheduledStack = new ArrayList<>();

    public static void main(String[] args) {
        loadProcesses(); //Loads process.txt into the program
        sortByEntrance();
        int accumulator = 0; //total runtime of program
        //while (processes.get(0) != null) {
        while(!scheduledStack.isEmpty()){ //rotates through process queue

            while (processes.isEmpty()){
                accumulator += 1;
                if (!scheduledStack.isEmpty()) {
                    if (scheduledStack.getFirst().entranceTime == accumulator && !scheduledStack.isEmpty()) {
                        processes.add(scheduledStack.getFirst());
                        System.out.println("Loaded Process " + scheduledStack.getFirst().id + " into stack at cycle " + accumulator);
                        cycleStack();
                    }
                }
            }

            while (!processes.isEmpty()){
                sortByRunTime();
                Process currentProcess = processes.getFirst(); //sets current process to the first in the queue

                //First Delay
                if (!scheduledStack.isEmpty() && scheduledStack.getFirst().entranceTime == accumulator) {
                    accumulator += 1;

                    processes.add(scheduledStack.getFirst());
                    System.out.println("Loaded Process " + scheduledStack.getFirst().id + " into stack at cycle " + accumulator);
                    cycleStack();

                } else {
                    accumulator += 1;
                }

                //Second Delay
                if (!scheduledStack.isEmpty() && scheduledStack.getFirst().entranceTime == accumulator) {
                    accumulator += 1;

                    processes.add(scheduledStack.getFirst());
                    System.out.println("Loaded Process " + scheduledStack.getFirst().id + " into stack at cycle " + accumulator);
                    cycleStack();
                } else {
                    accumulator += 1;
                }

                currentProcess.wTime += 2;
                currentProcess.turnaroundTime += 2;

                if (processes.size() > 1) {
                    for (int i = 1; i <= processes.size() - 1; i++) { //adds time to all the remaining processes
                        processes.get(i).wTime += 2;
                        processes.get(i).turnaroundTime += 2;
                    }
                }

                System.out.println("Process# " + currentProcess.id + " Started at clock cycle " + accumulator);

                //Process Currently Running
                while (currentProcess.eTime < currentProcess.tTime){
                    accumulator += 1;
                    if (!scheduledStack.isEmpty()) {
                        if (scheduledStack.getFirst().entranceTime == accumulator) {
                            processes.add(scheduledStack.getFirst());
                            System.out.println("Loaded Process " + scheduledStack.getFirst().id + " into stack at cycle " + accumulator);
                            cycleStack();
                        }
                    }
                    currentProcess.eTime += 1;
                    currentProcess.turnaroundTime += 1;

                    if (processes.size() > 1) {
                        for (int i = 1; i <= processes.size() - 1; i++) { //adds time to all the remaining processes
                            processes.get(i).wTime += 1;
                            processes.get(i).turnaroundTime += 1;
                        }
                    }

                }

                System.out.println("Process ended at clock cycle " + accumulator);
                System.out.println("Process " + currentProcess.id + " final turn around time = " + currentProcess.turnaroundTime);
                System.out.println("Process " + currentProcess.id + " normalized turn around time = " + currentProcess.turnaroundTime / currentProcess.tTime);

                rotateProcesses();

                processes.removeLast();

            }

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
                scheduledStack.add(new Process(Integer.parseInt(data[0]), Integer.parseInt(data[1]), Integer.parseInt(data[2]), Integer.parseInt(data[3]), Integer.parseInt(data[4]), Integer.parseInt(data[5])));
            }

            myReader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }



    //needs empty array check before
    public static void rotateProcesses(){  //Cycles processes through the arraylist
        for (int i = 1; i <= processes.size() - 1; i++) {

            processes.set(i - 1, processes.get(i));
        }
    }

    public static void cycleStack(){  //Cycles processes through the arraylist

        if(scheduledStack.size() > 1){
            for (int i = 1; i <= scheduledStack.size() - 1; i++) {

                scheduledStack.set(i - 1, scheduledStack.get(i));
            }
            scheduledStack.removeLast();
        } else  {
            scheduledStack.clear();
        }



    }

    public static void sortByEntrance() {
        scheduledStack.sort(Comparator.comparingInt(p -> p.entranceTime));

    }

    public static void sortByRunTime() {
        // Sorting the processes by the fifth field in descending order
        processes.sort(Comparator.comparingInt(p -> p.tTime));

    }

    //Send the working process back to the end of the queue
    public static void backToQueue(Process lastRun){ //Sends the last ran process back to the bottom of the queue
        processes.set(processes.size() - 1, lastRun);
    }


}
