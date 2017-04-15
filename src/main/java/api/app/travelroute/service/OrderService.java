package api.app.travelroute.service;

import api.app.travelroute.entity.OrderEntity;
import api.app.travelroute.repository.OrderRepository;
import api.base.common.Util;
import api.base.exception.NotAllowedException;
import api.base.exception.NotExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * Created by cc on 2017/4/12.
 */
@Service
public class OrderService {

    @Autowired
    OrderRepository orderRepo;


}
