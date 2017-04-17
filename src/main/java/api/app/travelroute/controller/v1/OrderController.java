package api.app.travelroute.controller.v1;

import api.app.travelroute.service.OrderService;
import api.base.common.DataResponse;
import api.base.common.OutputEntityJsonView;
import api.app.travelroute.service.AuthenticationService;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by cc on 2017/4/12.
 */
@Controller
@RestController("V1.OrderController")
@RequestMapping("/api/v1/order")
public class OrderController {

    @Autowired
    OrderService orderService;

    @Autowired
    AuthenticationService authenticationService;

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @JsonView(OutputEntityJsonView.Detail.class)
    public DataResponse createOrder(@RequestParam("dest_id") long destId,
                                    @RequestParam("route_id") long routeId,
                                    @RequestParam("begin_time") long beginTime,
                                    @RequestParam("end_time") long endTime,
                                    @RequestParam("count") int count,
                                    @RequestParam("money") double money,
                                    @RequestParam("username") String username,
                                    @RequestParam("phone") String phone,
                                    @RequestParam("note") String note) {

        return DataResponse.create().put("order", orderService.createOrder(authenticationService.getUserAuth().getUserId(), destId, routeId, beginTime, endTime, count, money, username, phone, note));
    }

    @RequestMapping(value = "/list-created", method = RequestMethod.GET)
    @JsonView(OutputEntityJsonView.Detail.class)
    public DataResponse getOrderListCreated(@PageableDefault(sort = {"createTime", "updateTime"}, direction = Sort.Direction.DESC) Pageable pageable) {

        return DataResponse.create().putPage("order_list", orderService.getOrderListCreated(authenticationService.getUserAuth().getUserId(), pageable));
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @JsonView(OutputEntityJsonView.Detail.class)
    @RequestMapping(value = "/list-received", method = RequestMethod.GET)
    public DataResponse getOrderListReceived(@PageableDefault(sort = {"createTime", "updateTime"}, direction = Sort.Direction.DESC) Pageable pageable) {

        return DataResponse.create().putPage("order_list", orderService.getOrderListReceived(pageable));
    }

    @RequestMapping(value = "/cancel", method = RequestMethod.POST)
    @JsonView(OutputEntityJsonView.Detail.class)
    public DataResponse updateOrderStatus(@RequestParam("id") long id) {

        return DataResponse.create().put("order", orderService.cancelOrder(authenticationService.getUserAuth().getUserId(), id));
    }

}
