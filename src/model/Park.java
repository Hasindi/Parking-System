package model;

public class Park {
    private String VehicleNumber;
    private String VehicleType;
    private String Slot;
    private String ParkingTime;

    public Park() {
    }

    public Park(String vehicleNumber, String vehicleType, String slot, String parkingTime) {
        VehicleNumber = vehicleNumber;
        VehicleType = vehicleType;
        Slot = slot;
        ParkingTime = parkingTime;
    }

    public String getVehicleNumber() {
        return VehicleNumber;
    }

    public void setVehicleNumber(String vehicleNumber) {
        VehicleNumber = vehicleNumber;
    }

    public String getVehicleType() {
        return VehicleType;
    }

    public void setVehicleType(String vehicleType) {
        VehicleType = vehicleType;
    }

    public String getSlot() {
        return Slot;
    }

    public void setSlot(String slot) {
        Slot = slot;
    }

    public String getParkingTime() {
        return ParkingTime;
    }

    public void setParkingTime(String parkedTime) {
        ParkingTime = parkedTime;
    }

    @Override
    public String toString() {
        return "Park{" +
                "VehicleNumber='" + VehicleNumber + '\'' +
                ", VehicleType='" + VehicleType + '\'' +
                ", Slot='" + Slot + '\'' +
                ", ParkedTime='" + ParkingTime + '\'' +
                '}';
    }
}
