public class ParkingSpot {
    private int spotId;
    private int floor;
    private ParkingSpotType type;
    private boolean isAvailable;
    private Vehicle parkedVehicle;

    public ParkingSpot(int spotId, int floor, ParkingSpotType type) {
        this.spotId = spotId;
        this.floor = floor;
        this.type = type;
        this.isAvailable = true;
        this.parkedVehicle = null;
    }

    public int getSpotId() {
        return spotId;
    }

    public int getFloor() {
        return floor;
    }

    public ParkingSpotType getType() {
        return type;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public Vehicle getParkedVehicle() {
        return parkedVehicle;
    }

    public synchronized boolean parkVehicle(Vehicle vehicle) {
        if (isAvailable && isCompatible(vehicle.getType())) {
            this.parkedVehicle = vehicle;
            this.isAvailable = false;
            return true;
        }
        return false;
    }

    public synchronized Vehicle removeVehicle() {
        if (!isAvailable) {
            Vehicle vehicle = this.parkedVehicle;
            this.parkedVehicle = null;
            this.isAvailable = true;
            return vehicle;
        }
        return null;
    }

    private boolean isCompatible(VehicleType vehicleType) {
        switch (this.type) {
            case MOTORCYCLE:
                return vehicleType == VehicleType.MOTORCYCLE;
            case COMPACT:
                return vehicleType == VehicleType.MOTORCYCLE || vehicleType == VehicleType.CAR;
            case LARGE:
                return true; // Can accommodate all vehicle types
            default:
                return false;
        }
    }

    @Override
    public String toString() {
        return "ParkingSpot{" +
                "spotId=" + spotId +
                ", floor=" + floor +
                ", type=" + type +
                ", isAvailable=" + isAvailable +
                '}';
    }
}