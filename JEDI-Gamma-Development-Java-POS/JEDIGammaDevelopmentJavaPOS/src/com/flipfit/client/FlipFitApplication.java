package com.flipfit.client;

import com.flipfit.bean.*;
import com.flipfit.business.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Main Client Application for FlipFit
 */
public class FlipFitApplication {
    private static final Scanner scanner = new Scanner(System.in);
    private static final BookingBusinessService bookingBusiness = new BookingBusinessService();
    private static final UserBusiness userBusiness = new UserBusinessImpl();
    private static final GymOwnerBusiness gymOwnerBusiness = new GymOwnerBusinessImpl();
    private static int nextUserId = 1;

    private static final List<Slot> masterSlot = new ArrayList<>();
    private static final GymCentre gc1 = new GymCentre(1, "Mahadevapura (Default)", "06:00 - 21:00", 500);

    public static void main(String[] args) {
        initializeData();

        System.out.println("==========================================");
        System.out.println("      WELCOME TO FLIPFIT SYSTEMS");
        System.out.println("==========================================");

        mainPage();
    }

    private static void mainPage() {
        System.out.println("\n--- LANDING PAGE ---");
        System.out.println("1. Login\n2. Registration\n3. Exit");
        System.out.print("Choose option: ");
        int portalOption = scanner.nextInt();

        switch (portalOption) {
            case 1:
                login();
                break;
            case 2:
                registration();
                break;
            case 3:
                System.out.println("Thanks for visiting! Stay Healthy.");
                System.exit(0);
            default:
                System.out.println("Invalid choice. Try again.");
        }
        mainPage();
    }

    private static void registration() {
        System.out.println("\n--- REGISTRATION ---");
        System.out.print("Enter Email: ");
        String regEmail = scanner.next();
        System.out.print("Enter Password: ");
        String regPass = scanner.next();

        String regUserId = String.valueOf(nextUserId++);

        System.out.println("Choose Role: 1. GymAdmin 2. GymCustomer 3. GymOwner");
        int roleChoice = scanner.nextInt();
        String role;
        if (roleChoice == 1) {
            role = "GymAdmin";
        } else if (roleChoice == 3) {
            role = "GymOwner";
        } else {
            role = "GymCustomer";
        }

        User newUser = new User();
        newUser.setEmail(regEmail);
        newUser.setPassword(regPass);
        newUser.setUserId(regUserId);
        newUser.setRole(role);

        userBusiness.register(newUser);
        System.out.println("\n[SUCCESS] Registered as " + role + " with User ID: " + regUserId + "! Please Login now.");
    }

    private static void login() {
        System.out.println("\n--- LOGIN ---");
        System.out.print("Enter Email: ");
        String email = scanner.next();
        System.out.print("Enter Password: ");
        String password = scanner.next();

        if (userBusiness.login(email, password)) {
            String role;
            if (email.toLowerCase().contains("admin")) {
                role = "GymAdmin";
            } else if (email.toLowerCase().contains("owner")) {
                role = "GymOwner";
            } else {
                role = "GymCustomer";
            }

            System.out.println("\nLogin Successful! Welcome, " + role);

            if (role.equals("GymAdmin")) {
                adminMenu();
            } else if (role.equals("GymOwner")) {
                gymOwnerMenu(email);
            } else {
                customerMenu();
            }
        } else {
            System.out.println("\n[ERROR] Invalid Credentials.");
        }
    }

