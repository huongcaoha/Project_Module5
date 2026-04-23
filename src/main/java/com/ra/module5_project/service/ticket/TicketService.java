package com.ra.module5_project.service.ticket;

import com.ra.module5_project.model.entity.TicketPrice;
import com.ra.module5_project.repository.TicketPriceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class TicketService {
    @Autowired
    private TicketPriceRepository ticketPriceRepository;

    public void initializeTicketPrice() {
        if (ticketPriceRepository.count() == 0) {
            List<TicketPrice> prices = new ArrayList<>();

            // Các hằng số dựa theo logic của Thầy
            String[] typesSeat = {"STANDARD", "DOUBLE"}; // Giả định Seat.getTypeSeat().toString()
            String[] typesMovie = {"2D", "3D"};

            // Khung giờ theo code của Thầy
            LocalTime dayStart = LocalTime.of(10, 0, 0);
            LocalTime dayEnd = LocalTime.of(21, 59, 59);
            LocalTime nightStart = LocalTime.of(22, 0, 0);
            LocalTime nightEnd = LocalTime.of(9, 59, 59);

            // Loại ngày: 23456 (Thứ 2-6), 78 (Cuối tuần)
            int[] daysArr = {23456, 78};

            for (String seat : typesSeat) {
                for (String movie : typesMovie) {
                    for (int days : daysArr) {
                        // 1. Ngày thường (isHoliday = false) - 2 khung giờ
                        prices.add(createPrice(seat, movie, dayStart, dayEnd, days, false,
                                calculateBasePrice(seat, movie, days, false, true)));
                        prices.add(createPrice(seat, movie, nightStart, nightEnd, days, false,
                                calculateBasePrice(seat, movie, days, false, false)));

                        // 2. Ngày lễ (isHoliday = true) - 2 khung giờ
                        // Để đơn giản, ngày lễ ta gán chung dayOfWeeks theo logic của Thầy
                        prices.add(createPrice(seat, movie, dayStart, dayEnd, days, true,
                                calculateBasePrice(seat, movie, days, true, true)));
                        prices.add(createPrice(seat, movie, nightStart, nightEnd, days, true,
                                calculateBasePrice(seat, movie, days, true, false)));
                    }
                }
            }
            ticketPriceRepository.saveAll(prices);
            System.out.println(">>> Đã khởi tạo 24 loại giá vé thành công!");
        }
    }

    // Hàm hỗ trợ tạo đối tượng cho gọn
    private TicketPrice createPrice(String seat, String movie, LocalTime start, LocalTime end, int days, boolean holiday, double p) {
        return TicketPrice.builder()
                .typeSeat(seat).typeMovie(movie)
                .startTime(start).endTime(end)
                .dayOfWeeks(days).isHoliday(holiday)
                .price(p).build();
    }

    // Hàm giả định tính giá tiền thực tế (Thầy có thể điều chỉnh con số)
    private double calculateBasePrice(String seat, String movie, int days, boolean holiday, boolean isDayTime) {
        double base = 60000; // Giá sàn 2D, ghế thường, ngày thường
        if (seat.equals("vip")) base += 20000;
        if (movie.equals("3D")) base += 30000;
        if (days == 78) base += 15000;
        if (holiday) base += 30000;
        if (!isDayTime) base -= 10000; // Phim khuya thường rẻ hơn hoặc tùy Thầy cấu hình
        return base;
    }
}
