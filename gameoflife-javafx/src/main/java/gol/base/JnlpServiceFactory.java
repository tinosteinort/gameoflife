package gol.base;

import com.github.tinosteinort.beanrepository.Factory;

import javax.jnlp.ServiceManager;
import javax.jnlp.UnavailableServiceException;

/**
 * Created by Tino on 02.12.2016.
 */
class JnlpServiceFactory<T> implements Factory<T> {

    private final String serviceClassName;

    JnlpServiceFactory(final String serviceClassName) {
        this.serviceClassName = serviceClassName;
    }

    @Override public T createInstance() {
        try {
            return (T) ServiceManager.lookup(serviceClassName);
        }
        catch (UnavailableServiceException ex) {
            throw new RuntimeException("Could not create Instance for " + serviceClassName, ex);
        }
    }
}
