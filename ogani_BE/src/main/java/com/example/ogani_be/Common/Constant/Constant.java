package com.example.ogani_be.Common.Constant;


public class Constant {
    public static final Integer NOTDELETE = 1; //chưa xóa
    public static final Integer DELETE = 0; // đã xóa
    public static final Integer STATUS = 1; // hoạt động
    public static final Integer INSTATUS = 0; // không hoạt động

    //Cart
    public static final Integer UNPAID = 1;// chưa thanh toaán
    public static final Integer PAID = 2; //  đã thanh toán
    //order
    public static final Integer DELIVERED = 1; // giao thành công
    public static final Integer PROCESSING = 2; // đang xử lý
    public static final Integer CANCEL = 3; //hủy
    public static final Integer End = 4; // kết thúc
    public static final Integer COMPLETE = 5; // hoàn thành
}
