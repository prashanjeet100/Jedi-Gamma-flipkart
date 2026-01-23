/**
 * 
 */
package com.flipfit.bean;

import java.time.LocalTime;

/**
 * 
 */
public class Slot {
	private String slotId;
    private LocalTime startTime;
    private int totalSeats;

    public String getSlotId() {
        return slotId;
    }

    public void setSlotId(String slotId) {
        this.slotId = slotId;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public int getTotalSeats() {
        return totalSeats;
    }

    public void setTotalSeats(int totalSeats) {
        this.totalSeats = totalSeats;
    }
}
