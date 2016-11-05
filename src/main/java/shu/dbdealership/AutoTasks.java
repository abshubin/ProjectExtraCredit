package shu.dbdealership;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.SystemUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import shu.dbdealership.Vehicle;

/**
 * Created by Andrew Shubin on 11/4/16.
 */

@Component
public class AutoTasks {

    private RestTemplate restTemplate = new RestTemplate();
    private String host = "http://localhost:8080";
    private String postURL = host + "/addVehicle";
    private String getURL = host + "/getVehicle";
    private String putURL = host + "/updateVehicle";
    private String deleteURL = host + "/deleteVehicle";

    @Scheduled(cron = "0/3 * * * * *")
    public void addVehicle() {
        String makeModel = RandomStringUtils.randomAlphanumeric(30);
        int year = RandomUtils.nextInt(1986, 2016);
        double retailPrice = RandomUtils.nextDouble(15000, 45000);
        Vehicle newVehicle = new Vehicle(makeModel, year, retailPrice);
        newVehicle = restTemplate.postForObject(postURL, newVehicle, Vehicle.class);
        System.out.println("ADDED Vehicle with ID = " + newVehicle.getId());
    }

    @Scheduled(cron = "1/3 * * * * *")
    public void deleteVehicle() {
        int deleteId = RandomUtils.nextInt(0, 100);
        restTemplate.delete(deleteURL + "/" + deleteId);
        System.out.println("DELETE attempted for ID = " + deleteId);
    }

    @Scheduled(cron = "2/3 * * * * *")
    public void updateVehicle() {
        int updateId = RandomUtils.nextInt(0, 100);
        Vehicle newVehicle = restTemplate.getForObject(getURL + "/" + updateId, Vehicle.class);
        if (newVehicle != null) {
            newVehicle.setYear(0);
            newVehicle.setRetailPrice(0);
            newVehicle.setMakeModel("UPDATED");
            restTemplate.put(putURL, newVehicle, Vehicle.class);
        }
        System.out.println("UPDATE attempted for ID = " + updateId);
    }
}
