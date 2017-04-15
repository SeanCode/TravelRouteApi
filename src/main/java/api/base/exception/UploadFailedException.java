package api.base.exception;

/**
 * Created by cc on 16/8/28.
 */
public class UploadFailedException extends BaseException {

    public UploadFailedException(String message) {
        super(UPLOAD_FAILED, message);
    }

    public UploadFailedException() {
        super(UPLOAD_FAILED, "上传失败");
    }

}
