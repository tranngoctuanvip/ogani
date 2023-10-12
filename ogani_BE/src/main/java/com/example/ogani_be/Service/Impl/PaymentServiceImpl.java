package com.example.ogani_be.Service.Impl;

import com.example.ogani_be.Common.Constant.Constant;
import com.example.ogani_be.Common.Mapper.Mapper;
import com.example.ogani_be.Config.Config;
import com.example.ogani_be.DTO.PaymentDto;
import com.example.ogani_be.Entity.Payment;
import com.example.ogani_be.Repository.OrderRepository;
import com.example.ogani_be.Repository.PaymentRepository;
import com.example.ogani_be.Service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class PaymentServiceImpl implements PaymentService {
    @Autowired
    private PaymentRepository paymentRepository;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private Mapper mapper;
    @Override
    public String URL() {
        var totalPayment = orderRepository.totalPayment(mapper.getUserId());
        Integer total = totalPayment;
//        String urlReturn = null;
        String vnp_TxnRef = Config.getRandomNumber(8);
        String orderInfor = "Hoan tien GD OrderId:" + vnp_TxnRef;
        Map<String, String> vnp_Params = new HashMap<>();
        vnp_Params.put("vnp_Version", Config.vnp_Version);
        vnp_Params.put("vnp_Command", Config.vnp_Command);
        vnp_Params.put("vnp_TmnCode", Config.vnp_TmnCode);
        vnp_Params.put("vnp_Amount", String.valueOf(total * 100));
        vnp_Params.put("vnp_CurrCode", "VND");
        vnp_Params.put("vnp_TxnRef", vnp_TxnRef);
        vnp_Params.put("vnp_OrderInfo", orderInfor);
        vnp_Params.put("vnp_BankCode","NCB");
        vnp_Params.put("vnp_OrderType", Config.orderType);
        String locate = "vn";
        vnp_Params.put("vnp_Locale", locate);
//        urlReturn += ;
        vnp_Params.put("vnp_ReturnUrl", Config.vnp_ReturnUrl);
        vnp_Params.put("vnp_IpAddr", Config.vnp_IpAddr);
//        vnp_Params.put("","");
        Calendar cld = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        String vnp_CreateDate = formatter.format(cld.getTime());
        vnp_Params.put("vnp_CreateDate", vnp_CreateDate);

        cld.add(Calendar.MINUTE, 15);
        String vnp_ExpireDate = formatter.format(cld.getTime());
        vnp_Params.put("vnp_ExpireDate", vnp_ExpireDate);

        List fieldNames = new ArrayList(vnp_Params.keySet());
        Collections.sort(fieldNames);
        StringBuilder hashData = new StringBuilder();
        StringBuilder query = new StringBuilder();
        Iterator itr = fieldNames.iterator();
        while (itr.hasNext()) {
            String fieldName = (String) itr.next();
            String fieldValue = (String) vnp_Params.get(fieldName);
            if ((fieldValue != null) && (fieldValue.length() > 0)) {
                //Build hash data
                hashData.append(fieldName);
                hashData.append('=');
                try {
                    hashData.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
                    //Build query
                    query.append(URLEncoder.encode(fieldName, StandardCharsets.US_ASCII.toString()));
                    query.append('=');
                    query.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                if (itr.hasNext()) {
                    query.append('&');
                    hashData.append('&');
                }
            }
        }
        String queryUrl = query.toString();
        String vnp_SecureHash = Config.hmacSHA512(Config.secretKey, hashData.toString());
        queryUrl += "&vnp_SecureHash=" + vnp_SecureHash;
        String paymentUrl = Config.vnp_PayUrl + "?" + queryUrl;
        return paymentUrl;
    }
    @Override
    public Payment create(PaymentDto paymentDto) {
        Payment payment = new Payment();
        payment.setDescription(payment.getDescription());
        payment.setPaymentCode(paymentDto.getPaymentCode());
        payment.setMoney(paymentDto.getMoney());
        payment.setBankCode(paymentDto.getBankCode());
        payment.setTransactionCode(paymentDto.getTransactionCode());
        payment.setOrderId(paymentDto.getOrderId());
        payment.setTransactionStatus(Constant.SUCCESSFULL);
        paymentRepository.save(payment);
        return payment;
    }

    @Override
    public Integer totalPayment() {
        var total = orderRepository.totalPayment(mapper.getUserId());
        return total;
    }
}
