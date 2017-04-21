package api.app.travelroute.controller.v1;

import api.app.travelroute.service.DestinationService;
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
@RestController("V1.DestinationController")
@RequestMapping("/api/v1/dest")
public class DestinationController {

    @Autowired
    DestinationService destService;

    @RequestMapping(value = "/query", method = RequestMethod.GET)
    @JsonView(OutputEntityJsonView.Basic.class)
    public DataResponse queryDestination(@RequestParam("dest") String query, @PageableDefault(sort = {"createTime", "updateTime"}, direction = Sort.Direction.DESC) Pageable pageable) {

        return DataResponse.create().putPage("dest_list", destService.queryDestination(query, pageable));
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @JsonView(OutputEntityJsonView.Detail.class)
    public DataResponse getDestinationList(@PageableDefault(sort = {"createTime", "updateTime"}, direction = Sort.Direction.DESC) Pageable pageable) {

        return DataResponse.create().putPage("dest_list", destService.getDestinationList(pageable));
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @JsonView(OutputEntityJsonView.Detail.class)
    public DataResponse getDestination(@PathVariable(name = "id") long id) {

        return DataResponse.create().put("dest", destService.getDestination(id));
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @JsonView(OutputEntityJsonView.Detail.class)
    public DataResponse saveDest(@RequestParam(value = "id", required = false, defaultValue = "0") long id,
                                 @RequestParam(value = "name", required = false) String name,
                                 @RequestParam(value = "intro", required = false) String intro,
                                 @RequestParam(value = "info", required = false) String info,
                                 @RequestParam(value = "dest", required = false) String dest,
                                 @RequestParam(value = "img", required = false) String img) {

        return DataResponse.create().put("destination", destService.saveDestination(id, name, intro, info, dest, img));
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @JsonView(OutputEntityJsonView.Basic.class)
    public DataResponse delete(@RequestParam("id") long id) {

        destService.delete(id);
        return DataResponse.create();
    }

}
