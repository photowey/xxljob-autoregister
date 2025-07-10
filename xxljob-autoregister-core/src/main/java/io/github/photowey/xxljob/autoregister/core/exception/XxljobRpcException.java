package io.github.photowey.xxljob.autoregister.core.exception;

/**
 * {@code XxljobRpcException}.
 *
 * @author photowey
 * @version 3.1.1.1.0.0
 * @since 2025/07/10
 */
public class XxljobRpcException extends RuntimeException {

    public XxljobRpcException() {
        super();
    }

    public XxljobRpcException(String message, Object... args) {
        super(String.format(message, args));
    }

    public XxljobRpcException(Throwable cause, String message, Object... args) {
        super(String.format(message, args), cause);
    }

    public XxljobRpcException(Throwable cause) {
        super(cause);
    }

    protected XxljobRpcException(
        String message,
        Throwable cause,
        boolean enableSuppression,
        boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
