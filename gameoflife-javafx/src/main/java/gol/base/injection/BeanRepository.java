package gol.base.injection;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * Created by Tino on 01.06.2016.
 */
public class BeanRepository {

    private final Map<Class<?>, BeanProvider> beanCreators;

    private BeanRepository(final Map<Class<?>, BeanProvider> beanCreators) {
        this.beanCreators = beanCreators;
    }

    public <T> T get(final Class<T> cls) {
        final BeanProvider provider = beanCreators.get(cls);
        if (provider == null) {
            throw new RuntimeException("No Bean registered for Class " + cls.getName());
        }
        return provider.getBean(this);
    }

    public static class BeanRepositoryBuilder {

        private final Map<Class<?>, BeanProvider> beanCreators = new HashMap<>();

        public <T> BeanRepositoryBuilder singleton(final Class<T> cls, final Function<BeanRepository, T> creator) {
            beanCreators.put(cls, new SingletonProvider(creator));
            return this;
        }

        public <T> BeanRepositoryBuilder singleton(final Class<T> cls, final Supplier<T> creator) {
            beanCreators.put(cls, new SingletonProvider((Function<BeanRepository, T>) repository -> creator.get()));
            return this;
        }

        public <T> BeanRepositoryBuilder prototype(final Class<T> cls, final Function<BeanRepository, T> creator) {
            beanCreators.put(cls, new PrototypeProvider(creator));
            return this;
        }

        public <T> BeanRepositoryBuilder prototype(final Class<T> cls, final Supplier<T> creator) {
            beanCreators.put(cls, new PrototypeProvider((Function<BeanRepository, T>) repository -> creator.get()));
            return this;
        }

        public <T> BeanRepositoryBuilder instance(final T instance) {
            beanCreators.put(instance.getClass(), new InstanceProvider(instance));
            return this;
        }

        public BeanRepository build() {
            return new BeanRepository(beanCreators);
        }
    }
}