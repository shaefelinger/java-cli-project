package de.functionfactory.car;

import java.math.BigDecimal;
import java.util.Objects;

public class Car {
    private String regNumber;
    private BigDecimal rentalPricePerDay;
    private Brand brand;
    private boolean isElectric;

    public Car(
            String regNumber,
            BigDecimal rentalPricePerDay,
            Brand brand,
            boolean isElectric
    ) {
        this.regNumber = regNumber;
        this.brand = brand;
        this.isElectric = isElectric;
        this.rentalPricePerDay = rentalPricePerDay;
    }

    public String getRegNumber() {
        return regNumber;
    }

    public void setRegNumber(String regNumber) {
        this.regNumber = regNumber;
    }

    public Brand getBrand() {
        return brand;
    }

    public void setBrand(Brand brand) {
        this.brand = brand;
    }

    public boolean isElectric() {
        return isElectric;
    }

    public void setElectric(boolean electric) {
        isElectric = electric;
    }

    public BigDecimal getRentalPricePerDay() {
        return rentalPricePerDay;
    }

    public void setRentalPricePerDay(BigDecimal rentalPricePerDay) {
        this.rentalPricePerDay = rentalPricePerDay;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Car car = (Car) o;
        return isElectric == car.isElectric && Objects.equals(regNumber, car.regNumber) && Objects.equals(rentalPricePerDay, car.rentalPricePerDay) && brand == car.brand;
    }

    @Override
    public int hashCode() {
        return Objects.hash(regNumber, brand, isElectric, rentalPricePerDay);
    }

    @Override
    public String toString() {
        return "Car{" +
                "regNumber='" + regNumber + '\'' +
                ", brand='" + brand + '\'' +
                ", isElectric=" + isElectric +
                ", rentalPricePerDay=" + rentalPricePerDay +
                '}';
    }
}
