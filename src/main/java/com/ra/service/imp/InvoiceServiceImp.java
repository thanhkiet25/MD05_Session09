package com.ra.service.imp;

import com.ra.model.constant.Status;
import com.ra.model.entity.Invoice;
import com.ra.model.entity.OrderServices;
import com.ra.model.entity.Reservation;
import com.ra.model.entity.Room;
import com.ra.repository.InvoiceRepository;
import com.ra.service.InvoiceService;
import com.ra.service.OrderServicesService;
import com.ra.service.ReservationServicesService;
import com.ra.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
@Service
public class InvoiceServiceImp implements InvoiceService {
    @Autowired
    private InvoiceRepository invoiceRepository;
    @Autowired
    private OrderServicesService orderServicesService;
    @Autowired
    private RoomService roomService;
    @Autowired
    private ReservationServicesService reservationService;
    @Override
    public Invoice payInvoice(Long reservationId) {
        Reservation reservation = reservationService.findById(reservationId);
        List<OrderServices> orderServices = orderServicesService.findAllByReservationId(reservationId);
        double totalPriceRoom = reservation.getRoom().getPrice();
        double totalPriceOrderService = orderServices.stream().map(ods -> ods.getRoomServices().getPrice() * ods.getQuantity())
                .reduce(0.0, Double::sum);
        double totalAmount = totalPriceRoom + totalPriceOrderService;

        Invoice invoice = new Invoice();
        invoice.setReservation(reservation);
        invoice.setTotalAmount(totalAmount);
        invoice.setCreatedDate(LocalDateTime.now());

        try {
            Invoice newInvoice =  invoiceRepository.save(invoice);
            Room room = reservation.getRoom();
            room.setReservation(null);
            roomService.save(room);

            reservation.setStatus(Status.CHECKOUT);
            reservationService.save(reservation);
            return newInvoice;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }


    }


    @Override
    public List<Invoice> getInvoiceHistoryByCustomerId(Long customerId) {
        return invoiceRepository.findByReservation_CustomerId(customerId);
    }
}
