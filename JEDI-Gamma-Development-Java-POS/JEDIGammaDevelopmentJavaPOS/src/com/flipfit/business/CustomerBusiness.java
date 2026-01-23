/**
 * 
 */
package com.flipfit.business;

import java.util.Date;

/**
 * 
 */
public interface CustomerBusiness {
	public void viewCentres(String city);
    public boolean viewAvailability(Date date, String centerId);
    public boolean bookSlot(String slotId, Date date);
    public boolean cancelBooking(String bookingId);
}
