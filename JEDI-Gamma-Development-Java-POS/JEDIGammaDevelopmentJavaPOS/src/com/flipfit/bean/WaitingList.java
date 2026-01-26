package com.flipfit.bean;

import java.util.LinkedList;
import java.util.Queue;

public class WaitingList {
    private int waitListId;
    private int slotId;
    private String date; // Added date to track waitlist for specific days
    private Queue<Integer> waitingQueue;

    // Inside WaitingList.java

    // Keep your 3-argument constructor
    public WaitingList(int waitListId, int slotId, String date) {
        this.waitListId = waitListId;
        this.slotId = slotId;
        this.date = date;
        this.waitingQueue = new LinkedList<>();
    }

    // Add this 2-argument constructor back to handle the old code
    public WaitingList(int waitListId, int slotId) {
        this.waitListId = waitListId;
        this.slotId = slotId;
        this.date = "Not Specified"; // Default value
        this.waitingQueue = new LinkedList<>();
    }

    public void addToQueue(int userId) {
        this.waitingQueue.add(userId);
        System.out.println("User " + userId + " added to Waitlist for " + date + ". Position: " + waitingQueue.size());
    }

    public Integer removeFromQueue() {
        return this.waitingQueue.poll();
    }

    public int getQueueSize() {
        return this.waitingQueue.size();
    }

    // Getters
    public int getSlotId() { return slotId; }
    public String getDate() { return date; }
    public Queue<Integer> getWaitingQueue() { return waitingQueue; }

    @Override
    public String toString() {
        return "Waitlist for Slot " + slotId + " [" + date + "] -> Queue: " + waitingQueue;
    }
}