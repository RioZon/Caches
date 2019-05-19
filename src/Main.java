public class Main {

    private static void lfuTest() {
        System.out.println("Start testing LFU Cache:");
        System.out.println("-----");
        try {
            LFUCache<Integer,String> negLfuCache = new LFUCache<>(-1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        LFUCache<Integer,String> lfuCache = new LFUCache<>(4);
        lfuCache.put(1,"1");
        assert lfuCache.get(1).equals("1");
        lfuCache.put(2,"10");
        assert lfuCache.get(1).equals("1");
        assert lfuCache.get(2).equals("10");
        lfuCache.put(3,"Hello");
        assert lfuCache.get(1).equals("1");
        assert lfuCache.get(2).equals("10");
        assert lfuCache.get(3).equals("Hello");
        lfuCache.get(3);
        lfuCache.get(3);
        lfuCache.get(3);
        lfuCache.put(4,"it");
        assert lfuCache.get(1).equals("1");
        assert lfuCache.get(2).equals("10");
        assert lfuCache.get(3).equals("Hello");
        assert lfuCache.get(4).equals("it");
        lfuCache.get(4);
        lfuCache.put(5,"is");
        assert lfuCache.get(1).equals("1");
        assert lfuCache.get(2).equals("10");
        assert lfuCache.get(3).equals("Hello");
        assert lfuCache.get(4) == null;
        assert lfuCache.get(5).equals("is");
        lfuCache.get(5);
        lfuCache.get(5);
        lfuCache.get(5);
        lfuCache.get(5);
        lfuCache.get(5);
        lfuCache.put(6,"cache");
        try {
            lfuCache.put(null,"4");
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            lfuCache.put(7, null);
        } catch (Exception e) {
            e.printStackTrace();
        }

        assert lfuCache.get(1).equals("1");
        assert lfuCache.get(2) == null;
        assert lfuCache.get(3).equals("Hello");
        assert lfuCache.get(4) == null;
        assert lfuCache.get(5).equals("is");
        assert lfuCache.get(6).equals("cache");

        System.out.println(lfuCache.toString());

        System.out.println("LFU tests has been passed");
        System.out.println("-----");
    }

    private static void lruTest() {
        System.out.println("Start testing LRU Cache:");
        System.out.println("-----");
        try {
            LRUCache<Integer,String> negLRuCache = new LRUCache<>(-1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        LRUCache<Integer,String> lruCache = new LRUCache<>(4);
        lruCache.put(1,"1");
        assert lruCache.getSilent(1).equals("1");
        lruCache.put(2,"10");
        assert lruCache.getSilent(1).equals("1");
        assert lruCache.getSilent(2).equals("10");
        lruCache.put(3,"Hello");
        assert lruCache.getSilent(1).equals("1");
        assert lruCache.getSilent(2).equals("10");
        assert lruCache.getSilent(3).equals("Hello");
        lruCache.get(1);
        lruCache.get(3);
        lruCache.get(1);
        lruCache.put(4,"it");
        assert lruCache.getSilent(1).equals("1");
        assert lruCache.getSilent(2).equals("10");
        assert lruCache.getSilent(3).equals("Hello");
        assert lruCache.getSilent(4).equals("it");
        lruCache.put(5,"is");
        assert lruCache.getSilent(1).equals("1");
        assert lruCache.getSilent(2) == null;
        assert lruCache.getSilent(3).equals("Hello");
        assert lruCache.getSilent(4).equals("it");
        assert lruCache.getSilent(5).equals("is");
        lruCache.get(1);
        lruCache.get(3);
        lruCache.get(1);
        lruCache.get(4);
        lruCache.get(1);
        lruCache.put(6,"cache");
        try {
            lruCache.put(null,"4");
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            lruCache.put(7, null);
        } catch (Exception e) {
            e.printStackTrace();
        }

        assert lruCache.getSilent(1).equals("1");
        assert lruCache.getSilent(2) == null;
        assert lruCache.getSilent(3).equals("Hello");
        assert lruCache.getSilent(4).equals("it");
        assert lruCache.getSilent(5) == null;
        assert lruCache.getSilent(6).equals("cache");

        System.out.println(lruCache.toString());

        System.out.println("LRU tests has been passed");
    }

    public static void main(String[] args) {
        lfuTest();
        lruTest();
        System.out.println("All tests has been passed");
    }
}
