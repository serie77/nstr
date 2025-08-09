import java.time.LocalDate;
import java.util.*;

// accommodation status enum
enum AccommodationStatus {
    CLEAN("Clean"),
    DIRTY("Dirty"),
    MAINTENANCE("Maintenance");
    
    private final String displayName;
    
    AccommodationStatus(String displayName) {
        this.displayName = displayName;
    }
    
    public String getDisplayName() {
        return displayName;
    }
    
    @Override
    public String toString() {
        return displayName;
    }
}

// accommodation type enum
enum AccommodationType {
    CABIN_ACCOMMODATES_4("Cabin Accommodates 4", 4, 85.0),
    YURT_ACCOMMODATES_2("Yurt Accommodates 2", 2, 65.0),
    DOME_ACCOMMODATES_2("Dome Accommodates 2", 2, 70.0),
    AIRSTREAM_ACCOMMODATES_4("AirStream Accommodates 4", 4, 95.0);
    
    private final String description;
    private final int capacity;
    private final double pricePerNight;
    
    AccommodationType(String description, int capacity, double pricePerNight) {
        this.description = description;
        this.capacity = capacity;
        this.pricePerNight = pricePerNight;
    }
    
    public String getDescription() {
        return description;
    }
    
    public int getCapacity() {
        return capacity;
    }
    
    public double getPricePerNight() {
        return pricePerNight;
    }
    
    @Override
    public String toString() {
        return description;
    }
}

// guest class
class Guest {
    private String firstName;
    private String lastName;
    private String phoneNumber;
    
    public Guest(String firstName, String lastName, String phoneNumber) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
    }
    
    public String getFirstName() {
        return firstName;
    }
    
    public String getLastName() {
        return lastName;
    }
    
    public String getPhoneNumber() {
        return phoneNumber;
    }
    
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    
    public String getFullName() {
        return firstName + " " + lastName;
    }
    
    @Override
    public String toString() {
        return getFullName() + " (" + phoneNumber + ")";
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        
        Guest guest = (Guest) obj;
        return firstName.equals(guest.firstName) && 
               lastName.equals(guest.lastName) && 
               phoneNumber.equals(guest.phoneNumber);
    }
    
    @Override
    public int hashCode() {
        return firstName.hashCode() + lastName.hashCode() + phoneNumber.hashCode();
    }
}

// accommodation class
class Accommodation {
    private int id;
    private AccommodationType type;
    private AccommodationStatus status;
    private int numCurrentGuests;
    private boolean breakfastRequired;
    
    public Accommodation(int id, AccommodationType type) {
        this.id = id;
        this.type = type;
        this.status = AccommodationStatus.CLEAN;
        this.numCurrentGuests = 0;
        this.breakfastRequired = false;
    }
    
    public int getId() {
        return id;
    }
    
    public AccommodationType getType() {
        return type;
    }
    
    public AccommodationStatus getStatus() {
        return status;
    }
    
    public int getNumCurrentGuests() {
        return numCurrentGuests;
    }
    
    public boolean isBreakfastRequired() {
        return breakfastRequired;
    }
    
    public int getCapacity() {
        return type.getCapacity();
    }
    
    public double getPricePerNight() {
        return type.getPricePerNight();
    }
    
    public void setStatus(AccommodationStatus status) {
        this.status = status;
    }
    
    public void setNumCurrentGuests(int numCurrentGuests) {
        this.numCurrentGuests = numCurrentGuests;
    }
    
    public void setBreakfastRequired(boolean breakfastRequired) {
        this.breakfastRequired = breakfastRequired;
    }
    
    public boolean isOccupied() {
        return numCurrentGuests > 0;
    }
    
    public boolean isAvailable() {
        return !isOccupied() && status == AccommodationStatus.CLEAN;
    }
    
    public String getOccupancyStatus() {
        return isOccupied() ? "Occupied" : "Unoccupied";
    }
    
    public String getAvailabilityStatus() {
        return isAvailable() ? "Available" : "Unavailable";
    }
    
    public int getBreakfastCount() {
        return breakfastRequired ? numCurrentGuests : 0;
    }
    
    @Override
    public String toString() {
        return "Accommodation " + id + " (" + type.getDescription() + ")";
    }
}

