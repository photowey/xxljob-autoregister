package io.github.photowey.xxljob.autoregister.register.listener;

import io.github.photowey.xxljob.autoregister.core.event.LoadAuthenticationCookieEvent;
import io.github.photowey.xxljob.autoregister.core.holder.AbstractApplicationContextHolder;
import io.github.photowey.xxljob.autoregister.register.engine.RegisterEngineGetter;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

/**
 * {@code LoadAuthenticationCookieEventListener}.
 *
 * @author photowey
 * @version 3.1.1.1.0.0
 * @since 2025/07/10
 */
@Component
public class LoadAuthenticationCookieEventListener
    extends AbstractApplicationContextHolder
    implements ApplicationListener<LoadAuthenticationCookieEvent>, RegisterEngineGetter {

    @Override
    public void onApplicationEvent(LoadAuthenticationCookieEvent event) {
        this.sync(event);
    }

    private void sync(LoadAuthenticationCookieEvent event) {
        String cookie = this.registerEngine().loginService().tryAcquireAuthenticationCookie();
        if (StringUtils.hasText(cookie)) {
            event.cookie(cookie);
        }
    }
}
