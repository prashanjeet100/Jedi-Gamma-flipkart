/**
 * 
 */
package com.flipfit.bean;

import java.time.LocalTime;
/**
 * 
 */
public class BookingDetails {
	private String centerName;
    private LocalTime slotTiming;

    public String getCenterName() {
        return centerName;
    }

    public void setCenterName(String centerName) {
        this.centerName = centerName;
    }

    public LocalTime getSlotTiming() {
        return slotTiming;
    }

    public void setSlotTiming(LocalTime slotTiming) {
        this.slotTiming = slotTiming;
    }
}
