import java.util.List;

public class NearestAvailableSpotStrategy implements ParkingSpotAllocationStrategy {
    
    @Override
    public ParkingSpot allocateSpot(List<ParkingSpot> availableSpots, Vehicle vehicle) {
        ParkingSpotType requiredType = getRequiredSpotType(vehicle.getType());
        
        // Find the nearest available spot (lowest floor, lowest spot ID)
        return availableSpots.stream()
                .filter(spot -> spot.isAvailable() && isSpotSuitable(spot.getType(), requiredType))
                .sorted((s1, s2) -> {
                    int floorCompare = Integer.compare(s1.getFloor(), s2.getFloor());
                    if (floorCompare != 0) {
                        return floorCompare;
                    }
                    return Integer.compare(s1.getSpotId(), s2.getSpotId());
                })
                .findFirst()
                .orElse(null);
    }

    private ParkingSpotType getRequiredSpotType(VehicleType vehicleType) {
        switch (vehicleType) {
            case MOTORCYCLE:
                return ParkingSpotType.MOTORCYCLE;
            case CAR:
                return ParkingSpotType.COMPACT;
            case BUS:
                return ParkingSpotType.LARGE;
            default:
                return null;
        }
    }

    private boolean isSpotSuitable(ParkingSpotType spotType, ParkingSpotType requiredType) {
        if (spotType == requiredType) {
            return true;
        }
        
        // Larger spots can accommodate smaller vehicles
        if (requiredType == ParkingSpotType.MOTORCYCLE) {
            return spotType == ParkingSpotType.COMPACT || spotType == ParkingSpotType.LARGE;
        }
        
        if (requiredType == ParkingSpotType.COMPACT) {
            return spotType == ParkingSpotType.LARGE;
        }
        
        return false;
    }
}