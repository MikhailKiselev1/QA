package pages.home_page;

public interface ExchangePage {

    public void preActions();

    public Double getCourse(String currencyName, String currencyType);

}
