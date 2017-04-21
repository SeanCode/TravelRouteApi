package api.app.travelroute.service;

import api.app.travelroute.entity.OrderEntity;
import api.app.travelroute.entity.Role;
import api.app.travelroute.entity.RouteEntity;
import api.app.travelroute.entity.UserEntity;
import api.app.travelroute.repository.OrderRepository;
import api.app.travelroute.repository.RouteRepository;
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

    @Autowired
    RouteRepository routeRepo;

    public OrderEntity createOrder(long userId, long routeId, long beginTime, int count, String username, String phone, String note) {
        RouteEntity route = routeRepo.findOne(routeId);
        if (route == null) {
            throw new NotExistsException();
        }
        OrderEntity order = new OrderEntity();
        order.setUserId(userId);
        order.setDestId(route.getDestId());
        order.setRouteId(routeId);
        order.setBeginTime(beginTime);
        order.setCount(count);
        order.setMoney(count * route.getPrice());
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

    public OrderEntity updateOrderStatus(UserEntity user, long id, int status) {
        OrderEntity order = orderRepo.findByIdAndUserId(id, user.getId());
        if (order == null) {
            throw new NotExistsException();
        }
        if (user.getRole() == Role.ROLE_USER) {
            if (order.getUserId() != user.getId()) {
                throw new NotAllowedException();
            }
        }
        order.setStatus(status);
        order.setUpdateTime(Util.time());

        return orderRepo.save(order);
    }

    public void delete(long id, long userId) {
        OrderEntity order = orderRepo.findByIdAndUserId(id, userId);
        if (order == null) {
            throw new NotExistsException();
        }
        orderRepo.delete(order);
    }

}
