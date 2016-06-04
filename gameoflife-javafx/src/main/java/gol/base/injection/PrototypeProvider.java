package gol.base.injection;

import java.util.function.Function;

/**
 * Created by Tino on 02.06.2016.
 */
class PrototypeProvider implements BeanProvider {

    private final Function<BeanRepository, ?> creator;

    PrototypeProvider(final Function<BeanRepository, ?> creator) {
        this.creator = creator;
    }

    @Override public <T> T getBean(final BeanRepository repository) {
        final Object instance = creator.apply(repository);
        new PostConstructor(repository).postConstruct(instance);
        return (T) instance;
    }
}
