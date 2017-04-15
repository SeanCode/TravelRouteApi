package api.app.travelroute.controller.v1;

import api.app.travelroute.service.OrderService;
import api.base.common.DataResponse;
import api.base.common.OutputEntityJsonView;
import api.base.service.AuthenticationFacadeService;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
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
    AuthenticationFacadeService authenticationFacadeService;

    @RequestMapping("/create")
    @JsonView(OutputEntityJsonView.Detail.class)
    public DataResponse createOrder() {

        return DataResponse.create();
    }

}
