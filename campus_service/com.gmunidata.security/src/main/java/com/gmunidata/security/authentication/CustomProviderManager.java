package com.gmunidata.security.authentication;

import com.gmunidata.security.handler.AppAuthenticationProvider;
import com.gmunidata.security.handler.CustomAuthenticationProvider;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.CredentialsContainer;
import org.springframework.security.core.SpringSecurityMessageSource;
import org.springframework.util.Assert;

import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class CustomProviderManager implements AuthenticationManager, MessageSourceAware, InitializingBean {
    private static final Log logger = LogFactory.getLog(ProviderManager.class);
    private AuthenticationEventPublisher eventPublisher;
    private List<AuthenticationProvider> providers;
    protected MessageSourceAccessor messages;
    private AuthenticationManager parent;
    private boolean eraseCredentialsAfterAuthentication;
    private CustomAuthenticationProvider serviceProvider;
    private AppAuthenticationProvider appProvider;

    public CustomProviderManager(CustomAuthenticationProvider serviceProvider, AppAuthenticationProvider appProvider) {
        this(Arrays.asList(serviceProvider, appProvider), null);
        this.appProvider = appProvider;
        this.serviceProvider = serviceProvider;
    }

    public CustomProviderManager(List<AuthenticationProvider> providers, AuthenticationManager parent) {
        this.eventPublisher = new CustomProviderManager.NullEventPublisher();
        this.providers = Collections.emptyList();
        this.messages = SpringSecurityMessageSource.getAccessor();
        this.eraseCredentialsAfterAuthentication = true;
        Assert.notNull(providers, "providers list cannot be null");
        this.providers = providers;
        this.parent = parent;
        this.checkState();
    }

    public void afterPropertiesSet() throws Exception {
        this.checkState();
    }

    private void checkState() {
        if (this.parent == null && this.providers.isEmpty()) {
            throw new IllegalArgumentException("A parent AuthenticationManager or a list of AuthenticationProviders is required");
        }
    }

    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        Class<? extends Authentication> toTest = authentication.getClass();
        AuthenticationException lastException = null;
        Authentication result = null;
        boolean debug = logger.isDebugEnabled();
        Iterator var6 = this.getProviders().iterator();

        while (var6.hasNext()) {
            AuthenticationProvider provider = (AuthenticationProvider) var6.next();
            if (provider.supports(toTest)) {
                if (debug) {
                    logger.debug("Authentication attempt using " + provider.getClass().getName());
                }

                try {
                    result = provider.authenticate(authentication);
                    if (result != null) {
                        this.copyDetails(authentication, result);
                        break;
                    }
                } catch (AccountStatusException var11) {
                    this.prepareException(var11, authentication);
                    throw var11;
                } catch (InternalAuthenticationServiceException var12) {
                    this.prepareException(var12, authentication);
                    throw var12;
                } catch (AuthenticationException var13) {
                    lastException = var13;
                }
            }
        }

        if (result == null && this.parent != null) {
            try {
                result = this.parent.authenticate(authentication);
            } catch (ProviderNotFoundException var9) {
                ;
            } catch (AuthenticationException var10) {
                lastException = var10;
            }
        }

        if (result != null) {
            if (this.eraseCredentialsAfterAuthentication && result instanceof CredentialsContainer) {
                ((CredentialsContainer) result).eraseCredentials();
            }

            this.eventPublisher.publishAuthenticationSuccess(result);
            return result;
        } else {
            if (lastException == null) {
                lastException = new ProviderNotFoundException(this.messages.getMessage("ProviderManager.providerNotFound", new Object[]{toTest.getName()}, "No AuthenticationProvider found for {0}"));
            }

            this.prepareException((AuthenticationException) lastException, authentication);
            throw lastException;
        }
    }

    public Authentication authenticate(Authentication authentication, String type) throws AuthenticationException {
        Class<? extends Authentication> toTest = authentication.getClass();
        AuthenticationException lastException = null;
        Authentication result = null;
        boolean debug = logger.isDebugEnabled();
        AuthenticationProvider provider = serviceProvider;
        if("app".equalsIgnoreCase(type)){
            provider = appProvider;
        }
        if (provider.supports(toTest)) {
            if (debug) {
                logger.debug("Authentication attempt using " + provider.getClass().getName());
            }

            try {
                result = provider.authenticate(authentication);
                if (result != null) {
                    this.copyDetails(authentication, result);
                }
            } catch (AccountStatusException var11) {
                this.prepareException(var11, authentication);
                throw var11;
            } catch (InternalAuthenticationServiceException var12) {
                this.prepareException(var12, authentication);
                throw var12;
            } catch (AuthenticationException var13) {
                lastException = var13;
            }
        }

        if (result == null && this.parent != null) {
            try {
                result = this.parent.authenticate(authentication);
            } catch (ProviderNotFoundException var9) {
                ;
            } catch (AuthenticationException var10) {
                lastException = var10;
            }
        }

        if (result != null) {
            if (this.eraseCredentialsAfterAuthentication && result instanceof CredentialsContainer) {
                ((CredentialsContainer) result).eraseCredentials();
            }

            this.eventPublisher.publishAuthenticationSuccess(result);
            return result;
        } else {
            if (lastException == null) {
                lastException = new ProviderNotFoundException(this.messages.getMessage("ProviderManager.providerNotFound", new Object[]{toTest.getName()}, "No AuthenticationProvider found for {0}"));
            }

            this.prepareException((AuthenticationException) lastException, authentication);
            throw lastException;
        }
    }

    private void prepareException(AuthenticationException ex, Authentication auth) {
        this.eventPublisher.publishAuthenticationFailure(ex, auth);
    }

    private void copyDetails(Authentication source, Authentication dest) {
        if (dest instanceof AbstractAuthenticationToken && dest.getDetails() == null) {
            AbstractAuthenticationToken token = (AbstractAuthenticationToken) dest;
            token.setDetails(source.getDetails());
        }

    }

    public List<AuthenticationProvider> getProviders() {
        return this.providers;
    }

    public void setMessageSource(MessageSource messageSource) {
        this.messages = new MessageSourceAccessor(messageSource);
    }

    public void setAuthenticationEventPublisher(AuthenticationEventPublisher eventPublisher) {
        Assert.notNull(eventPublisher, "AuthenticationEventPublisher cannot be null");
        this.eventPublisher = eventPublisher;
    }

    public void setEraseCredentialsAfterAuthentication(boolean eraseSecretData) {
        this.eraseCredentialsAfterAuthentication = eraseSecretData;
    }

    public boolean isEraseCredentialsAfterAuthentication() {
        return this.eraseCredentialsAfterAuthentication;
    }

    private static final class NullEventPublisher implements AuthenticationEventPublisher {
        private NullEventPublisher() {
        }

        public void publishAuthenticationFailure(AuthenticationException exception, Authentication authentication) {
        }

        public void publishAuthenticationSuccess(Authentication authentication) {
        }
    }
}
