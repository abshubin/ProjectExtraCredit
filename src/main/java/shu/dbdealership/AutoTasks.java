package shu.dbdealership;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 * Created by Andrew Shubin on 11/4/16.
 */

@Component
public class AutoTasks {

    private RestTemplate restTemplate = new RestTemplate();
    private boolean firstCall = true;
    private int nextId;
    private String host = "http://localhost:8080";
    private String postURL = host + "/addVehicle";
    private String getURL = host + "/getVehicle";
    private String putURL = host + "/updateVehicle";
    private String deleteURL = host + "/deleteVehicle";
    private String maxIdURL = host + "/getHighestId";

    @Scheduled(cron = "0/3 * * * * *")
    public void addVehicle() {
        init();
        String makeModel = RandomStringUtils.randomAlphanumeric(30);
        int year = RandomUtils.nextInt(1986, 2016);
        double retailPrice = RandomUtils.nextDouble(15000, 45000);
        Vehicle newVehicle = new Vehicle(nextId++, makeModel, year, retailPrice);
        restTemplate.postForObject(postURL, newVehicle, Vehicle.class);
    }

    @Scheduled(cron = "1/3 * * * * *")
    public void deleteVehicle() {
        int deleteId = RandomUtils.nextInt(0, 100);
        restTemplate.delete(deleteURL + "/" + deleteId);
    }

    @Scheduled(cron = "2/3 * * * * *")
    public void updateVehicle() {
        ObjectMapper mapper = new ObjectMapper();
        int updateId = RandomUtils.nextInt(0, 100);
        Vehicle newVehicle = new Vehicle(updateId, "UPDATED", 0, 0);
        restTemplate.put(putURL, newVehicle, Vehicle.class);
        Vehicle testVehicle = restTemplate.getForObject(getURL + "/" + updateId, Vehicle.class);
        System.out.println("UPDATED attempted on Vehicle with ID = " + updateId);
        String message;
        if (testVehicle != null) {
            try {
                message = "Vehicle JSON: " + mapper.writeValueAsString(testVehicle);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
                message = "Error encountered generating JSON";
            }
        } else {
            message = "No Vehicle with this ID";
        }
        System.out.println(message);
    }

    private void init() {
        if (firstCall) {
            nextId = restTemplate.getForObject(maxIdURL, Integer.class);
            nextId++;   // Since the id set in the above line is the
                        // highest USED id, we increment to have the
                        // next UNUSED id.
            firstCall = false;
        }
    }
}
