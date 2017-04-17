package api.app.travelroute.service;

import api.app.travelroute.entity.OrderEntity;
import api.app.travelroute.entity.Role;
import api.app.travelroute.entity.UserEntity;
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

    public OrderEntity createOrder(long userId, long destId, long routeId, long beginTime, long endTime, int count, double money, String username, String phone, String note) {
        OrderEntity order = new OrderEntity();
        order.setUserId(userId);
        order.setDestId(destId);
        order.setRouteId(routeId);
        order.setBeginTime(beginTime);
        order.setEndTime(endTime);
        order.setCount(count);
        order.setMoney(money);
        order.setUsername(username);
        order.setPhone(phone);
        order.setNote(note);
        order.setStatus(OrderEntity.STATUS_INIT);
        order.setCreateTime(Util.time());
        order.setUpdateTime(order.getCreateTime());

        return orderRepo.save(order);
    }

    public Page<OrderEntity> getOrderListCreated(long userId, Pageable pageable) {

        return orderRepo.findByUserId(userId, pageable);
    }

    public Page<OrderEntity> getOrderListReceived(Pageable pageable) {

        return orderRepo.findAll(pageable);
    }

    public OrderEntity cancelOrder(long userId, long id) {
        OrderEntity order = orderRepo.findByIdAndUserId(id, userId);
        if (order == null) {
            throw new NotExistsException();
        }
        order.setStatus(OrderEntity.STATUS_CANCEL);
        order.setUpdateTime(Util.time());

        return orderRepo.save(order);
    }

}
