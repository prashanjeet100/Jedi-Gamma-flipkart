/**
 * 
 */
package com.flipfit.business;

/**
 * 
 */
public interface BookingBusiness {
	public boolean validateNoConflict();
    public void promoteWaitlist();
}
