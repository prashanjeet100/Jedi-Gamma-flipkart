package com.flipfit.business;

import com.flipfit.bean.GymCentre;
import java.util.List;

/**
 * Business Interface for Gym Owner operations
 */
public interface GymOwnerBusiness {
	/**
	 * Add a new gym centre
	 * 
	 * @param gymCentre The gym centre to add
	 * @return true if added successfully
	 */
	public boolean addCentre(GymCentre gymCentre);

	/**
	 * Delete a gym centre by its ID
	 * 
	 * @param centreId The ID of the centre to delete
	 * @return true if deleted successfully
	 */
	public boolean deleteCentre(int centreId);

	/**
	 * Get all gym centres owned by a specific owner
	 * 
	 * @param ownerId The owner's identifier (email or PAN card)
	 * @return List of gym centres
	 */
	public List<GymCentre> getOwnerCentres(String ownerId);

	/**
	 * View all centres
	 * 
	 * @return List of all gym centres
	 */
	public List<GymCentre> getAllCentres();
}
