package com.flipfit.business;

import com.flipfit.bean.WaitingList;
import java.util.HashMap;
import java.util.Map;

/**
 * Implementation of Waiting List Business logic.
 * This class interacts with the hardcoded "Database" for waitlist management.
 */
public class WaitingListBusinessImpl implements WaitingListBusiness {

    // Hardcoded Collection acting as our persistence layer
    // Key: slotId, Value: WaitingList object
    private static Map<Integer, WaitingList> waitlistDb = new HashMap<>();

    @Override
    public void addCustomerToWaitlist(int userId, int slotId, int centreId) {
        // Retrieve the waitlist for this slot
        WaitingList waitlist = waitlistDb.get(slotId);

        // If it doesn't exist in the DB yet, initialize it
        if (waitlist == null) {
            // Using your new constructor: WaitingList(waitListId, slotId)
            int newWaitListId = waitlistDb.size() + 1;
            waitlist = new WaitingList(newWaitListId, slotId);
            waitlistDb.put(slotId, waitlist);
        }

        // Use the polished addToQueue method which handles duplicate checks
        waitlist.addToQueue(userId);
    }

    @Override
    public void promoteCustomerFromWaitlist(int slotId) {
        WaitingList waitlist = waitlistDb.get(slotId);

        // Check if waitlist exists and has users
        if (waitlist != null && waitlist.getQueueSize() > 0) {
            Integer nextUser = waitlist.removeFromQueue();
            System.out.println("Promotion Logic: User " + nextUser +
                    " removed from Waitlist for Slot " + slotId +
                    " and moved to active booking.");
            // Note: In a full system, you would call BookingBusiness.createBooking() here
        } else {
            System.out.println("Waitlist is empty for Slot ID: " + slotId);
        }
    }

    @Override
    public WaitingList getWaitingListStatus(int slotId) {
        // Direct retrieval from our hardcoded map
        if (waitlistDb.containsKey(slotId)) {
            return waitlistDb.get(slotId);
        }

        System.out.println("Log: No active waitlist found for Slot: " + slotId);
        return null;
    }
}