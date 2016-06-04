package gol.base.injection;

/**
 * Created by Tino on 02.06.2016.
 */
class PostConstructor {

    private final BeanRepository repository;

    public PostConstructor(final BeanRepository repository) {
        this.repository = repository;
    }

    public void postConstruct(final Object bean) {
        if (bean instanceof PostConstructible) {
            ((PostConstructible) bean).onPostConstruct(repository);
        }
    }
}
