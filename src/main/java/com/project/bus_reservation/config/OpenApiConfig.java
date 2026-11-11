package com.project.bus_reservation.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.servers.Server;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "Bus Reservation System API",
                version = "1.0.0",
                description = "REST API for managing bus reservations, including user management, operator management, " +
                        "bus fleet management, route planning, seat management, booking, and cancellation.",
                contact = @Contact(
                        name = "Bus Reservation Support",
                        email = "support@busreservation.com"
                ),
                license = @License(
                        name = "Apache 2.0",
                        url = "https://www.apache.org/licenses/LICENSE-2.0"
                )
        ),
        servers = {
                @Server(url = "http://localhost:8080", description = "Local Development Server")
        },
        tags = {
                @Tag(name = "User Management", description = "APIs for managing users - create, retrieve, and update user accounts"),
                @Tag(name = "Operator Management", description = "APIs for managing bus operators - create and retrieve operator details"),
                @Tag(name = "Bus Management", description = "APIs for managing buses under an operator - create and retrieve bus details"),
                @Tag(name = "Bus Display", description = "APIs for searching and displaying available buses on routes"),
                @Tag(name = "Route Management", description = "APIs for managing routes under an operator - create and retrieve route details"),
                @Tag(name = "Seat Management", description = "APIs for viewing all seats across all buses"),
                @Tag(name = "Bus Seats", description = "APIs for viewing seats of a specific bus"),
                @Tag(name = "Passenger Management", description = "APIs for managing passengers under a user account"),
                @Tag(name = "Booking Management", description = "APIs for creating and retrieving bookings for a user"),
                @Tag(name = "Cancellation", description = "APIs for cancelling bookings")
        }
)
public class OpenApiConfig {
}
