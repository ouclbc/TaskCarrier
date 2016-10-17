package com.ouclbc.request;

import com.ouclbc.util.IService;

/**
 * <p>
 * Title: RequestManager.
 * </p>
 * <p>
 * Description: 请求管理器.
 * </p>
 * 
 * @author  .
 * @version $Id$
 */

public class RequestManager implements IService {
    private static RequestManager mInstance = new RequestManager();
    private RequestExecutor mExecutor;

    private RequestManager() {
        mExecutor = new RequestExecutor();
    }

    public static RequestManager getInstance() {
        return mInstance;
    }

    public void add(TaskCarrier request) {
        mExecutor.execute(request);
    }

    public void stop() {
        mExecutor.stop();
        mExecutor = new RequestExecutor();
    }
}
