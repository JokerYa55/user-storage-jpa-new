package org.keycloak.examples.storage.user;

import org.jboss.logging.Logger;
import org.keycloak.component.ComponentModel;
import org.keycloak.models.KeycloakSession;
import org.keycloak.storage.UserStorageProviderFactory;

import javax.naming.InitialContext;

/**
 * @version 1
 */
public class EjbExampleUserStorageProviderFactory implements UserStorageProviderFactory<EjbExampleUserStorageProvider> {
    private static final Logger log = Logger.getLogger(EjbExampleUserStorageProviderFactory.class);


    @Override
    public EjbExampleUserStorageProvider create(KeycloakSession session, ComponentModel model) {
        try {
            log.debug("----------------------------------------------------------------------");
            log.debug("create");
            InitialContext ctx = new InitialContext();
            EjbExampleUserStorageProvider provider = (EjbExampleUserStorageProvider)ctx.lookup("java:global/user-storage-jpa-example/" + EjbExampleUserStorageProvider.class.getSimpleName());
            log.debug("provider = " + provider + "  -> " + EjbExampleUserStorageProvider.class.getSimpleName());
            provider.setModel(model);
            provider.setSession(session);
            return provider;
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    public String getId() {
        return "example-user-storage-jpa";
    }

    @Override
    public String getHelpText() {
        return "JPA Example User Storage Provider";
    }

    @Override
    public void close() {
        log.info("<<<<<< Closing factory");

    }
}