// booking class
class Booking {
    private static int nextBookingId = 1;
    
    private int bookingId;
    private Guest primaryGuest;
    private LocalDate checkInDate;
    private int numberOfNights;
    private int numberOfGuests;
    private boolean breakfastRequired;
    private Accommodation accommodation;
    
    public Booking(Guest primaryGuest, LocalDate checkInDate, int numberOfNights, 
                   int numberOfGuests, boolean breakfastRequired) {
        this.bookingId = nextBookingId++;
        this.primaryGuest = primaryGuest;
        this.checkInDate = checkInDate;
        this.numberOfNights = numberOfNights;
        this.numberOfGuests = numberOfGuests;
        this.breakfastRequired = breakfastRequired;
    }
    
    public int getBookingId() {
        return bookingId;
    }
    
    public Guest getPrimaryGuest() {
        return primaryGuest;
    }
    
    public LocalDate getCheckInDate() {
        return checkInDate;
    }
    
    public int getNumberOfNights() {
        return numberOfNights;
    }
    
    public int getNumberOfGuests() {
        return numberOfGuests;
    }
    
    public boolean isBreakfastRequired() {
        return breakfastRequired;
    }
    
    public Accommodation getAccommodation() {
        return accommodation;
    }
    
    public LocalDate getCheckOutDate() {
        return checkInDate.plusDays(numberOfNights);
    }
    
    public void setAccommodation(Accommodation accommodation) {
        this.accommodation = accommodation;
    }
    
    public double calculateTotalCost() {
        if (accommodation != null) {
            return accommodation.getPricePerNight() * numberOfNights;
        }
        return 0.0;
    }
    
    public int getBreakfastCount() {
        return breakfastRequired ? numberOfGuests : 0;
    }
    
    @Override
    public String toString() {
        return "Booking " + bookingId + " - " + primaryGuest.getFullName() + 
               " (" + checkInDate + " for " + numberOfNights + " nights)";
    }
}

// campsite manager class
class CampsiteManager {
    private List<Accommodation> accommodations;
    private List<Booking> activeBookings;
    private List<Booking> bookingHistory;
    
    public CampsiteManager() {
        this.accommodations = new ArrayList<>();
        this.activeBookings = new ArrayList<>();
        this.bookingHistory = new ArrayList<>();
        initializeAccommodations();
    }
    
    private void initializeAccommodations() {
        accommodations.add(new Accommodation(1, AccommodationType.CABIN_ACCOMMODATES_4));
        accommodations.add(new Accommodation(2, AccommodationType.CABIN_ACCOMMODATES_4));
        accommodations.add(new Accommodation(3, AccommodationType.CABIN_ACCOMMODATES_4));
        accommodations.add(new Accommodation(4, AccommodationType.CABIN_ACCOMMODATES_4));
        
        accommodations.add(new Accommodation(5, AccommodationType.YURT_ACCOMMODATES_2));
        accommodations.add(new Accommodation(6, AccommodationType.YURT_ACCOMMODATES_2));
        
        accommodations.add(new Accommodation(7, AccommodationType.DOME_ACCOMMODATES_2));
        accommodations.add(new Accommodation(8, AccommodationType.DOME_ACCOMMODATES_2));
        
        accommodations.add(new Accommodation(9, AccommodationType.AIRSTREAM_ACCOMMODATES_4));
        accommodations.add(new Accommodation(10, AccommodationType.AIRSTREAM_ACCOMMODATES_4));
    }
    
    public List<Accommodation> getAccommodations() {
        return new ArrayList<>(accommodations);
    }
    
    public List<Booking> getActiveBookings() {
        return new ArrayList<>(activeBookings);
    }
    
    public Accommodation getAccommodationById(int id) {
        return accommodations.stream()
                .filter(acc -> acc.getId() == id)
                .findFirst()
                .orElse(null);
    }
    
    public boolean checkInGuest(int accommodationId, Guest guest, LocalDate checkInDate, 
                               int numberOfNights, int numberOfGuests, boolean breakfastRequired) {
        
        Accommodation accommodation = getAccommodationById(accommodationId);
        
        if (accommodation == null) {
            return false;
        }
        
        if (!accommodation.isAvailable()) {
            return false;
        }
        
        if (numberOfGuests > accommodation.getCapacity()) {
            return false;
        }
        
        Booking booking = new Booking(guest, checkInDate, numberOfNights, 
                                    numberOfGuests, breakfastRequired);
        booking.setAccommodation(accommodation);
        
        accommodation.setNumCurrentGuests(numberOfGuests);
        accommodation.setBreakfastRequired(breakfastRequired);
        
        activeBookings.add(booking);
        
        return true;
    }
    
