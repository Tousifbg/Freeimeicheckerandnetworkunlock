package free.imei.check.and.network.unlocker.Models;

public class ModelClass {
    String imei,country,network,date;

    public ModelClass(String imei, String country, String network, String date) {
        this.imei = imei;
        this.country = country;
        this.network = network;
        this.date = date;
    }

    public ModelClass() {
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getNetwork() {
        return network;
    }

    public void setNetwork(String network) {
        this.network = network;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
