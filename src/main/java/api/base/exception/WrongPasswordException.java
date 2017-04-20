package api.base.exception;

/**
 * Created by cc on 16/5/29.
 */
public class WrongPasswordException extends BaseException {

    public WrongPasswordException() {
        super(WRONG_PASSWORD, "密码错误");
    }
}
