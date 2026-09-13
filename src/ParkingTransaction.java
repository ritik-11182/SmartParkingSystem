import java.time.LocalDateTime;
import java.time.Duration;

public class ParkingTransaction {
    private String transactionId;
    private Vehicle vehicle;
    private ParkingSpot parkingSpot;
    private LocalDateTime entryTime;
    private LocalDateTime exitTime;
    private double fee;

    public ParkingTransaction(String transactionId, Vehicle vehicle, ParkingSpot parkingSpot) {
        this.transactionId = transactionId;
        this.vehicle = vehicle;
        this.parkingSpot = parkingSpot;
        this.entryTime = LocalDateTime.now();
        this.exitTime = null;
        this.fee = 0.0;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public ParkingSpot getParkingSpot() {
        return parkingSpot;
    }

    public LocalDateTime getEntryTime() {
        return entryTime;
    }

    public LocalDateTime getExitTime() {
        return exitTime;
    }

    public double getFee() {
        return fee;
    }

    public void setExitTime(LocalDateTime exitTime) {
        this.exitTime = exitTime;
    }

    public void setFee(double fee) {
        this.fee = fee;
    }

    public long getDurationInMinutes() {
        if (exitTime == null) {
            return Duration.between(entryTime, LocalDateTime.now()).toMinutes();
        }
        return Duration.between(entryTime, exitTime).toMinutes();
    }

    @Override
    public String toString() {
        return "ParkingTransaction{" +
                "transactionId='" + transactionId + '\'' +
                ", vehicle=" + vehicle +
                ", parkingSpot=" + parkingSpot +
                ", entryTime=" + entryTime +
                ", exitTime=" + exitTime +
                ", fee=" + fee +
                '}';
    }
}