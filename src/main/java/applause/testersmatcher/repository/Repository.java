package applause.testersmatcher.repository;

import applause.testersmatcher.model.Identifiable;

public interface Repository<K, T extends Identifiable<K>> {
    T getById(K id);

    T save(T element);

    T update(T element);

    void delete(T element);
}
