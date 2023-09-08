package com.example.ogani_be.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface StatisticalService {
    List<Map<String,Object>> sumOrders(LocalDate startTime, LocalDate endTime);
}
