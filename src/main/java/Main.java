public class Main {
    public static void main(final String... args) throws InterruptedException {
        startThread(new MsgProducer());
        startThread(new MsgConsumer());
        Thread.sleep(1000);
        startThread(new MsgConsumer());
        startThread(new MsgProducer());
        startThread(new MsgConsumer());
        startThread(new MsgProducer());
    }

    private static void startThread(final Runnable runnable) {
        final Thread thread = new Thread(runnable);
        thread.setDaemon(false);
        thread.start();
    }
}
