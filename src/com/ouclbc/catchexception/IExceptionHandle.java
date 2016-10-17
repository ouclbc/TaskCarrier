package com.ouclbc.catchexception;

import android.content.Context;

/**
 * <p>
 * Title: IExceptionHandle.
 * </p>
 * <p>
 * Description: 异常发生时，处理接口.
 * </p>
 * 
 * @author 
 * @version $Id$
 */
public interface IExceptionHandle {
    /**
     * <p>
     * Title: performException.
     * </p>
     * <p>
     * Description: 异常发生时，处理接口.
     * </p>
     * 
     * @param exception
     * @param context
     */
    void performException(Throwable exception, Context context);
}