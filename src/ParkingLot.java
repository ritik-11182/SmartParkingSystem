import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.time.LocalDateTime;

public class ParkingLot {
    private static ParkingLot instance;
    private List<ParkingSpot> parkingSpots;
    private Map<String, ParkingTransaction> activeTransactions;
    private List<ParkingTransaction> completedTransactions;
    private ParkingSpotAllocationStrategy allocationStrategy;
    private FeeCalculationStrategy feeCalculationStrategy;
    private AtomicInteger transactionCounter;

    private ParkingLot() {
        this.parkingSpots = new ArrayList<>();
        this.activeTransactions = new ConcurrentHashMap<>();
        this.completedTransactions = new ArrayList<>();
        this.allocationStrategy = new NearestAvailableSpotStrategy();
        this.feeCalculationStrategy = new HourlyFeeCalculationStrategy();
        this.transactionCounter = new AtomicInteger(1);
    }

    // Singleton pattern
    public static synchronized ParkingLot getInstance() {
        if (instance == null) {
            instance = new ParkingLot();
        }
        return instance;
    }

    public void addParkingSpot(ParkingSpot spot) {
        parkingSpots.add(spot);
    }

    public void setAllocationStrategy(ParkingSpotAllocationStrategy strategy) {
        this.allocationStrategy = strategy;
    }

    public void setFeeCalculationStrategy(FeeCalculationStrategy strategy) {
        this.feeCalculationStrategy = strategy;
    }

    public synchronized String parkVehicle(Vehicle vehicle) {
        // Check if vehicle is already parked
        if (activeTransactions.containsKey(vehicle.getLicensePlate())) {
            System.out.println("Vehicle " + vehicle.getLicensePlate() + " is already parked.");
            return null;
        }

        // Allocate parking spot
        ParkingSpot allocatedSpot = allocationStrategy.allocateSpot(parkingSpots, vehicle);
        
        if (allocatedSpot == null) {
            System.out.println("No available parking spot for vehicle: " + vehicle);
            return null;
        }

        // Park the vehicle
        if (allocatedSpot.parkVehicle(vehicle)) {
            String transactionId = "TXN" + transactionCounter.getAndIncrement();
            ParkingTransaction transaction = new ParkingTransaction(transactionId, vehicle, allocatedSpot);
            activeTransactions.put(vehicle.getLicensePlate(), transaction);
            
            System.out.println("Vehicle parked successfully!");
            System.out.println("Transaction ID: " + transactionId);
            System.out.println("Spot: Floor " + allocatedSpot.getFloor() + ", Spot ID " + allocatedSpot.getSpotId());
            System.out.println("Entry Time: " + transaction.getEntryTime());
            
            return transactionId;
        }

        return null;
    }

    public synchronized double exitVehicle(String licensePlate) {
        ParkingTransaction transaction = activeTransactions.get(licensePlate);
        
        if (transaction == null) {
            System.out.println("No active parking transaction found for vehicle: " + licensePlate);
            return -1;
        }

        // Set exit time
        transaction.setExitTime(LocalDateTime.now());
        
        // Calculate fee
        long duration = transaction.getDurationInMinutes();
        double fee = feeCalculationStrategy.calculateFee(transaction.getVehicle().getType(), duration);
        transaction.setFee(fee);

        // Remove vehicle from spot
        transaction.getParkingSpot().removeVehicle();

        // Move transaction to completed
        activeTransactions.remove(licensePlate);
        completedTransactions.add(transaction);

        System.out.println("Vehicle exited successfully!");
        System.out.println("Transaction ID: " + transaction.getTransactionId());
        System.out.println("Duration: " + duration + " minutes");
        System.out.println("Fee: $" + String.format("%.2f", fee));

        return fee;
    }

    public void displayAvailability() {
        Map<ParkingSpotType, Integer> availabilityMap = new HashMap<>();
        availabilityMap.put(ParkingSpotType.MOTORCYCLE, 0);
        availabilityMap.put(ParkingSpotType.COMPACT, 0);
        availabilityMap.put(ParkingSpotType.LARGE, 0);

        for (ParkingSpot spot : parkingSpots) {
            if (spot.isAvailable()) {
                availabilityMap.put(spot.getType(), availabilityMap.get(spot.getType()) + 1);
            }
        }

        System.out.println("\n=== Parking Lot Availability ===");
        System.out.println("Motorcycle Spots Available: " + availabilityMap.get(ParkingSpotType.MOTORCYCLE));
        System.out.println("Compact Spots Available: " + availabilityMap.get(ParkingSpotType.COMPACT));
        System.out.println("Large Spots Available: " + availabilityMap.get(ParkingSpotType.LARGE));
        System.out.println("================================\n");
    }

    public ParkingTransaction getActiveTransaction(String licensePlate) {
        return activeTransactions.get(licensePlate);
    }

    public List<ParkingTransaction> getCompletedTransactions() {
        return new ArrayList<>(completedTransactions);
    }

    public int getTotalSpots() {
        return parkingSpots.size();
    }

    public int getAvailableSpots() {
        return (int) parkingSpots.stream().filter(ParkingSpot::isAvailable).count();
    }
}