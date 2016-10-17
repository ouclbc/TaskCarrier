package com.ouclbc.util;

import java.util.Comparator;
import java.util.concurrent.PriorityBlockingQueue;

/**
 * <p>
 * Title: TODO.
 * </p>
 * <p>
 * Description: TODO.
 * </p>
 * 
 * @author laozhang
 * @version $Id$
 */

public class FixPriorityBlockingQueue<E> extends PriorityBlockingQueue<E> {
    /**
     * TODO
     */
    private static final long serialVersionUID = -1703881478843609214L;
    private int mCapacity = Integer.MAX_VALUE;

    public FixPriorityBlockingQueue(int capacity) {
        super(capacity);
        mCapacity = capacity;
    }

    public FixPriorityBlockingQueue(int capacity,
            Comparator<? super E> comparator) {
        super(capacity, comparator);
        mCapacity = capacity;
    }

    @Override
    public boolean offer(E e) {
        boolean result = false;
        if (mCapacity > size()) {
            result = super.offer(e);
        }
        return result;
    }
}
