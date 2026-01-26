package com.flipfit.business;

import com.flipfit.bean.Slot;

import java.util.List;

public class SlotServiceImpl {
    private List<Slot> slots;

    public SlotServiceImpl(List<Slot> slots) {
        this.slots = slots;
    }

    public List<Slot> getAllSlots() {
        return this.slots;
    }

    public int getAvailableSlots(int slotId) {
        for(Slot s : slots) {
            if(s.getSlotId() == slotId) {
                return s.getAvailableSeats();
            }
        }
        return 0;
    }
}
