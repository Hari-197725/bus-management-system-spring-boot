package com.project.bus_reservation;

import com.project.bus_reservation.user.dto.request.UserCreateRequest;
import com.project.bus_reservation.user.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class BusReservationApplicationTests {

    @Autowired
    private UserService userService;

	@Test
	void contextLoads() {
        System.out.println("Testing getAllUsers before: " + userService.getAllUsers().size());
        String testPhone = "99" + (System.currentTimeMillis() % 100000000L);
        UserCreateRequest req = new UserCreateRequest("Test User", "test@example.com", testPhone);
        userService.createUsers(req);
        System.out.println("Testing getAllUsers after: " + userService.getAllUsers().size());
	}

}
