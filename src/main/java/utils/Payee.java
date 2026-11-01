package utils;

public class Payee {

    private final String name;
    private final String address;
    private final String city;
    private final String state;
    private final String zipCode;
    private final String phone;
    private final String accountNumber;
    private final String verifyAccountNumber;
    private final String amount;

    public Payee(String name, String address, String city, String state, String zipCode,
                 String phone, String accountNumber, String verifyAccountNumber, String amount) {
        this.name = name;
        this.address = address;
        this.city = city;
        this.state = state;
        this.zipCode = zipCode;
        this.phone = phone;
        this.accountNumber = accountNumber;
        this.verifyAccountNumber = verifyAccountNumber;
        this.amount = amount;
    }

    public String getName(){
        return name;
    }
    public String getAddress(){
        return address;
    }
    public String getCity()                {
        return city; }
    public String getState()               {
        return state; }
    public String getZipCode()             {
        return zipCode; }
    public String getPhone()               {
        return phone; }
    public String getAccountNumber()       {
        return accountNumber; }
    public String getVerifyAccountNumber() {
        return verifyAccountNumber; }
    public String getAmount()              {
        return amount; }
}
