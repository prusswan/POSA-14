import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @class SimpleSemaphore
 *
 * @brief This class provides a simple counting semaphore implementation using
 *        Java a ReentrantLock and a ConditionObject. It must implement both
 *        "Fair" and "NonFair" semaphore semantics, just liked Java Semaphores.
 */
public class SimpleSemaphore {
    /**
     * Constructor initialize the data members.
     */
    public SimpleSemaphore (int permits,
                            boolean fair)
    {
        // TODO - you fill in here
        mCount = permits;
        lock = new ReentrantLock(fair);
        noPermits = lock.newCondition();
    }

    /**
     * Acquire one permit from the semaphore in a manner that can
     * be interrupted.
     */
    public void acquire() throws InterruptedException {
        // TODO - you fill in here
        lock.lockInterruptibly();
        try {
            while (mCount == 0) {
                noPermits.await();
            }
            mCount--;
        } finally {
            lock.unlock();
        }
    }

    /**
     * Acquire one permit from the semaphore in a manner that
     * cannot be interrupted.
     */
    public void acquireUninterruptibly() {
        // TODO - you fill in here
        lock.lock();
        try {
            while (mCount == 0) {
                noPermits.awaitUninterruptibly();
            }
            mCount--;
        } finally {
            lock.unlock();
        }
    }

    /**
     * Return one permit to the semaphore.
     */
    void release() {
        // TODO - you fill in here
        lock.lock();
        try {
            mCount++;
            noPermits.signal();
        } finally {
            lock.unlock();
        }
    }

    /**
     * Define a ReentrantLock to protect the critical section.
     */
    // TODO - you fill in here
    private ReentrantLock lock;

    /**
     * Define a ConditionObject to wait while the number of
     * permits is 0.
     */
    // TODO - you fill in here
    private Condition noPermits;

    /**
     * Define a count of the number of available permits.
     */
    // TODO - you fill in here
    volatile int mCount;
}
