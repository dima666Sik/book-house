package ua.house.book.core.domain;

public enum Currency {
    USD("United States Dollar"),
    EUR("Euro"),
    GBP("British Pound Sterling"),
    JPY("Japanese Yen"),
    UAH("Ukrainian Hryvnia");

    private final String fullName;

    Currency(String fullName) {
        this.fullName = fullName;
    }

    public String getFullName() {
        return fullName;
    }
}
