package com.atguigu.daijia.order.service;

import com.atguigu.daijia.model.entity.order.OrderInfo;
import com.atguigu.daijia.model.form.order.OrderInfoForm;
import com.atguigu.daijia.model.form.order.StartDriveForm;
import com.atguigu.daijia.model.form.order.UpdateOrderBillForm;
import com.atguigu.daijia.model.form.order.UpdateOrderCartForm;
import com.atguigu.daijia.model.vo.base.PageVo;
import com.atguigu.daijia.model.vo.order.CurrentOrderInfoVo;
import com.atguigu.daijia.model.vo.order.OrderBillVo;
import com.atguigu.daijia.model.vo.order.OrderProfitsharingVo;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

public interface OrderInfoService extends IService<OrderInfo> {

    Long saveOrderInfo(OrderInfoForm orderInfoForm);

    Integer getOrderStatus(Long orderId);

    Boolean robNewOrder(Long driverId, Long orderId);

    CurrentOrderInfoVo searchCustomerCurrentOrder(Long customerId);

    CurrentOrderInfoVo searchDriverCurrentOrder(Long driverId);

    Boolean driverArriveStartLocation(Long orderId, Long driverId);

    Boolean updateOrderCart(UpdateOrderCartForm updateOrderCartForm);

    Boolean startDrive(StartDriveForm startDriveForm);

    Long getOrderNumByTime(String startTime, String endTime);

    Boolean endDrive(UpdateOrderBillForm updateOrderBillForm);

    void systemCancelOrder(Long orderId);

    PageVo findCustomerOrderPage(Page<OrderInfo> pageParam, Long customerId);

    PageVo findDriverOrderPage(Page<OrderInfo> pageParam, Long driverId);

    OrderBillVo getOrderBillInfo(Long orderId);

    OrderProfitsharingVo getOrderProfitsharing(Long orderId);

    Boolean sendOrderBillInfo(Long orderId, Long driverId);
}
