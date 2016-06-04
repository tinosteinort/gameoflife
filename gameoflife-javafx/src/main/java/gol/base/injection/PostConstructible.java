package gol.base.injection;

/**
 * Created by Tino on 02.06.2016.
 */
public interface PostConstructible {

    void onPostConstruct(BeanRepository repository);
}
