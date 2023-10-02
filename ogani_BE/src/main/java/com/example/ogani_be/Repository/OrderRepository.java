package com.example.ogani_be.Repository;

import com.example.ogani_be.Entity.Order;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    Optional<Order> findByDeliverAndDeleted(Long deliver, Integer deleted);
    long removeById(Long id);
    Optional<Order> findByIdAndDeleted(Long id, Integer deleted);
    @Modifying
    @Transactional
    @Query(value = "insert into order_cart (cart_id,order_id) values (:cart_id,:order_id)",nativeQuery = true)
    void create(@Param("cart_id") Integer cart_id, @Param("order_id") Long order_id);

    @Query(value = "select o.username,o.address,o.phone, p.name, c2.quality, sum(c2.quality * p.price) as total,o.status " +
            "from orders o \n" +
            "               join order_cart oc on oc.order_id = o.id\n" +
            "               join cart c2 on c2.id = oc.cart_id \n" +
            "               join product p on p.id = c2.product_id \n" +
            "               where o.deleted = 1\n" +
            "               group by o.username ,o.address, o.phone ,p.name ,c2.quality ,o.status ",nativeQuery = true)
    List<Map<String,Object>> getAll();
    @Query(value = "select o.username, o.address, o.phone, p.name, c2.quality, sum(c2.quality * p.price) as total,o.status \n" +
            "            from orders o\n" +
            "                    join order_cart oc on oc.order_id = o.id\n" +
            "                    join cart c2 on c2.id = oc.cart_id\n" +
            "                    join product p on p.id = c2.product_id\n" +
            "                    where o.deleted = 1 and o.deliver = :deliver\n" +
            "                 group by o.username ,o.address, o.phone ,p.name ,c2.quality ,o.status",nativeQuery = true)
    List<Map<String,Object>> getAllByDeliver(@Param("deliver") Long deliver);
    @Query(value = "select sum(o.status) as total,\n" +
            "              sum(CASE WHEN o.status = 1 THEN o.status ELSE 0 end) as unpaid,\n" +
            "              sum(CASE WHEN o.status = 2 THEN o.status ELSE 0 end) as paid\n" +
            "      from orders o\n" +
            "           join order_cart oc on oc.order_id = o.id \n" +
            "     where o.deleted = 1 \n" +
            " and o.create_at between :startTime and (SELECT DATE_ADD(:endTime, INTERVAL '23:59:59' HOUR_SECOND))\n" +
            " or (:startTime is null and :endTime is null)",nativeQuery = true)
    List<Map<String,Object>> selectTotal(@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
                                         @JsonFormat(pattern = "yyyy-MM-dd")
                                         @RequestParam("startTime")LocalDate startTime,
                                         @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
                                         @JsonFormat(pattern = "yyyy-MM-dd")
                                         @RequestParam("endTime") LocalDate endTime);
}