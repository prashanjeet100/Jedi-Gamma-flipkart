package com.flipfit.business;

import com.flipfit.bean.GymCentre;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Implementation of GymOwnerBusiness interface
 * Manages gym centres for gym owners
 */
public class GymOwnerBusinessImpl implements GymOwnerBusiness {
	// In-memory storage: centreId -> GymCentre
	private static Map<Integer, GymCentre> allCentres = new HashMap<>();
	// Track ownership: ownerId -> List of centreIds
	private static Map<String, List<Integer>> ownerCentresMap = new HashMap<>();
	private static int nextCentreId = 1000; // Auto-increment centre IDs

	/**
	 * Add a new gym centre
	 */
	@Override
	public boolean addCentre(GymCentre gymCentre) {
		if (gymCentre == null) {
			System.out.println("[ERROR] Cannot add null centre.");
			return false;
		}

		// Assign a new centre ID if not set
		if (gymCentre.getCentreId() == 0) {
			gymCentre.setCentreId(nextCentreId++);
		}

		allCentres.put(gymCentre.getCentreId(), gymCentre);
		System.out.println("[SUCCESS] Gym Centre added with ID: " + gymCentre.getCentreId());
		return true;
	}

	/**
	 * Add a centre and associate it with an owner
	 */
	public boolean addCentre(GymCentre gymCentre, String ownerId) {
		if (addCentre(gymCentre)) {
			ownerCentresMap.computeIfAbsent(ownerId, k -> new ArrayList<>())
					.add(gymCentre.getCentreId());
			return true;
		}
		return false;
	}

	/**
	 * Delete a gym centre by its ID
	 */
	@Override
	public boolean deleteCentre(int centreId) {
		if (allCentres.containsKey(centreId)) {
			allCentres.remove(centreId);
			// Remove from ownership map
			ownerCentresMap.values().forEach(list -> list.remove(Integer.valueOf(centreId)));
			System.out.println("[SUCCESS] Gym Centre with ID " + centreId + " deleted.");
			return true;
		}
		System.out.println("[ERROR] Centre ID " + centreId + " not found.");
		return false;
	}

	/**
	 * Get all gym centres owned by a specific owner
	 */
	@Override
	public List<GymCentre> getOwnerCentres(String ownerId) {
		List<Integer> centreIds = ownerCentresMap.getOrDefault(ownerId, new ArrayList<>());
		return centreIds.stream()
				.map(allCentres::get)
				.filter(centre -> centre != null)
				.collect(Collectors.toList());
	}

	/**
	 * Get all gym centres in the system
	 */
	@Override
	public List<GymCentre> getAllCentres() {
		return new ArrayList<>(allCentres.values());
	}
}
