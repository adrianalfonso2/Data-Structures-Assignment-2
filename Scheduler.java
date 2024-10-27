import java.io.*;
import java.util.*;

class Job {
    int id;
    int processingTime;
    Integer priority = null;      // Nullable priority, used for Task 2
    Integer arrivalTime = null;   // Nullable arrival time, used for Task 3

    // Constructor for Task 1
    public Job(int id, int processingTime) {
        this.id = id;
        this.processingTime = processingTime;
    }

    // Constructor for Task 2 with priority
    public Job(int id, int processingTime, Integer priority) {
        this.id = id;
        this.processingTime = processingTime;
        this.priority = priority;
    }

    // Constructor for Task 3 with arrivalTime
    public Job(int id, int processingTime, int arrivalTime) {
        this.id = id;
        this.processingTime = processingTime;
        this.arrivalTime = arrivalTime;
    }
}

public class Scheduler {

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
        System.out.println("Average completion time: " + averageCompletionTime);
    }

    public static void schedulePrioritySPT(List<Job> jobs) {
        List<Job> priorityJobs = new ArrayList<>();
        for (Job job : jobs) {
            if (job.priority != null) {
                priorityJobs.add(job);
            }
        }
    
        priorityJobs.sort(Comparator.comparingInt((Job job) -> job.priority)
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
        System.out.println("Average completion time: " + averageCompletionTime);
    }

    public static void scheduleDynamicSPT(List<Job> jobs) {
        jobs.sort(Comparator.comparingInt(job -> job.arrivalTime));

        PriorityQueue<Job> minHeap = new PriorityQueue<>(Comparator.comparingInt(job -> job.processingTime));
        int currentTime = 0;
        double totalCompletionTime = 0;
        List<Integer> executionOrder = new ArrayList<>();
        int index = 0;

        while (index < jobs.size() || !minHeap.isEmpty()) {
            while (index < jobs.size() && jobs.get(index).arrivalTime <= currentTime) {
                minHeap.offer(jobs.get(index));
                index++;
            }

            if (minHeap.isEmpty()) {
                currentTime = jobs.get(index).arrivalTime;
            } else {
                Job job = minHeap.poll();
                currentTime += job.processingTime;
                totalCompletionTime += currentTime - job.arrivalTime;
                executionOrder.add(job.id);
            }
        }

        double averageCompletionTime = totalCompletionTime / jobs.size();
        System.out.println("Execution order: " + executionOrder);
        System.out.println("Average completion time: " + averageCompletionTime);
    }

    public static List<Job> readTask1Jobs(String filename) throws IOException {
        List<Job> jobs = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(" ");
                int id = Integer.parseInt(parts[0]);
                int processingTime = Integer.parseInt(parts[1]);
                jobs.add(new Job(id, processingTime));
            }
        }
        return jobs;
    }

    public static List<Job> readTask2Jobs(String filename) throws IOException {
        List<Job> jobs = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(" ");
                int id = Integer.parseInt(parts[0]);
                int processingTime = Integer.parseInt(parts[1]);
                int priority = Integer.parseInt(parts[2]);
                jobs.add(new Job(id, processingTime, priority));
            }
        }
        return jobs;
    }
    public static List<Job> readTask3Jobs(String filename) throws IOException {
        List<Job> jobs = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(" ");
                int id = Integer.parseInt(parts[0]);
                int processingTime = Integer.parseInt(parts[1]);
                int arrivalTime = Integer.parseInt(parts[2]);
                jobs.add(new Job(id, processingTime, arrivalTime));
            }
        }
        return jobs;
    }
    public static void main(String[] args) throws IOException {
        System.out.println("Task 1:");
        List<Job> task1Jobs = readTask1Jobs("task1-input.txt");
        scheduleSPT(task1Jobs);

        System.out.println("\nTask 2:");
        List<Job> task2Jobs = readTask2Jobs("task2-input.txt");
        schedulePrioritySPT(task2Jobs);

        System.out.println("\nTask 3:");
        List<Job> task3Jobs = readTask3Jobs("task3-input.txt");
        scheduleDynamicSPT(task3Jobs);
    }
}