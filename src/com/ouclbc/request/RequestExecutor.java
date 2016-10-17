package com.ouclbc.request;

import android.os.Handler;
import android.os.Looper;
import android.util.SparseArray;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

import com.ouclbc.request.IRequestTask.Priority;
import com.ouclbc.util.FixPriorityBlockingQueue;

/**
 * <p>
 * Title: TODO.
 * </p>
 * <p>
 * Description: TODO.
 * </p>
 * 
 * @author 
 * @Modify 李保成
 * @version $Id$
 */

class RequestExecutor {
    private static final String TAG = "RequestExecutor";
    private FixPriorityBlockingQueue<Runnable> mExecuteQueue = new FixPriorityBlockingQueue<Runnable>(
            20);
    private ThreadPoolExecutor mPool = new ThreadPoolExecutor(2, 6, 60L,
            TimeUnit.SECONDS, mExecuteQueue, new RejectedExecutionHandler() {

                @Override
                public void rejectedExecution(Runnable r,
                        ThreadPoolExecutor executor) {
                    addInWaitingQueue(r);
                }
            });
    private PriorityBlockingQueue<Runnable> mWaitingQueue = new PriorityBlockingQueue<Runnable>();
    private Handler mHandler = new Handler(Looper.getMainLooper());

    private ExecutorService mCachedThreadPool = Executors.newCachedThreadPool();

    private SparseArray<TaskCarrier> mRequestMap = new SparseArray<TaskCarrier>();
    private final ReentrantLock mLock = new ReentrantLock();

    private void addInWaitingQueue(Runnable r) {
        mLock.lock();
        try {
            mRequestMap.remove(((TaskCarrier) r).getRequestId());
        } finally {
            mLock.unlock();
        }
        mWaitingQueue.put(r);
    }

    void execute(TaskCarrier request) {
        boolean addFlag = false;
        mLock.lock();
        try {
            if (mRequestMap.get(request.getRequestId()) == null) {
                mRequestMap.put(request.getRequestId(), request);
                addFlag = true;
            }
        } finally {
            mLock.unlock();
        }
        if (addFlag) {
            request.onAddInQueue(this);
            if ((request.getPriority() != null) && (request.getPriority() == Priority.IMMEDIATE)) {
                mCachedThreadPool.execute(request);
            } else {
                mPool.execute(request);
            }

        }
    }

    void finish(TaskCarrier request) {
        mLock.lock();
        try {
            mRequestMap.remove(request.getRequestId());
        } finally {
            mLock.unlock();
        }

        try {
            if (mWaitingQueue.size() > 0) {
                Runnable task = mWaitingQueue.take();
                execute((TaskCarrier) task);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    synchronized void ehco(Ehco ehco) {
        mHandler.post(ehco);
    }

    void stop() {
        mPool.shutdownNow();
        mLock.lock();
        try {
            mRequestMap.clear();
        } finally {
            mLock.unlock();
        }
        mExecuteQueue.clear();
        mWaitingQueue.clear();
    }
}
