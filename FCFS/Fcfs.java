package FCFS;
import java.util.*;

import java.util.PriorityQueue;

public class Fcfs {
    
    static boolean isAllProcessInReadyQueue;
    static class Process{

        int process_id,arrival_time,burst_time;

        Process(int pid,int at,int bt){
            this.process_id = pid;
            this.arrival_time = at;
            this.burst_time = bt;
        }
    }

    private static void firstComeFirstServe(int arr[][]){
        
        //represents the no of processes and time required to run all processes
        int n = arr.length,time=0;
        
        //priority queue to sort all processes based on their arrival time
        PriorityQueue<Process> pq = new PriorityQueue<>((a,b)-> a.arrival_time - b.arrival_time);
          
        //ArrayList to track turn-around time for each process-> index represents the process Id and value represents the turn around time for that process;
        ArrayList<Integer> turn_around_time = new ArrayList<>();

        //ArrayList to track waiting time for each process-> index represents the process Id and value represents the waiting time for that process;
        ArrayList<Integer> waiting_time = new ArrayList<>();
        

        //ArrayList to track completion time for each process-> index represents the process Id and value represents the completion time for that process;
        ArrayList<Integer> complete_time = new ArrayList<>();


        for(int i=0;i<n;i++){
            pq.add(new Process(i, arr[i][0],arr[i][1]));
        }
        
        for(int i=0;i<n;i++){
            complete_time.add(0);
            waiting_time.add(0);
            turn_around_time.add(0);
        }
        
        int first_process_arrival_time = pq.peek().arrival_time;
        time = time + first_process_arrival_time;
         
        //idea is to just remove elements from the queue one by one because the processes are aldready sorted
        //based on arrival time

        while(!pq.isEmpty()){

            Process p = pq.poll();

            time += p.burst_time;

            //completion time for the process
            complete_time.set(p.process_id, time);
            
            //TURN AROUND TIME = CT-AT
            turn_around_time.set(p.process_id, complete_time.get(p.process_id)-p.arrival_time);

            //WAITING TIME = TAT - BT
            waiting_time.set(p.process_id,turn_around_time.get(p.process_id)-p.burst_time);
        }

        //Printing turn around time for each process in order of the process_id
        for(int i=0;i<complete_time.size();i++){

            System.out.println("Completion Time for process "+i+" is "+complete_time.get(i)+" seconds");

        }
         //Printing turn around time for each process in order of the process_id
         for(int i=0;i<turn_around_time.size();i++){

            System.out.println("Turn around Time for process "+i+" is "+turn_around_time.get(i)+" seconds");

        }

        //Printing waiting time for each process in order of the process_id
        for(int i=0;i<turn_around_time.size();i++){

            System.out.println("Waiting Time for process "+i+" is "+waiting_time.get(i)+" seconds");

        }



    }

    public static void main(String[] args) {
        
        int arr[][] = {{1,5},{1,4},{2,2},{4,1}};

        firstComeFirstServe(arr);
    }


}
