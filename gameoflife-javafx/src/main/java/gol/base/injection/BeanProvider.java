package gol.base.injection;

/**
 * Created by Tino on 02.06.2016.
 */
interface BeanProvider {

    <T> T getBean(BeanRepository repository);
}
