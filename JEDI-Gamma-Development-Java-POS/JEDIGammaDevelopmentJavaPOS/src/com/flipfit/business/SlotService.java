package com.flipfit.business;

import com.flipfit.bean.Slot;
import java.util.List;

public interface SlotService {
    List<Slot> getAllSlots();
    int getAvailableSlots(int slotId);
}
