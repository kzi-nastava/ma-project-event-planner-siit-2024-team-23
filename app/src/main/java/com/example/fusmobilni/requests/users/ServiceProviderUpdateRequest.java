package com.example.fusmobilni.requests.users;

public class ServiceProviderUpdateRequest extends  AuthenticatedUserUpdateRequest {
    private String companyName;
    private String companyDescription;

    public ServiceProviderUpdateRequest(String firstName, String surname, String city, String address,
                                        String streetNumber, String phoneNumber, String companyName,
                                        String companyDescription, String longitude, String latitude) {
        super(firstName, surname, city, address, phoneNumber, streetNumber, longitude, latitude);
        this.companyName = companyName;
        this.companyDescription = companyDescription;
    }

    public String getLongitude() {
        return super.getLongitude();
    }

    public void setLongitude(String longitude) {
        super.setLongitude(longitude);
    }

    public String getLatitude() {
        return super.getLatitude();
    }

    public void setLatitude(String latitude) {
        super.setLatitude(latitude);
    }

    public String getStreetNumber() {
        return super.getStreetNumber();
    }

    public void setStreetNumber(String streetNumber) {
        super.setStreetNumber(streetNumber);
    }

    public String getFirstName() {
        return super.getFirstName();
    }

    public void setFirstName(String firstName) {
        super.setFirstName(firstName);
    }

    public String getSurname() {
        return super.getSurname();
    }

    public void setSurname(String surname) {
        super.setSurname(surname);
    }

    public String getCity() {
        return super.getCity();
    }

    public void setCity(String city) {
        super.setCity(city);
    }

    public String getAddress() {
        return super.getAddress();
    }

    public void setAddress(String address) {
        super.setAddress(address);
    }

    public String getPhoneNumber() {
        return super.getPhoneNumber();
    }

    public void setPhoneNumber(String phoneNumber) {
        super.setPhoneNumber(phoneNumber);
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyDescription() {
        return companyDescription;
    }

    public void setCompanyDescription(String companyDescription) {
        this.companyDescription = companyDescription;
    }
}
