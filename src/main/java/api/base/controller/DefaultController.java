package api.base.controller;

import com.fasterxml.jackson.annotation.JsonView;
import api.base.common.DataResponse;
import api.base.common.OutputEntityJsonView;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by cc on 16/7/30.
 */
@RestController
public class DefaultController {

    @RequestMapping("/")
    @JsonView(OutputEntityJsonView.Basic.class)
    public DataResponse index() {
        return new DataResponse().put("hello", "world");
    }


}
