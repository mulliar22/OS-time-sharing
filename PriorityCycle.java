import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.ArrayList;


public class PriorityCycle {

    //initialize dynamic length array for processes
    static ArrayList<Process> processes = new ArrayList<>();
    static ArrayList<Process> priorityProcesses = new ArrayList<>();
    
    public static void main(String[] args) {
        loadProcesses(); //Loads process.txt into the program
//        sort2DArrayByRow();
        int timeSclice = 49; //number of time slices
        int delay = 2; //delay between process runtimes
        int accumulator = 0; //total runtime of program
        int priorityCount = processes.get(0).priority;// Sets the priority Count to the highest priority
        int lowestPriority = processes.get(0).priority;

        for (Process p : processes) {
            if (p.priority > priorityCount) {
                priorityCount = p.priority;
            }

            if (p.priority < lowestPriority) {
                lowestPriority = p.priority;
            }
        }

        while(!processes.isEmpty()){ //rotates through process queue
            Process currentProcess;
            for (int i = 0; i < processes.size() - 1; i++) {
                if (processes.get(i).priority == priorityCount) {
                    priorityProcesses.add(processes.get(i));
                }
            }

            if (!priorityProcesses.isEmpty()){
                currentProcess = priorityProcesses.get(0);
            }
            else {
                currentProcess = null;
            }

            while(!priorityProcesses.isEmpty()) {
                accumulator += 2;
                System.out.println("Process# " + currentProcess.id + " with Priority " + currentProcess.priority + " Started at clock cycle " + accumulator);
                int remain = currentProcess.tTime - currentProcess.eTime; //amount of time remaining in a process

                if (remain < timeSclice) {  //Checks if time remaining is less then time slice
                    currentProcess.addWTime(delay); //adds delay to wait time
                    currentProcess.addETime(remain); //adds remaining time to elapsed time

                    accumulator += remain;

                } else {
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

                for (int i = 1; i < priorityProcesses.size() - 1; i++) { //adds time to all the remaining processes
                    priorityProcesses.get(i).addWTime(delay);
                    priorityProcesses.get(i).addWTime(timeSclice);
                    priorityProcesses.get(i).addWTime(delay);

                }


                if (currentProcess.eTime >= currentProcess.tTime) { //removes process if it is completed
                    currentProcess = null;
                }

                if (currentProcess == null) { //checks if run process is empty

                    currentProcess = roundRobin();

                    priorityProcesses.removeLast();
                }
                else{
                    Process temp = currentProcess;
                    currentProcess = roundRobin();
                    backToQueue(temp);
                }


                System.out.println("Current process ended at clock cycle " + accumulator);

            }
            System.out.println("Priority " + priorityCount + " completed");
            priorityCount--;
            if (priorityCount >= lowestPriority) {
                System.out.println("Starting Priority " + priorityCount + " processes");
            } else {
                processes.clear();
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
                processes.add(new Process(Integer.parseInt(data[0]), Integer.parseInt(data[1]), Integer.parseInt(data[2]), Integer.parseInt(data[3]), Integer.parseInt(data[4])));
            }

            myReader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    //needs empty array check before
    public static Process roundRobin(){  //Cycles processes through the arraylist
        for (int i = 1; i <= priorityProcesses.size() - 1; i++) {

            priorityProcesses.set(i - 1, priorityProcesses.get(i));
        }
        return priorityProcesses.get(0);
    }

    //Send the working process back to the end of the queue
    public static void backToQueue(Process lastRun){ //Sends the last ran process back to the bottom of the queue
        priorityProcesses.set(priorityProcesses.size() - 1, lastRun);
    }


//    public static void sort2DArrayByRow() {
//        // Sorting the processes by the fifth field in descending order
//        processes.sort((p1, p2) -> {
//            // Assuming index 4 corresponds to some field `priority` in the custom Process class
//            return Integer.compare(p2.priority, p1.priority); // Descending order
//        });
//
//    }

}
