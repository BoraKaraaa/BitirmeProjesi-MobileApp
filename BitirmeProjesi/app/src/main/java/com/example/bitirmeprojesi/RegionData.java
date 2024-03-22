package com.example.bitirmeprojesi;

import androidx.annotation.NonNull;

public class RegionData  extends Data
{
    private int RegionID;
    private String Country;
    private String City;

    public RegionData(int regionID, String country, String city)
    {
        RegionID = regionID;
        Country = country;
        City = city;
    }

    public int getRegionID() { return RegionID; }
    public void setRegionID(int regionID) { RegionID = regionID; }
    public String getCountry() { return Country; }
    public void setCountry(String country) { Country = country; }
    public String getCity() { return City; }
    public void setCity(String city) { City = city; }

    @NonNull
    @Override
    public String toString()
    {
        return "RegionData{" +
                "RegionID='" + RegionID + '\'' +
                ", Country='" + Country + '\'' +
                ", City='" + City + '\'' +
                '}';
    }

}
