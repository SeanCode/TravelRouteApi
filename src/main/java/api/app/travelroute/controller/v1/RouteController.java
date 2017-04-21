package api.app.travelroute.controller.v1;

import api.app.travelroute.service.RouteService;
import api.base.common.DataResponse;
import api.base.common.OutputEntityJsonView;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * Created by cc on 2017/4/16.
 */
@Controller
@RestController("V1.RouteController")
@RequestMapping("/api/v1/route")
public class RouteController {

    @Autowired
    RouteService routeService;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @JsonView(OutputEntityJsonView.Detail.class)
    public DataResponse getRouteList(@RequestParam("dest_id") long destId, @PageableDefault(sort = {"createTime", "updateTime"}, direction = Sort.Direction.DESC) Pageable pageable) {

        return DataResponse.create().putPage("route_list", routeService.getRouteListByDestId(destId, pageable));
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @JsonView(OutputEntityJsonView.Detail.class)
    public DataResponse getRoute(@PathVariable("id") long id) {

        return DataResponse.create().put("route", routeService.getRoute(id));
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @JsonView(OutputEntityJsonView.Detail.class)
    public DataResponse saveRoute(@RequestParam(value = "dest_id", required = false, defaultValue = "0") long destId,
                                  @RequestParam(value = "id", required = false, defaultValue = "0") long id,
                                  @RequestParam(value = "name", required = false) String name,
                                  @RequestParam(value = "price", required = false) Double price,
                                  @RequestParam(value = "intro", required = false) String intro,
                                  @RequestParam(value = "info", required = false) String info,
                                  @RequestParam(value = "notice", required = false) String notice,
                                  @RequestParam(value = "img", required = false) String img) {

        return DataResponse.create().put("route", routeService.saveRoute(destId, id, name, price, intro, info, notice, img));
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @JsonView(OutputEntityJsonView.Basic.class)
    public DataResponse deleteRoute(@RequestParam("id") long id) {

        routeService.delete(id);
        return DataResponse.create();
    }

}
