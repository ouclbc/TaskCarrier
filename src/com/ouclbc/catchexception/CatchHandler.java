package com.ouclbc.catchexception;

import android.content.Context;

import java.lang.Thread.UncaughtExceptionHandler;

/**
 * <p>
 * Title: CatchHandler.
 * </p>
 * <p>
 * Description: 捕获异常句柄，全局捕获未处理异常.
 * </p>
 * 
 * @author
 * @version $Id$
 */
public class CatchHandler implements UncaughtExceptionHandler {
    private static CatchHandler mInstance = new CatchHandler();
    private Context mContext;
    private IExceptionHandle mHandle = new DefaultHandle();
    private Throwable mException;

    private CatchHandler() {
    }

    public static CatchHandler getInstance() {
        return mInstance;
    }

    /**
     * <p>
     * Title: init.
     * </p>
     * <p>
     * Description: 设置要捕获的对象.
     * </p>
     * 
     * @param ctx
     *            捕获的对象
     * @param handle
     *            异常处理句柄
     */
    public void init(Context ctx, IExceptionHandle handle) {
        mContext = ctx;
        if (handle != null)
            mHandle = handle;
        Thread.setDefaultUncaughtExceptionHandler(this);

    }

    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        mException = ex;
        new Thread() {
            @Override
            public void run() {
                mHandle.performException(mException, mContext);
            }
        }.start();
    }
}