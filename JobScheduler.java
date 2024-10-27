import java.io.*;
import java.util.*;

public class JobScheduler {

    // Job class to store job attributes
    static class Job {
        int id;
        int processingTime;
        Integer priority = null;
        Integer arrivalTime = null;

        // Constructors
        public Job(int id, int processingTime) {
            this.id = id;
            this.processingTime = processingTime;
        }

        public Job(int id, int processingTime, int priority) {
            this(id, processingTime);
            this.priority = priority;
        }

        public Job(int id, int processingTime, int arrivalTime, boolean isArrivalTime) {
            this(id, processingTime);
            this.arrivalTime = arrivalTime;
        }
    }

    // Method to read jobs for Task 1 (SPT scheduling)
    public static List<Job> readTask1Jobs(String filename) throws IOException {
        List<Job> jobs = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.trim().split("\\s+");
                int id = Integer.parseInt(parts[0]);
                int processingTime = Integer.parseInt(parts[1]);
                jobs.add(new Job(id, processingTime));
            }
        }
        return jobs;
    }

    // Method to read jobs for Task 2 (Priority scheduling)
    public static List<Job> readTask2Jobs(String filename) throws IOException {
        List<Job> jobs = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.trim().split("\\s+");
                int id = Integer.parseInt(parts[0]);
                int processingTime = Integer.parseInt(parts[1]);
                int priority = Integer.parseInt(parts[2]);
                jobs.add(new Job(id, processingTime, priority));
            }
        }
        return jobs;
    }

    // Method to read jobs for Task 3 (Dynamic SPT with arrival times)
    public static List<Job> readTask3Jobs(String filename) throws IOException {
        List<Job> jobs = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.trim().split("\\s+");
                int id = Integer.parseInt(parts[0]);
                int processingTime = Integer.parseInt(parts[1]);
                int arrivalTime = Integer.parseInt(parts[2]);
                jobs.add(new Job(id, processingTime, arrivalTime, true));
            }
        }
        return jobs;
    }

    // Task 1: Shortest Processing Time First (SPT)
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
        System.out.printf("Average completion time: %.2f%n", averageCompletionTime);
    }

    // Task 2: Priority-Based SPT
    public static void schedulePrioritySPT(List<Job> jobs) {
        jobs.sort(Comparator.comparingInt((Job job) -> job.priority)
                .thenComparingInt(job -> job.processingTime));

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
        System.out.printf("Average completion time: %.2f%n", averageCompletionTime);
    }

    // Task 3: Dynamic SPT with Arrival Time
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
        System.out.printf("Average completion time: %.2f%n", averageCompletionTime);
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