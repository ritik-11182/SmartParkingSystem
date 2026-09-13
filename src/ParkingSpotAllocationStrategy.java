import java.util.List;

public interface ParkingSpotAllocationStrategy {
    ParkingSpot allocateSpot(List<ParkingSpot> availableSpots, Vehicle vehicle);
}