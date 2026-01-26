# Gym Owner Functionality Implementation

## Summary
Successfully implemented gym owner functionality in the FlipFit application. Gym owners can now login and manage their gym centres.

## New Features Added

### 1. **Gym Owner Registration**
- Added GymOwner as a role option during registration (option 3)
- Users can register as GymOwner by selecting option 3 when prompted

### 2. **Gym Owner Login**
- Email addresses containing "owner" (case-insensitive) are automatically routed to the Gym Owner menu
- Example: `owner@flipfit.com`, `gymowner123@example.com`

### 3. **Gym Owner Menu Options**
Once logged in as a gym owner, users can:
1. **Add New Gym Centre** - Create new gym centres with:
   - Centre Name
   - Operating Hours (e.g., "06:00 - 21:00")
   - Price per Slot
   
2. **View My Gym Centres** - See all gym centres owned by the logged-in owner
   
3. **View All Gym Centres** - Browse all gym centres in the system
   
4. **Delete Gym Centre** - Remove a gym centre by its ID (with confirmation prompt)
   
5. **Logout** - Return to the main landing page

## Files Modified/Created

### Modified Files:
1. **FlipFitApplication.java**
   - Added GymOwnerMenu import
   - Updated `registration()` to handle GymOwner role (option 3)
   - Updated `login()` to detect gym owner by email pattern
   - Added `gymOwnerMenu(String ownerEmail)` method

### Created/Updated Files:
2. **GymOwnerBusiness.java** (Interface)
   - `addCentre(GymCentre gymCentre)` - Add a new gym centre
   - `deleteCentre(int centreId)` - Delete a gym centre
   - `getOwnerCentres(String ownerId)` - Get centres owned by specific owner
   - `getAllCentres()` - Get all centres in the system

3. **GymOwnerBusinessImpl.java** (Implementation)
   - In-memory storage using HashMap for gym centres
   - Auto-incrementing centre IDs starting from 1000
   - Ownership tracking (ownerId -> List of centreIds)
   - Full CRUD operations for gym centres

4. **GymOwnerMenu.java** (Client UI)
   - Interactive menu with nicely formatted tables
   - Input validation and user confirmations
   - Clean separation of concerns with dedicated methods for each operation

## How to Test

### Step 1: Register as Gym Owner
```
1. Run the application
2. Choose "2. Registration"
3. Enter email (e.g., owner@flipfit.com)
4. Enter password
5. Enter user ID
6. Choose "3. GymOwner" when prompted for role
```

### Step 2: Login as Gym Owner
```
1. Choose "1. Login"
2. Enter the owner email (must contain "owner")
3. Enter password
4. You'll be redirected to the Gym Owner Menu
```

### Step 3: Add a Gym Centre
```
1. Select "1. Add New Gym Centre"
2. Enter centre name (e.g., "Downtown Fitness")
3. Enter operating hours (e.g., "05:00 - 23:00")
4. Enter price per slot (e.g., 600)
5. Centre will be added with auto-generated ID
```

### Step 4: View and Delete Centres
```
1. Select "2. View My Gym Centres" to see your centres
2. Select "4. Delete Gym Centre" to remove a centre
3. Enter the centre ID when prompted
4. Confirm deletion with "yes"
```

## Technical Notes

- **Role Detection**: Email pattern matching is used (contains "owner" for GymOwner, "admin" for GymAdmin)
- **Data Storage**: In-memory HashMap storage (data is lost when application stops)
- **ID Generation**: Centre IDs start from 1000 and auto-increment
- **Ownership Tracking**: Each gym centre is associated with the owner's email
- **User Experience**: Formatted tables with box-drawing characters for better readability

## Future Enhancements
- Persistent database storage
- Better role management (not just email pattern matching)
- Edit/Update gym centre details
- Add slots to gym centres
- View bookings per gym centre
- Revenue reports for owners