    public boolean checkOutGuest(int accommodationId) {
        Accommodation accommodation = getAccommodationById(accommodationId);
        
        if (accommodation == null || !accommodation.isOccupied()) {
            return false;
        }
        
        Booking bookingToRemove = activeBookings.stream()
                .filter(booking -> booking.getAccommodation().getId() == accommodationId)
                .findFirst()
                .orElse(null);
        
        if (bookingToRemove != null) {
            activeBookings.remove(bookingToRemove);
            bookingHistory.add(bookingToRemove);
        }
        
        accommodation.setNumCurrentGuests(0);
        accommodation.setBreakfastRequired(false);
        accommodation.setStatus(AccommodationStatus.DIRTY);
        
        return true;
    }
    
    public boolean updateAccommodationStatus(int accommodationId, AccommodationStatus newStatus) {
        Accommodation accommodation = getAccommodationById(accommodationId);
        
        if (accommodation == null) {
            return false;
        }
        
        accommodation.setStatus(newStatus);
        return true;
    }
    
    public int getTotalBreakfastsRequired() {
        return accommodations.stream()
                .mapToInt(Accommodation::getBreakfastCount)
                .sum();
    }
    
    public int getAccommodationsRequiringCleaning() {
        return (int) accommodations.stream()
                .filter(acc -> acc.getStatus() == AccommodationStatus.DIRTY)
                .count();
    }
    
    public int getAccommodationsInMaintenance() {
        return (int) accommodations.stream()
                .filter(acc -> acc.getStatus() == AccommodationStatus.MAINTENANCE)
                .count();
    }
    
    public int getOccupiedAccommodations() {
        return (int) accommodations.stream()
                .filter(Accommodation::isOccupied)
                .count();
    }
    
    public int getAvailableAccommodations() {
        return (int) accommodations.stream()
                .filter(Accommodation::isAvailable)
                .count();
    }
}

// main class for testing
public class Main {
    public static void main(String[] args) {
        System.out.println("=== Cedar Woods System Test ===");
        
        CampsiteManager manager = new CampsiteManager();
        
        System.out.println("Total accommodations: " + manager.getAccommodations().size());
        System.out.println("Available: " + manager.getAvailableAccommodations());
        
        Guest guest1 = new Guest("John", "Smith", "07123456789");
        Guest guest2 = new Guest("Jane", "Doe", "07987654321");
        
        System.out.println("\nCreated guests:");
        System.out.println("- " + guest1.getFullName());
        System.out.println("- " + guest2.getFullName());
        
        System.out.println("\n=== Testing Check-ins ===");
        boolean checkIn1 = manager.checkInGuest(1, guest1, LocalDate.now(), 2, 2, true);
        System.out.println("Guest 1 check-in to accommodation 1: " + checkIn1);
        
        boolean checkIn2 = manager.checkInGuest(5, guest2, LocalDate.now(), 1, 1, false);
        System.out.println("Guest 2 check-in to accommodation 5: " + checkIn2);
        
        System.out.println("\n=== Current Statistics ===");
        System.out.println("Breakfasts required: " + manager.getTotalBreakfastsRequired());
        System.out.println("Occupied accommodations: " + manager.getOccupiedAccommodations());
        System.out.println("Available accommodations: " + manager.getAvailableAccommodations());
        
        System.out.println("\n=== Testing Check-out ===");
        boolean checkOut = manager.checkOutGuest(1);
        System.out.println("Guest 1 check-out: " + checkOut);
        
        System.out.println("\n=== Final Statistics ===");
        System.out.println("Occupied accommodations: " + manager.getOccupiedAccommodations());
        System.out.println("Accommodations requiring cleaning: " + manager.getAccommodationsRequiringCleaning());
        System.out.println("Available accommodations: " + manager.getAvailableAccommodations());
        
        System.out.println("\n=== Test Complete ===");
    }
} 