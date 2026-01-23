/**
 * 
 */
package com.flipfit.business;

import java.util.Date;

/**
 * 
 */
public class CustomerBusinessImpl implements CustomerBusiness {
	public void viewCentres(String city) { }
    public boolean viewAvailability(Date date, String centerId) { return false; }
    public boolean bookSlot(String slotId, Date date) { return false; }
    public boolean cancelBooking(String bookingId) { return false; }
}
