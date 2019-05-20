import java.util.HashMap;
import java.util.Map;

/**
 * Кэш для хранения объектов с вытеснением самого
 * редко используемого элемента
 * @param <K> Ключ
 * @param <V> Ссылка на объект
 */
public class LFUCache<K,V> implements Cache<K,V> {

    /**
     * Класс для хранения объекта и количества запросов к нему
     */
    private class Unit {
        private V object;
        private int frequency;

        /**
         * Конструктор класса
         * @param object ссылка на объект для хранения
         */
        Unit(V object) {
            this.object = object;
            frequency = 0;
        }

        V getObject() {
            ++frequency;
            if (frequency == Integer.MAX_VALUE) {
                for (Map.Entry<K,Unit> entry : lhMap.entrySet()) {
                    entry.getValue().decreaseFrequency();
                }
            }
            return object;
        }

        // debug only
        V getObjectSilent() {
            return object;
        }

        int getFrequency() {
            return frequency;
        }

        void decreaseFrequency() {frequency /= 2;}
    }

    private int maxCapacity;
    private HashMap<K, Unit> lhMap = new HashMap<>();

    /**
     * Конструктор класса
     * @param maxCapacity Максимальная вместительность кэша
     */
    public LFUCache(int maxCapacity) {
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
            return lhMap.get(key).getObject();
        }
        return null;
    }

    /*for debug only
     возвращает ссылку на объект без инкрементации
     счетчика запросов */
    public V getSilent(K key) {
        if (lhMap.containsKey(key)) {
            return lhMap.get(key).getObjectSilent();
        }
        return null;
    }

    /**
     * Добавление элемента в кэш
     * @param key ключ добавляемого элемента
     * @param value ссылка на добавляемый элемент
     */
    @Override
    public void put(K key, V value) {
        if (key == null) {
            throw new NullPointerException("Key must not be null");
        }
        if (value == null) {
            throw new NullPointerException("Value must not be null");
        }
        if (lhMap.size() == maxCapacity) {
            lhMap.remove(getMinFreqKey());
        }
        lhMap.put(key,new Unit(value));
    }

    /**
     * Поиск наименее запрашиваемого элемента для последующего удаления из кэша
     * @return ключ наименее запрашиваемого элемента
     */
    private K getMinFreqKey(){
        K key = null;
        int minFrequency = Integer.MAX_VALUE;
        for (Map.Entry<K,Unit> entry : lhMap.entrySet()) {
            if (entry.getValue().getFrequency() < minFrequency) {
                key = entry.getKey();
                minFrequency = entry.getValue().getFrequency();
            }
        }
        return key;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<K,Unit> entry : lhMap.entrySet()) {
            sb.append(entry.getKey());
            sb.append(" = ");
            sb.append(entry.getValue().getObject());
            sb.append(" ");
        }
        return sb.toString();
    }
}
