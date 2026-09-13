public class Main {
    public static void main(String[] args) {
        // Initialize Parking Lot (Singleton)
        ParkingLot parkingLot = ParkingLot.getInstance();

        // Setup parking lot with multiple floors and spots
        System.out.println("=== Initializing Parking Lot ===");

        // Floor 1: 5 motorcycle spots, 10 compact spots, 5 large spots
        for (int i = 1; i <= 5; i++) {
            parkingLot.addParkingSpot(new ParkingSpot(i, 1, ParkingSpotType.MOTORCYCLE));
        }
        for (int i = 6; i <= 15; i++) {
            parkingLot.addParkingSpot(new ParkingSpot(i, 1, ParkingSpotType.COMPACT));
        }
        for (int i = 16; i <= 20; i++) {
            parkingLot.addParkingSpot(new ParkingSpot(i, 1, ParkingSpotType.LARGE));
        }

        // Floor 2: 5 motorcycle spots, 10 compact spots, 5 large spots
        for (int i = 21; i <= 25; i++) {
            parkingLot.addParkingSpot(new ParkingSpot(i, 2, ParkingSpotType.MOTORCYCLE));
        }
        for (int i = 26; i <= 35; i++) {
            parkingLot.addParkingSpot(new ParkingSpot(i, 2, ParkingSpotType.COMPACT));
        }
        for (int i = 36; i <= 40; i++) {
            parkingLot.addParkingSpot(new ParkingSpot(i, 2, ParkingSpotType.LARGE));
        }

        System.out.println("Parking Lot initialized with " + parkingLot.getTotalSpots() + " spots across 2 floors.");
        System.out.println();

        // Display initial availability
        parkingLot.displayAvailability();

        // Test Case 1: Park different types of vehicles
        System.out.println("=== Test Case 1: Parking Vehicles ===");
        Vehicle motorcycle1 = new Vehicle("MOTO-001", VehicleType.MOTORCYCLE);
        Vehicle car1 = new Vehicle("CAR-001", VehicleType.CAR);
        Vehicle car2 = new Vehicle("CAR-002", VehicleType.CAR);
        Vehicle bus1 = new Vehicle("BUS-001", VehicleType.BUS);

        parkingLot.parkVehicle(motorcycle1);
        System.out.println();
        parkingLot.parkVehicle(car1);
        System.out.println();
        parkingLot.parkVehicle(car2);
        System.out.println();
        parkingLot.parkVehicle(bus1);
        System.out.println();

        // Display availability after parking
        parkingLot.displayAvailability();

        // Test Case 2: Try to park already parked vehicle
        System.out.println("=== Test Case 2: Duplicate Parking Attempt ===");
        parkingLot.parkVehicle(car1);
        System.out.println();

        // Test Case 3: Simulate some time passing and exit vehicles
        System.out.println("=== Test Case 3: Vehicle Exit and Fee Calculation ===");

        // Simulate time by waiting (optional - for demo purposes)
        try {
            System.out.println("Simulating parking duration...");
            Thread.sleep(2000); // 2 seconds to simulate time passing
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        parkingLot.exitVehicle("MOTO-001");
        System.out.println();
        parkingLot.exitVehicle("CAR-001");
        System.out.println();

        // Display availability after exits
        parkingLot.displayAvailability();

        // Test Case 4: Park more vehicles in freed spots
        System.out.println("=== Test Case 4: Parking in Freed Spots ===");
        Vehicle motorcycle2 = new Vehicle("MOTO-002", VehicleType.MOTORCYCLE);
        Vehicle car3 = new Vehicle("CAR-003", VehicleType.CAR);

        parkingLot.parkVehicle(motorcycle2);
        System.out.println();
        parkingLot.parkVehicle(car3);
        System.out.println();

        // Display final availability
        parkingLot.displayAvailability();

        // Test Case 5: Exit remaining vehicles
        System.out.println("=== Test Case 5: Exit Remaining Vehicles ===");
        parkingLot.exitVehicle("CAR-002");
        System.out.println();
        parkingLot.exitVehicle("BUS-001");
        System.out.println();
        parkingLot.exitVehicle("MOTO-002");
        System.out.println();
        parkingLot.exitVehicle("CAR-003");
        System.out.println();

        // Display final availability
        parkingLot.displayAvailability();

        // Test Case 6: Try to exit non-existent vehicle
        System.out.println("=== Test Case 6: Exit Non-Existent Vehicle ===");
        parkingLot.exitVehicle("INVALID-001");
        System.out.println();

        System.out.println("=== Parking Lot System Demo Completed ===");
    }
}