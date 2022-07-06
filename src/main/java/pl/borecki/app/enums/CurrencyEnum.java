package pl.borecki.app.enums;

public enum CurrencyEnum {

    PLN("PLN"),
    USD("USD");

    private String iso4217Code = "";

    CurrencyEnum(String code) {
        this.iso4217Code = code;
    }

    @Override
    public String toString() {
        return iso4217Code;
    }
}