    private static void customerMenu() {
        GymCentre selectedCentre = null;
        boolean loggedIn = true;

        while (loggedIn) {
            System.out.println("\n--- CUSTOMER MENU ---");
            System.out.println("1. Select/Change Gym Centre");
            System.out.println("2. View Available Slots");
            System.out.println("3. Book a Slot");
            System.out.println("4. Cancel a Booking");
            System.out.println("5. Logout");
            System.out.print("Select an option: ");

            int choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    selectedCentre = selectGymCentre();
                    break;
                case 2:
                    if (selectedCentre == null) {
                        System.out.println("\n[ERROR] Please select a gym centre first.");
                    } else {
                        displayMasterSlots(selectedCentre.getSlots());
                    }
                    break;
                case 3:
                    if (selectedCentre == null) {
                        System.out.println("\n[ERROR] Please select a gym centre first.");
                    } else {
                        handleBooking(selectedCentre);
                    }
                    break;
                case 4:
                    if (selectedCentre == null) {
                        System.out.println("\n[ERROR] Please select a gym centre first.");
                    } else {
                        handleCancellation(selectedCentre);
                    }
                    break;
                case 5:
                    loggedIn = false;
                    System.out.println("Logging out...");
                    break;
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

    private static void adminMenu() {
        boolean loggedIn = true;
        while (loggedIn) {
            System.out.println("\n--- ADMIN MENU ---");
            System.out.println("1. View System Snapshot (Centre-wise)");
            System.out.println("2. Logout");
            System.out.print("Select an option: ");

            int choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    // Fetch dynamic list of all centres
                    List<GymCentre> allCentres = gymOwnerBusiness.getAllCentres();
                    if (allCentres.isEmpty()) {
                        allCentres = new ArrayList<>();
                        allCentres.add(gc1); // Fallback to default centre
                    }
                    displaySystemState(allCentres, bookingBusiness.getAllBookings());
                    break;
                case 2:
                    loggedIn = false;
                    System.out.println("Logging out Admin...");
                    break;
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

    private static void gymOwnerMenu(String ownerEmail) {
        GymOwnerMenu ownerMenu = new GymOwnerMenu(ownerEmail);
        ownerMenu.showMenu();
    }

    private static GymCentre selectGymCentre() {
        List<GymCentre> allCentres = gymOwnerBusiness.getAllCentres();
        if (allCentres.isEmpty()) {
            allCentres = new ArrayList<>();
            allCentres.add(gc1);
        }

        System.out.println("\nAvailable Gym Centres:");
        System.out.println("---------------------------------------------------------------------------------");
        System.out.printf("%-5s %-25s %-20s %-15s\n", "ID", "Centre Name", "Hours", "Price");
        for (GymCentre centre : allCentres) {
            System.out.printf("%-5d %-25s %-20s Rs. %-11d\n",
                    centre.getCentreId(), centre.getCentreName(), centre.getOpenTime(), centre.getPrice());
        }

        System.out.print("\nEnter Centre ID: ");
        int centreId = scanner.nextInt();
        for (GymCentre centre : allCentres) {
            if (centre.getCentreId() == centreId)
                return centre;
        }
        System.out.println("[ERROR] Invalid ID.");
        return null;
    }

    private static void handleBooking(GymCentre selectedCentre) {
        System.out.print("Confirm User ID: ");
        int userId = scanner.nextInt();
        System.out.print("Enter Slot ID to book: ");
        int slotId = scanner.nextInt();

        Slot selectedSlot = findSlotById(selectedCentre.getSlots(), slotId);
        if (selectedSlot != null) {
            bookingBusiness.createBooking(userId, selectedCentre.getCentreId(), selectedSlot, 1,
                    selectedCentre.getPrice());
        } else {
            System.out.println("\n[ERROR] Invalid Slot ID.");
        }
    }

    private static void handleCancellation(GymCentre selectedCentre) {
        System.out.print("Enter Booking ID to cancel: ");
        int bId = scanner.nextInt();
        boolean result = bookingBusiness.cancelBooking(bId, selectedCentre.getSlots());
        if (!result)
            System.out.println("\n[ERROR] Cancellation failed.");
    }

    private static void initializeData() {
        masterSlot.add(new Slot(1, 6, 2));
        masterSlot.add(new Slot(2, 7, 2));
        masterSlot.add(new Slot(3, 8, 2));
        for (Slot s : masterSlot)
            gc1.addSlot(s);
    }

    private static Slot findSlotById(List<Slot> slots, int id) {
        for (Slot s : slots) {
            if (s.getSlotId() == id)
                return s;
        }
        return null;
    }

    /**
     * Display center-wise snapshot for Admin
     */
    private static void displaySystemState(List<GymCentre> centres, List<Booking> bookings) {
        System.out.println("\n" + "=".repeat(70));
        System.out.println("                ADMIN SYSTEM SNAPSHOT");
        System.out.println("=".repeat(70));

        for (GymCentre centre : centres) {
            System.out.println("\n>>> CENTRE: " + centre.getCentreName() + " (ID: " + centre.getCentreId() + ")");
            displayMasterSlots(centre.getSlots());
            displayWaitingLists(centre.getSlots());
            System.out.println("-".repeat(30));
        }

        displayBookings(bookings);
        System.out.println("=".repeat(70));
    }

    private static void displayMasterSlots(List<Slot> slots) {
        System.out.println("[SLOT AVAILABILITY]");
        System.out.printf("%-10s %-10s %-12s %-15s\n", "ID", "Time", "Category", "Available");
        for (Slot s : slots) {
            String cat = (s.getStartTime() < 12) ? "Morning" : "Evening";
            System.out.printf("%-10d %-10d %-12s %-1d/%-1d Seats\n",
                    s.getSlotId(), s.getStartTime(), cat, s.getAvailableSeats(), s.getMaximumSeats());
        }
    }

    private static void displayWaitingLists(List<Slot> slots) {
        System.out.println("[WAITING LISTS]");
        for (Slot s : slots) {
            WaitingList wl = s.getWaitingList();
            System.out.printf("Slot %-2d | Queue: %-20s | Count: %-2d\n",
                    s.getSlotId(), wl.getWaitingQueue().toString(), wl.getQueueSize());
        }
    }

    private static void displayBookings(List<Booking> bookings) {
        System.out.println("\n[ACTIVE BOOKINGS]");
        if (bookings.isEmpty()) {
            System.out.println("No active bookings.");
            return;
        }
        System.out.printf("%-12s %-10s %-10s %-10s\n", "Booking ID", "User ID", "Slot ID", "Cost");
        for (Booking b : bookings) {
            System.out.printf("%-12d %-10d %-10d Rs. %-10.2f\n",
                    b.getBookingId(), b.getUserId(), b.getSlotId(), b.getTotalCost());
        }
    }
}