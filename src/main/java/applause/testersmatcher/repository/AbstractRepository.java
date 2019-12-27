package applause.testersmatcher.repository;

import applause.testersmatcher.model.Identifiable;
import java.lang.reflect.Field;

public abstract class AbstractRepository<K, T extends Identifiable<K>> implements Repository<K, T> {
    @Override
    public synchronized T save(T element) {
        K id = setId(element);
        return saveInternal(element);
    }

    private K setId(T element) {
        K id;
        if ((id = element.getId()) == null) {
            id = calculateId(element);
            Field idField;
            try {
                idField = element.getClass().getDeclaredField("id");
                idField.setAccessible(true);
                idField.set(element, id);
                idField.setAccessible(false);
            } catch (NoSuchFieldException | IllegalAccessException exception) {
                throw new IllegalStateException(exception.getLocalizedMessage(), exception);
            }
        }
        return id;
    }

    protected abstract T saveInternal(T element);

    protected abstract K calculateId(T element);
}
