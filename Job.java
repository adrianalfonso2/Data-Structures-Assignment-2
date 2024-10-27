public class Job {
int id;
    int processingTime;
    Integer priority = null;      
    Integer arrivalTime = null;  

    public Job(int id, int processingTime) {
        this.id = id;
        this.processingTime = processingTime;
    }

    public Job(int id, int processingTime, Integer priority) {
        this.id = id;
        this.processingTime = processingTime;
        this.priority = priority;
    }

    public Job(int id, int processingTime, int arrivalTime) {
        this.id = id;
        this.processingTime = processingTime;
        this.arrivalTime = arrivalTime;
    }
}