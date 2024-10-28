import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

class Job {
    int id;
    int processingTime;
    Integer priority;
    Integer arrivalTime;

    Job(int id, int processingTime, Integer priority, Integer arrivalTime) {
        this.id = id;
        this.processingTime = processingTime;
        this.priority = priority;
        this.arrivalTime = arrivalTime;
    }
}

public class JobScheduler {

    public static void main(String[] args) throws IOException {
        String task1Path = "task1-input.txt";
        String task2Path = "task2-input.txt";
        String task3Path = "task3-input.txt";

        System.out.println("Task 1:");
        List<Job> jobs1 = readJobs(task1Path, false, false);
        scheduleSPT(jobs1);  

        System.out.println("Task 2:");
        List<Job> jobs2 = readJobs(task2Path, true, false);
        schedulePrioritySPT(jobs2);  

        System.out.println("Task 3:");
        List<Job> jobs3 = readJobs(task3Path, false, true);
        scheduleArrivalSPT(jobs3);  
    }

    public static List<Job> readJobs(String filePath, boolean hasPriority, boolean hasArrivalTime) throws IOException {
        List<Job> jobs = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(" ");
                int id = Integer.parseInt(parts[0]);
                int processingTime = Integer.parseInt(parts[1]);
                Integer priority = hasPriority ? Integer.parseInt(parts[2]) : null;
                Integer arrivalTime = hasArrivalTime ? Integer.parseInt(parts[2]) : null;
                jobs.add(new Job(id, processingTime, priority, arrivalTime));
            }
        }
        return jobs;
    }

    public static void scheduleSPT(List<Job> jobs) {
        jobs.sort(Comparator.comparingInt(job -> job.processingTime));

        int currentTime = 0;
        double totalCompletionTime = 0;
        List<Integer> executionOrder = new ArrayList<>();

        for (Job job : jobs) {
            currentTime += job.processingTime;
            totalCompletionTime += currentTime;
            executionOrder.add(job.id);
        }

        double averageCompletionTime = totalCompletionTime / jobs.size();
        System.out.println("Execution order: " + executionOrder);
        System.out.printf("Average completion time: %.2f\n", averageCompletionTime);
    }

    public static void schedulePrioritySPT(List<Job> jobs) {
        List<Job> priorityJobs = new ArrayList<>(jobs);
        priorityJobs.sort(Comparator.comparing((Job job) -> job.priority)
                .thenComparingInt(job -> job.processingTime));

        int currentTime = 0;
        double totalCompletionTime = 0;
        List<Integer> executionOrder = new ArrayList<>();

        for (Job job : priorityJobs) {
            currentTime += job.processingTime;
            totalCompletionTime += currentTime;
            executionOrder.add(job.id);
        }

        double averageCompletionTime = totalCompletionTime / priorityJobs.size();
        System.out.println("Execution order: " + executionOrder);
        System.out.printf("Average completion time: %.2f\n", averageCompletionTime);
    }

    public static void scheduleArrivalSPT(List<Job> jobs) {
        PriorityQueue<Job> jobQueue = new PriorityQueue<>(Comparator
                .comparing((Job job) -> job.arrivalTime)
                .thenComparingInt(job -> job.processingTime));

        int currentTime = 0;
        double totalCompletionTime = 0;
        List<Integer> executionOrder = new ArrayList<>();

        jobQueue.addAll(jobs);
        while (!jobQueue.isEmpty()) {
            Job job = jobQueue.poll();
            currentTime = Math.max(currentTime, job.arrivalTime) + job.processingTime;
            totalCompletionTime += currentTime;
            executionOrder.add(job.id);
        }

        double averageCompletionTime = totalCompletionTime / jobs.size();
        System.out.println("Execution order: " + executionOrder);
        System.out.printf("Average completion time: %.2f\n", averageCompletionTime);
    }
}