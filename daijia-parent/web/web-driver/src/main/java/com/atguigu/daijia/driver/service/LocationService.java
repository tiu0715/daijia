package com.atguigu.daijia.driver.service;

import com.atguigu.daijia.model.form.map.UpdateDriverLocationForm;
import com.atguigu.daijia.model.form.map.UpdateOrderLocationForm;

public interface LocationService {


    Boolean updateDriverLocation(UpdateDriverLocationForm updateDriverLocationForm);

    Object updateOrderLocationToCache(UpdateOrderLocationForm updateOrderLocationForm);
}
