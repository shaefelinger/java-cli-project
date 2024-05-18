package de.functionfactory.booking;

import de.functionfactory.car.Car;
import de.functionfactory.user.User;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

public class CarBooking {
    private UUID uuid;
    private Car car;
    private User user;
    private LocalDateTime bookingTime;
    private boolean isCanceled;

    public CarBooking(UUID uuid, Car car, User user, LocalDateTime bookingTime, boolean isCanceled) {
        this.uuid = uuid;
        this.car = car;
        this.user = user;
        this.bookingTime = bookingTime;
        this.isCanceled = isCanceled;
    }
    public CarBooking(UUID uuid, Car car, User user, LocalDateTime bookingTime) {
        this.uuid = uuid;
        this.car = car;
        this.user = user;
        this.bookingTime = bookingTime;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public LocalDateTime getBookingTime() {
        return bookingTime;
    }

    public void setBookingTime(LocalDateTime bookingTime) {
        this.bookingTime = bookingTime;
    }

    public boolean isCanceled() {
        return isCanceled;
    }

    public void setCanceled(boolean canceled) {
        isCanceled = canceled;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CarBooking that = (CarBooking) o;
        return isCanceled == that.isCanceled && Objects.equals(uuid, that.uuid) && Objects.equals(car, that.car) && Objects.equals(user, that.user) && Objects.equals(bookingTime, that.bookingTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid, car, user, bookingTime, isCanceled);
    }

    @Override
    public String toString() {
        return "CarBooking{" +
                "uuid=" + uuid +
                ", car=" + car +
                ", user=" + user +
                ", bookingTime=" + bookingTime +
                ", isCanceled=" + isCanceled +
                '}';
    }
}
