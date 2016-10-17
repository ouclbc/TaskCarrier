package com.ouclbc.request;

import com.ouclbc.util.dataholder.DataHolder;

/**
 * <p>
 * Title: TODO.
 * </p>
 * <p>
 * Description: TODO.
 * </p>
 * 
 * @author 
 * @version $Id$
 */

public interface IResponse {
    /**
     * 正确执行结束后响应处理
     * 
     * @param result
     *            正确执行结果
     */
    public void onResponse(DataHolder result);

    /**
     * 异常执行结束后相应处理
     * 
     * @param error
     *            异常处理结果
     */
    public void onError(DataHolder error);

}
