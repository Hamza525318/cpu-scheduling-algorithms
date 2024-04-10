package roundRobin;
import java.util.*;

public class RoundRobin{
    
    static boolean isAllProcessInReadyQueue;
    static class Process{

        int process_id,arrival_time,burst_time;

        Process(int pid,int at,int bt){
            this.process_id = pid;
            this.arrival_time = at;
            this.burst_time = bt;
        }
    }

    private static void roundRobin(int arr[][],int time_quantum){
       
        //priority queue to sort processes based on arrival time;
        PriorityQueue<Process> pq = new PriorityQueue<>((a,b)-> a.arrival_time - b.arrival_time);
        
        //two queues one ready and one running queue
        Queue<Process> ready = new LinkedList<>();
        Queue<Process> running = new LinkedList<>();

        //ArrayList to track completion time for all processes
        ArrayList<int[]> completed_process = new ArrayList<>();

        //ArrayList to track turn-around time for each process-> index represents the process Id and value represents the turn around time fot that process;
        ArrayList<Integer> turn_around_time = new ArrayList<>(arr.length); 
        
        
        //ArrayList to track waiting time for each process ->index represents the process Id and value represents the waiting time fot that process;
        ArrayList<Integer> waiting_time = new ArrayList<>(arr.length);

        for(int i=0;i<arr.length;i++){
            turn_around_time.add(0);
            waiting_time.add(0);
        }

        int time = 0;
        isAllProcessInReadyQueue = false;

        for(int i=0;i<arr.length;i++){
            pq.add(new Process(i, arr[i][0],arr[i][1]));
        }

        int first_process_at = pq.peek().arrival_time;
        time += first_process_at;


        while(!pq.isEmpty() && pq.peek().arrival_time == first_process_at){
            ready.add(pq.poll());
        }

        if(pq.isEmpty()){
            isAllProcessInReadyQueue = true;
        }

        while(!ready.isEmpty()){
             
            //pop the process from ready queue and add it to the running queue;
            Process p = ready.poll();
            running.add(p);

            //check if the burst time is less than time quantum or not and add it to the running time;
            if(p.burst_time > time_quantum){
                time += time_quantum;
            }
            else{
                time = time + p.burst_time; 
                completed_process.add(new int[]{p.process_id,time});

                //TAT = CT-AT
                turn_around_time.set(p.process_id,time-p.arrival_time);
                System.out.println(p.process_id);

                //WT = TAT - INITIAL Burst Time
                waiting_time.set(p.process_id,turn_around_time.get(p.process_id)-arr[p.process_id][1]);
            }
             
            //if not all process are ready queue, check and add if any new process is ready within time
            if(!isAllProcessInReadyQueue){
                checkAndAddRemainingProcesses(time,ready,pq);
            }

            //add the existing process back in ready queue if burst time is still left
            if(p.burst_time > time_quantum){
                ready.add(new Process(p.process_id, p.arrival_time,p.burst_time-time_quantum));
            }
        }
        
        //Printing Completion time for each process in order of their completion time
        for(int i=0;i<completed_process.size();i++){

            System.out.println("Process ID: "+completed_process.get(i)[0]+" completed at "+completed_process.get(i)[1]+" seconds");

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

    private static void checkAndAddRemainingProcesses(int time,Queue<Process> readyQueue,PriorityQueue<Process> pq){

        while(!pq.isEmpty() && pq.peek().arrival_time <= time){
            readyQueue.add(pq.poll());
        }

        if(pq.isEmpty()){
            isAllProcessInReadyQueue = true;
        }

    }


    public static void main(String[] args) {
        
        int arr[][] = {{0,5},{1,4},{2,2},{4,1}};
        int time_quantum = 2;

        roundRobin(arr, time_quantum);

    }


}