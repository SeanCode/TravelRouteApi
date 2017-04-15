package api.app.travelroute.controller.v1;

import api.base.common.DataResponse;
import api.base.common.OutputEntityJsonView;
import api.base.exception.BaseException;
import api.base.service.UploadService;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;

/**
 * Created by cc on 2017/4/12.
 */
@RestController("V1.UploadController")
@RequestMapping("/api/v1/upload")
@Controller
public class UploadController {

    @Autowired
    UploadService uploadService;

    @RequestMapping("/img")
    @JsonView(OutputEntityJsonView.Detail.class)
    public DataResponse handleFileUpload(@RequestParam("file") MultipartFile file) {
        uploadService.validateFile(file);

        DataResponse response = DataResponse.create();
        try {
            uploadService.ensureImageRoot();
            String fileName = uploadService.copy(file);

            HashMap<String, Object> fileDataMap = new HashMap<>();
            fileDataMap.put("name", fileName);
            response.put("file", fileDataMap);

        } catch (IOException | RuntimeException e) {
            e.printStackTrace();
            throw new BaseException(BaseException.ERROR, "文件上传失败 ");
        }

        return response;
    }

}
