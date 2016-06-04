package gol.base.injection;

import java.util.Objects;

/**
 * Created by Tino on 02.06.2016.
 */
class InstanceProvider implements BeanProvider {

    private final Object instance;

    InstanceProvider(final Object instance) {
        Objects.requireNonNull(instance, "Non null instance required");
        this.instance = instance;
    }

    @Override public <T> T getBean(final BeanRepository repository) {
        return (T) instance;
    }
}
