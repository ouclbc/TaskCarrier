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

class Ehco implements Runnable {
    /**
     * 响应对应的请求
     */
    private TaskCarrier mRequest;
    /**
     * 响应对应的数据
     */
    private DataHolder mDataHolder;

    Ehco(TaskCarrier request, DataHolder dataHolder) {
        mRequest = request;
        mDataHolder = dataHolder;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Runnable#run()
     */
    @Override
    public void run() {
        if (mRequest.isCancel()) {
            mRequest.onFinish();
            return;
        }
        mRequest.onFinish();
        if (mRequest.getResponse() != null) {
            if (mDataHolder == null || !mDataHolder.isCorrect()) {
                mRequest.getResponse().onError(mDataHolder);
            } else {
                mRequest.getResponse().onResponse(mDataHolder);
            }
        }
    }

}
