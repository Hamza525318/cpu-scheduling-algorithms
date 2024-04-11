package SJF;
import java.util.*;

//program to implement Shortest Job first with Arrival Time

public class ShortestJobFirst {

    static boolean isAllProcessInReadyQueue;
    static class Process{

        int process_id,arrival_time,burst_time;

        Process(int pid,int at,int bt){
            this.process_id = pid;
            this.arrival_time = at;
            this.burst_time = bt;
        }
    }

    private static void shortestJobFirstWithArrivalTime(int arr[][]){

        int n = arr.length,time=0;
        isAllProcessInReadyQueue = false;

         //priority queue to sort processes based on arrival time;
        PriorityQueue<Process> pq = new PriorityQueue<>((a,b)-> a.arrival_time - b.arrival_time);

        //Running queue that sorts the process based on the burst time.
        PriorityQueue<Process> running = new PriorityQueue<Process>(new Comparator<Process>() {
            public int compare(Process p1,Process p2){
                if(p1.burst_time == p2.burst_time){
                    return p1.arrival_time-p2.arrival_time;
                }
                return p1.burst_time-p2.burst_time;
            }
        });
        
        //ArrayList to track turn-around time for each process-> index represents the process Id and value represents the turn around time for that process;
        ArrayList<Integer> turn_around_time = new ArrayList<>();

        //ArrayList to track waiting time for each process-> index represents the process Id and value represents the waiting time for that process;
        ArrayList<Integer> waiting_time = new ArrayList<>();
        
        //ArrayList to track completion time for each process-> index represents the process Id and value represents the completion time for that process;
        ArrayList<Integer> complete_time = new ArrayList<>();


        for(int i=0;i<n;i++){
            complete_time.add(0);
            turn_around_time.add(0);
            waiting_time.add(0);
        }

        for(int i=0;i<n;i++){
            pq.add(new Process(i,arr[i][0],arr[i][1]));
        }
        
        int first_process_arrival_time = pq.peek().arrival_time;
        time = time + first_process_arrival_time;

        while(!pq.isEmpty() && pq.peek().arrival_time == first_process_arrival_time){
            running.add(pq.poll());
        }

        if(pq.isEmpty()){
            isAllProcessInReadyQueue = true;
        }
        
        while(!running.isEmpty()){

            Process p = running.poll();

            if(!isAllProcessInReadyQueue){

                time = time + 1;
                checkAndAddRemainingProcesses(pq,running,time);
                if(p.burst_time > 1){
                    running.add(new Process(p.process_id,p.arrival_time,p.burst_time-1));
                }
                else{

                complete_time.set(p.process_id, time);
                turn_around_time.set(p.process_id,complete_time.get(p.process_id)-p.arrival_time);
                waiting_time.set(p.process_id,turn_around_time.get(p.process_id)-arr[p.process_id][1]);

                }

            }
            else{
               
                time = time + p.burst_time;
                complete_time.set(p.process_id, time);
                turn_around_time.set(p.process_id,complete_time.get(p.process_id)-p.arrival_time);
                waiting_time.set(p.process_id,turn_around_time.get(p.process_id)-arr[p.process_id][1]);
            }
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

    private static void checkAndAddRemainingProcesses(PriorityQueue<Process> pq,PriorityQueue<Process> running,int time){

        while(!pq.isEmpty() && pq.peek().arrival_time <= time){
            
            running.add(pq.poll());
        }

        if(pq.isEmpty()){
            isAllProcessInReadyQueue = true;
        }
    }

    public static void main(String[] args) {
        
        int arr[][] = {{0,8},{1,4},{2,9},{3,5}};

        shortestJobFirstWithArrivalTime(arr);
    }
}
