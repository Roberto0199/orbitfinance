package com.orbitfinance.core.alert;


import com.maxmind.geoip2.DatabaseReader;
import com.maxmind.geoip2.model.CityResponse;
import com.maxmind.geoip2.record.City;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.net.InetAddress;

@Slf4j
@Service
public class GeoIpService {

    private DatabaseReader databaseReader;

    @PostConstruct
    public void init(){
        try {
            InputStream database = getClass()
                    .getClassLoader()
                    .getResourceAsStream("geoip/GeoLite2-City.mmdb");

            if(database==null){
                log.error("GeoLite2-City.mmdb not found in resources/geoip/");
                return;
            }

            databaseReader = new DatabaseReader.Builder(database).build();
            log.info("GeoIP database loaded successfully.");

        }catch (Exception e){
            log.error("Failed to load GeoIP database.", e);
        }

    }

    public String resolveLocation(String ip){
        try {
            InetAddress ipAddress = InetAddress.getByName(ip);
            CityResponse response = databaseReader.city(ipAddress);

            String city = response.getCity().getName();
            String country = response.getCountry().getName();

            if(city == null) city = "Unknown City";
            if(country == null) country = "Unknown Country";

            return city + ", " + country;
        } catch (Exception e){
            log.warn("Could not resolve location for IP {}: {}", ip, e.getMessage());
            return "Unknown Location";
        }
    }

}
