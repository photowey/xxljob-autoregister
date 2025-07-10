package io.github.photowey.xxljob.autoregister.core.event;

import org.springframework.context.ApplicationEvent;

/**
 * {@code LoadAuthenticationCookieEvent}.
 *
 * @author photowey
 * @version 3.1.1.1.0.0
 * @since 2025/07/10
 */
public class LoadAuthenticationCookieEvent extends ApplicationEvent {

    private String cookie;

    public LoadAuthenticationCookieEvent() {
        super(new Object());
    }

    public void cookie(String cookie) {
        this.cookie = cookie;
    }

    public String cookie() {
        return cookie;
    }
}
