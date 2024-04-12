package priority;
import java.util.*;

//implementation of Priority Scheduling without premption
//process only consists of burst time and priority
//higher the number more is the Priority

public class PriorityWithoutpremption {

    static class Process{

        int process_id,burst_time,priority;

        Process(int pid,int bt,int priority){
            this.process_id = pid;
            this.burst_time = bt;
            this.priority = priority;
        }
    }

    private static void priorityScheduleWithoutPremption(int arr[][]){

        int n = arr.length,time=0;

        PriorityQueue<Process> pq = new PriorityQueue<>((a,b)->b.priority-a.priority);

        ArrayList<Integer> complete_time = new ArrayList<>(n);

        for(int i=0;i<n;i++){
            complete_time.add(0);
        }

        for(int i=0;i<n;i++){

            pq.add(new Process(i,arr[i][0],arr[i][1]));
        }

        while(!pq.isEmpty()){

            Process p = pq.poll();
            time = time + p.burst_time;

            complete_time.set(p.process_id,time);
        }

        for(int i=0;i<complete_time.size();i++){

            System.out.println("Completion Time for process "+i+" is "+complete_time.get(i)+" seconds");

        }



    }

    public static void main(String[] args) {
        
        //0th-index -> burst time
        //1stindex -> priority of each process

        int arr[][] = {{5,2},{4,1},{7,3},{3,4},{2,5}};

        priorityScheduleWithoutPremption(arr);
    }
    
}
