package com.atguigu.daijia.driver.service.impl;

import com.atguigu.daijia.common.execption.GuiguException;
import com.atguigu.daijia.common.result.ResultCodeEnum;
import com.atguigu.daijia.driver.client.DriverInfoFeignClient;
import com.atguigu.daijia.driver.service.LocationService;
import com.atguigu.daijia.map.client.LocationFeignClient;
import com.atguigu.daijia.model.entity.driver.DriverSet;
import com.atguigu.daijia.model.form.map.UpdateDriverLocationForm;
import com.atguigu.daijia.model.form.map.UpdateOrderLocationForm;
import lombok.extern.slf4j.Slf4j;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@SuppressWarnings({"unchecked", "rawtypes"})
public class LocationServiceImpl implements LocationService {

    @Autowired
    private LocationFeignClient locationFeignClient;
    @Autowired
    private DriverInfoFeignClient driverInfoFeignClient;

    /**
     * 开启接单服务：更新司机经纬度位置
     * @param updateDriverLocationForm
     * @return
     */
    @Override
    public Boolean updateDriverLocation(UpdateDriverLocationForm updateDriverLocationForm) {
        //开启接单了才能更新司机接单位置
        DriverSet driverSet = driverInfoFeignClient.getDriverSet(updateDriverLocationForm.getDriverId()).getData();
        if(driverSet.getServiceStatus().intValue() == 1) {
            return locationFeignClient.updateDriverLocation(updateDriverLocationForm).getData();
        } else {
            throw new GuiguException(ResultCodeEnum.NO_START_SERVICE);
        }
    }

    /**
     * 实时更新司机位置信息
     * @param updateOrderLocationForm
     * @return
     */
    @Override
    public Boolean updateOrderLocationToCache(UpdateOrderLocationForm updateOrderLocationForm) {
        return locationFeignClient.updateOrderLocationToCache(updateOrderLocationForm).getData();
    }

}
