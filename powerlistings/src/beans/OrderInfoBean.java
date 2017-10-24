package beans;
import java.util.ArrayList;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement
public class OrderInfoBean {
    public long yextId;
    public String partnerId;
    public String status;
    public String name;
    public Address address;
    public ArrayList<PhoneInfo> phones;
    public ArrayList<Category> categories;
    public String description;
    public ArrayList<Email> emails;
    public GeoData geoData;
    public ArrayList<Hour> hours;
    public HoursText hoursText;
    public ArrayList<Image> images;
    public ArrayList<Video> videos;
    public SpecialOffer specialOffer;
    public ArrayList<String> paymentOptions;
    public ArrayList<Url> urls;
    public Attribution attribution;
    public ArrayList<String> keywords;
    public ArrayList<List> lists;
    public boolean closed;
    public String closeDate;
    public ArrayList<String> specialties;
    public ArrayList<String> brands;
    public ArrayList<String> products;
    public ArrayList<String> services;
    public String yextEstablished;
    public ArrayList<String> associations;
    public ArrayList<String> languages;
}














