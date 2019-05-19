import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Кэш для хранения объектов с вытеснением в зависимости
 * от последнего времени запроса
 * @param <K> Ключ
 * @param <V> Ссылка на объект
 */
public class LRUCache<K,V> implements Cache<K,V> {

    private int maxCapacity;
    private LinkedHashMap<K, V> lhMap = new LinkedHashMap<>() {
        @Override
        protected boolean removeEldestEntry(Map.Entry<K,V> eldest) {
            return size() > maxCapacity;
        }
    };

    /**
     * Конструктор класса
     * @param maxCapacity Максимальная вместительность кэша
     */
    public LRUCache(int maxCapacity) {
        if (maxCapacity <= 0) {
            throw new IllegalArgumentException("Capacity must be > 0");
        }
        this.maxCapacity = maxCapacity;
    }

    /**
     * Получение ссылки на элемент по ключу
     * @param key ключ объекта для возврата
     * @return ссылка на объект в кэше
     */
    @Override
    public V get(K key) {
        if (lhMap.containsKey(key)) {
            V object = lhMap.get(key);
            lhMap.remove(key);
            lhMap.put(key,object);
            return object;
        }
        return null;
    }

    /*for debug only
     возвращает ссылку на объект без изменения
     порядка очереди */
    V getSilent(K key) {
        if (lhMap.containsKey(key)) {
            return lhMap.get(key);
        }
        return null;
    }

    /**
     * Добавление элемента в кэш
     * @param key ключ добавляемого элемента
     * @param value ссылка на добавляемый элемент
     */
    @Override
    public void put(K key,V value) {
        if (key == null) {
            throw new NullPointerException("Key must not be null");
        }
        if (value == null) {
            throw new NullPointerException("Value must not be null");
        }
        lhMap.put(key,value);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<K,V> entry : lhMap.entrySet()) {
            sb.append(entry.getKey());
            sb.append(" = ");
            sb.append(entry.getValue());
            sb.append(" ");
        }
        return sb.toString();
    }
}
