public class HourlyFeeCalculationStrategy implements FeeCalculationStrategy {
    private static final double MOTORCYCLE_RATE_PER_HOUR = 10.0;
    private static final double CAR_RATE_PER_HOUR = 20.0;
    private static final double BUS_RATE_PER_HOUR = 40.0;

    @Override
    public double calculateFee(VehicleType vehicleType, long durationInMinutes) {
        double hours = Math.ceil(durationInMinutes / 60.0);
        
        switch (vehicleType) {
            case MOTORCYCLE:
                return hours * MOTORCYCLE_RATE_PER_HOUR;
            case CAR:
                return hours * CAR_RATE_PER_HOUR;
            case BUS:
                return hours * BUS_RATE_PER_HOUR;
            default:
                return 0.0;
        }
    }
}