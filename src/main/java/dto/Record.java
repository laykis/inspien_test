package dto;


import com.google.gson.annotations.SerializedName;

public class Record {
    @SerializedName("Names")
    private String names;

    @SerializedName("Phone")
    private String phone;

    @SerializedName("Email")
    private String email;

    @SerializedName("BirthDate")
    private String birthDate;

    @SerializedName("Company")
    private String company;

    @SerializedName("PersonalNumber")
    private String personalNumber;

    @SerializedName("OrganisationNumber")
    private String organisationNumber;

    @SerializedName("Country")
    private String country;

    @SerializedName("Region")
    private String region;

    @SerializedName("City")
    private String city;

    @SerializedName("Street")
    private String street;

    @SerializedName("ZipCode")
    private int zipCode;

    @SerializedName("CreditCard")
    private long creditCard;

    @SerializedName("GUID")
    private String guid;

    // Getters and Setters
    public String getNames() {
        return names;
    }

    public void setNames(String names) {
        this.names = names;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getPersonalNumber() {
        return personalNumber;
    }

    public void setPersonalNumber(String personalNumber) {
        this.personalNumber = personalNumber;
    }

    public String getOrganisationNumber() {
        return organisationNumber;
    }

    public void setOrganisationNumber(String organisationNumber) {
        this.organisationNumber = organisationNumber;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public int getZipCode() {
        return zipCode;
    }

    public void setZipCode(int zipCode) {
        this.zipCode = zipCode;
    }

    public long getCreditCard() {
        return creditCard;
    }

    public void setCreditCard(long creditCard) {
        this.creditCard = creditCard;
    }

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }
}
