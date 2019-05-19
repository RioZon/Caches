/**
 * Интерфейс кэша с чтением и добавлением элементов
 * @param <K> Ключ
 * @param <V> Ссылка на объект
 */
public interface Cache<K,V> {
    /**
     * Чтение значения из кэша
     * @param key ключ
     * @return ссылка на обект
     */
    V get(K key);

    /**
     * Добавление элемента в кэш
     * @param key ключ элемента
     * @param value ссылка на элемент
     */
    void put(K key,V value);
}
