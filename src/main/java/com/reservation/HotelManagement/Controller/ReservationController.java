package com.reservation.HotelManagement.Controller;

import com.reservation.HotelManagement.Model.Client;
import com.reservation.HotelManagement.Model.Reservation;
import com.reservation.HotelManagement.Model.Venue_reservation;
import com.reservation.HotelManagement.Repository.ClientRepo;
import com.reservation.HotelManagement.Repository.ReservationRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/reservation")

@CrossOrigin(originPatterns = "*")

public class ReservationController {

    @Autowired
    private ReservationRepo reservationRepo;

    @Autowired
    private ClientRepo clientRepo;

    @PostMapping
    @ResponseBody
    public Reservation createNewReservation(@RequestBody Reservation reservation, @RequestParam Long clientId) {
        Client client = clientRepo.findById(clientId).orElseThrow(() -> new RuntimeException("Client not found"));
        reservation.setClient(client);
        return reservationRepo.save(reservation);
    }

    @GetMapping
    public List<Reservation> getReservationsWithClientDetails(){
        List<Reservation> reservations = reservationRepo.findAll();
        for (Reservation reservation : reservations) {
            Client client = reservation.getClient();
            // You can add additional client details to the response if needed
        }
        return reservations;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Reservation> getReservationById(@PathVariable Long id) {
        Optional<Reservation> reservation = reservationRepo.findById(id);
        if (reservation.isPresent()) {
            return ResponseEntity.ok(reservation.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }


//    @GetMapping
//    public ResponseEntity<List<Reservation>> getAllReservations() {
//        List<Reservation> reservations = reservationRepo.findAll();
//        return ResponseEntity.ok(reservations);
//    }



    // Update a reservation
    @PutMapping("/{id}")
    public ResponseEntity<Reservation> updateReservation(@PathVariable Long id, @RequestBody Reservation reservation) {
        Optional<Reservation> existingReservation = reservationRepo.findById(id);
        if (existingReservation.isPresent()) {
            reservation.setId(id); // Ensure the correct ID is being updated
            Reservation updatedReservation = reservationRepo.save(reservation);
            return new ResponseEntity<>(updatedReservation, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Delete a reservation by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable Long id) {
        Optional<Reservation> reservation = reservationRepo.findById(id);
        if (reservation.isPresent()) {
            reservationRepo.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


<<<<<<< HEAD


=======
>>>>>>> 6c7b5113672fe4fb315fb4fe0be02ed50466e5c4
    @PatchMapping("{id}/status")
    public ResponseEntity<Reservation> updateStatus(@PathVariable long id) {
        Reservation reservation = reservationRepo.findById(id).orElseThrow();
        if (reservation.getStatus().equals("Pending")) {
            reservation.setStatus("Checked-in");
        } else {
            reservation.setStatus("Checked-out");
        }
        reservationRepo.save(reservation);
        return ResponseEntity.ok(reservation);
    }
}

