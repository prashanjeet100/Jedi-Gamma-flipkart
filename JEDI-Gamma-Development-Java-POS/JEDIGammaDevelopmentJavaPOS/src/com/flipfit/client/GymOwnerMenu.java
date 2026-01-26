package com.flipfit.client;

import com.flipfit.bean.GymCentre;
import com.flipfit.bean.Slot;
import com.flipfit.business.GymOwnerBusiness;
import com.flipfit.business.GymOwnerBusinessImpl;
import java.util.List;
import java.util.Scanner;

/**
 * Client menu for Gym Owner operations
 */
public class GymOwnerMenu {
    private static final Scanner scanner = new Scanner(System.in);
    private static final GymOwnerBusiness gymOwnerBusiness = new GymOwnerBusinessImpl();
    private String ownerEmail;

    public GymOwnerMenu(String ownerEmail) {
        this.ownerEmail = ownerEmail;
    }

    /**
     * Display the gym owner menu and handle user interactions
     */
    public void showMenu() {
        boolean loggedIn = true;

        while (loggedIn) {
            System.out.println("\n╔═══════════════════════════════════════╗");
            System.out.println("║       GYM OWNER MENU                  ║");
            System.out.println("╚═══════════════════════════════════════╝");
            System.out.println("1. Add New Gym Centre");
            System.out.println("2. View My Gym Centres");
            System.out.println("3. View All Gym Centres");
            System.out.println("4. Delete Gym Centre");
            System.out.println("5. Logout");
            System.out.print("Select an option: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    addNewCentre();
                    break;
                case 2:
                    viewMyCentres();
                    break;
                case 3:
                    viewAllCentres();
                    break;
                case 4:
                    deleteCentre();
                    break;
                case 5:
                    loggedIn = false;
                    System.out.println("\n[INFO] Logging out from Gym Owner menu...");
                    break;
                default:
                    System.out.println("\n[ERROR] Invalid choice. Please try again.");
            }
        }
    }

    /**
     * Add a new gym centre
     */
    private void addNewCentre() {
        System.out.println("\n--- ADD NEW GYM CENTRE ---");

        System.out.print("Enter Centre Name: ");
        String centreName = scanner.nextLine();

        System.out.print("Enter Operating Hours (e.g., 06:00 - 21:00): ");
        String openTime = scanner.nextLine();

        System.out.print("Enter Price per Slot: ");
        int price = scanner.nextInt();

        System.out.print("Enter Number of Slots to Add: ");
        int numSlots = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        // Create new gym centre (ID will be auto-assigned)
        GymCentre newCentre = new GymCentre(0, centreName, openTime, price);

        // Add slots to the centre
        if (numSlots > 0) {
            System.out.println("\n--- ADDING SLOTS TO GYM CENTRE ---");
            for (int i = 1; i <= numSlots; i++) {
                System.out.println("\nSlot " + i + " of " + numSlots + ":");

                System.out.print("  Enter Start Time (24-hour format, e.g., 6 for 6AM, 18 for 6PM): ");
                int startTime = scanner.nextInt();

                System.out.print("  Enter Maximum Capacity (number of seats): ");
                int maxCapacity = scanner.nextInt();
                scanner.nextLine(); // Consume newline

                // Create and add slot to the centre
                Slot newSlot = new Slot(i, startTime, maxCapacity);
                newCentre.addSlot(newSlot);
            }
        }

        // Use the overloaded method that tracks ownership
        if (gymOwnerBusiness instanceof GymOwnerBusinessImpl) {
            ((GymOwnerBusinessImpl) gymOwnerBusiness).addCentre(newCentre, ownerEmail);
        } else {
            gymOwnerBusiness.addCentre(newCentre);
        }

        System.out.println(
                "\n✓ Gym Centre '" + centreName + "' has been added successfully with " + numSlots + " slot(s)!");
    }

    /**
     * View centres owned by current owner
     */
    private void viewMyCentres() {
        System.out.println("\n--- MY GYM CENTRES ---");
        List<GymCentre> myCentres = gymOwnerBusiness.getOwnerCentres(ownerEmail);

        if (myCentres.isEmpty()) {
            System.out.println("No gym centres found. Add one to get started!");
            return;
        }

        System.out.println("\n╔═══════════════════════════════════════════════════════════════════════╗");
        System.out.printf("║ %-10s %-20s %-20s %-12s ║\n", "ID", "Centre Name", "Operating Hours", "Price/Slot");
        System.out.println("╠═══════════════════════════════════════════════════════════════════════╣");

        for (GymCentre centre : myCentres) {
            System.out.printf("║ %-10d %-20s %-20s Rs. %-8d ║\n",
                    centre.getCentreId(),
                    centre.getCentreName(),
                    centre.getOpenTime(),
                    centre.getPrice());
        }
        System.out.println("╚═══════════════════════════════════════════════════════════════════════╝");
    }

    /**
     * View all gym centres in the system
     */
    private void viewAllCentres() {
        System.out.println("\n--- ALL GYM CENTRES ---");
        List<GymCentre> allCentres = gymOwnerBusiness.getAllCentres();

        if (allCentres.isEmpty()) {
            System.out.println("No gym centres available in the system.");
            return;
        }

        System.out.println("\n╔═══════════════════════════════════════════════════════════════════════╗");
        System.out.printf("║ %-10s %-20s %-20s %-12s ║\n", "ID", "Centre Name", "Operating Hours", "Price/Slot");
        System.out.println("╠═══════════════════════════════════════════════════════════════════════╣");

        for (GymCentre centre : allCentres) {
            System.out.printf("║ %-10d %-20s %-20s Rs. %-8d ║\n",
                    centre.getCentreId(),
                    centre.getCentreName(),
                    centre.getOpenTime(),
                    centre.getPrice());
        }
        System.out.println("╚═══════════════════════════════════════════════════════════════════════╝");
    }

    /**
     * Delete a gym centre
     */
    private void deleteCentre() {
        System.out.println("\n--- DELETE GYM CENTRE ---");

        // First show the owner's centres
        viewMyCentres();

        System.out.print("\nEnter Centre ID to delete (or 0 to cancel): ");
        int centreId = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        if (centreId == 0) {
            System.out.println("[INFO] Delete operation cancelled.");
            return;
        }

        // Confirm deletion
        System.out.print("Are you sure you want to delete Centre ID " + centreId + "? (yes/no): ");
        String confirmation = scanner.nextLine();

        if (confirmation.equalsIgnoreCase("yes")) {
            if (gymOwnerBusiness.deleteCentre(centreId)) {
                System.out.println("\n✓ Gym Centre deleted successfully!");
            }
        } else {
            System.out.println("[INFO] Deletion cancelled.");
        }
    }
}
