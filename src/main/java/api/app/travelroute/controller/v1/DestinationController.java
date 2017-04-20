package api.app.travelroute.controller.v1;

import api.app.travelroute.service.DestinationService;
import api.base.common.DataResponse;
import api.base.common.OutputEntityJsonView;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
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

    @RequestMapping(value = "", method = RequestMethod.GET)
    @JsonView(OutputEntityJsonView.Basic.class)
    public DataResponse getDestinationList(@RequestParam("dest") String query) {

        return DataResponse.create().put("dest_list", destService.queryDestination(query));
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
                                 @RequestParam(value = "name", required = false, defaultValue = "null") String name,
                                 @RequestParam(value = "intro", required = false, defaultValue = "null") String intro,
                                 @RequestParam(value = "info", required = false, defaultValue = "null") String info,
                                 @RequestParam(value = "dest", required = false, defaultValue = "null") String dest,
                                 @RequestParam(value = "img", required = false, defaultValue = "null") String img) {

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
