public interface FeeCalculationStrategy {
    double calculateFee(VehicleType vehicleType, long durationInMinutes);
}