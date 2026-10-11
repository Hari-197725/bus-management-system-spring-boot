package com.project.bus_reservation.service;

import com.google.cloud.firestore.Firestore;
import com.google.firebase.cloud.FirestoreClient;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

@Service
public class BusService {

    @PostConstruct
    public void addTestBus() {
        try {
            // Get the Firestore database instance
            Firestore db = FirestoreClient.getFirestore();

            // Create some dummy data for a bus
            Map<String, Object> busData = new HashMap<>();
            busData.put("busNumber", "TN-58-1234");
            busData.put("route", "Madurai to Chennai");
            busData.put("totalSeats", 40);

            // Save it to a collection named 'buses' with a document ID of 'test-bus-1'
            db.collection("buses").document("test-bus-1").set(busData);

            System.out.println("SUCCESS: Test bus written to Firestore!");
        } catch (Exception e) {
            System.err.println("FAILED to write to Firestore:");
            e.printStackTrace();
        }
    }
}
}
