/**
 * 
 */
package com.flipfit.bean;

import java.util.Date;
import java.util.List;

/**
 * 
 */
public class Schedule {
	private Date date;
    private int availableSeats;
    private List<String> waitingList;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getAvailableSeats() {
        return availableSeats;
    }

    public void setAvailableSeats(int availableSeats) {
        this.availableSeats = availableSeats;
    }

    public List<String> getWaitingList() {
        return waitingList;
    }

    public void setWaitingList(List<String> waitingList) {
        this.waitingList = waitingList;
    }
}
