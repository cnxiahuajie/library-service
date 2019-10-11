package work.codehub.library.exception;

/**
 * 未登录/登录失败异常
 */
public class UnauthenticationException extends RuntimeException {

    public UnauthenticationException() {
    }

    public UnauthenticationException(String message) {
        super(message);
    }

}
