package revox.config;

/**
 * Created by ashraf on 8/1/2015.
 */
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.access.expression.method.DefaultMethodSecurityExpressionHandler;
import org.springframework.security.access.expression.method.MethodSecurityExpressionHandler;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.GlobalMethodSecurityConfiguration;
import org.springframework.security.core.Authentication;

import java.io.Serializable;

/**
 * Created by ashraf on 6/7/15.
 */
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)

public class GlobalMethod extends GlobalMethodSecurityConfiguration {
    @Override
    protected MethodSecurityExpressionHandler createExpressionHandler() {
        DefaultMethodSecurityExpressionHandler expressionHandler = new DefaultMethodSecurityExpressionHandler();
        expressionHandler.setPermissionEvaluator(new Permission());
        return expressionHandler;
    }

    private class Permission implements PermissionEvaluator {

        @Override
        public boolean hasPermission(Authentication authentication, Object targetDomainObject, Object permission) {
            return false;
        }

        @Override
        public boolean hasPermission(Authentication authentication, Serializable targetId, String targetType, Object permission) {
            return false;
        }
    }
}