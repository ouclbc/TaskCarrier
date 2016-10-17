package com.ouclbc.request;
import android.os.Process;
/**
 * <p>
 * Title: TODO.
 * </p>
 * <p>
 * Description: TODO.
 * </p>
 * 
 * @author .
 * @version $Id$
 */

public class TaskCarrier implements Runnable, Comparable<TaskCarrier> {
    private IRequestTask mTask;
    private IResponse mResponse;
    private boolean mPrepare;
    private RequestExecutor mExecutor;

    public TaskCarrier() {
    }

    public TaskCarrier(IRequestTask requestTask) {
        this(requestTask, null);
    }

    public TaskCarrier(IRequestTask requestTask, IResponse response) {
        setTaskAndResponse(requestTask, response);
    }

    public void setRequestTask(IRequestTask requestTask) {
        if (mPrepare) {
            throw new IllegalArgumentException("请求已经放入队列中，不能设置RequestTask");
        }
        mTask = requestTask;
    }

    public void setResponse(IResponse response) {
        if (mPrepare) {
            throw new IllegalArgumentException("请求已经放入队列中，不能设置Response");
        }
        mResponse = response;
    }

    public void setTaskAndResponse(IRequestTask requestTask, IResponse response) {
        setRequestTask(requestTask);
        setResponse(response);
    }

    public void prepare() {
        if (mTask != null) {
            RequestManager.getInstance().add(this);
        } else {
            throw new IllegalArgumentException("准备加入请求队列前没有设置请求任务");
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Comparable#compareTo(java.lang.Object)
     */
    @Override
    public int compareTo(TaskCarrier another) {
        return another.getPriority().ordinal() - getPriority().ordinal();
    }

    public IRequestTask.Priority getPriority() {
        return mTask.getPriority();
    }

    int getRequestId() {
        if (mTask != null) {
            return mTask.TaskId();
        } else {
            throw new IllegalArgumentException("获取请求Id前没有设置请求任务");
        }
    }

    void onAddInQueue(RequestExecutor executor) {
        mPrepare = true;
        mExecutor = executor;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Runnable#run()
     */
    @Override
    public void run() {
        Process.setThreadPriority(Process.THREAD_PRIORITY_BACKGROUND);
        if (mTask.isCancel()) {
            onFinish();
        } else {
            mExecutor.ehco(new Ehco(this, mTask.performRequest()));
        }
    }

    IResponse getResponse() {
        return mResponse;
    }

    void onFinish() {
        mExecutor.finish(this);
        mPrepare = false;
    }

    boolean isCancel() {
        return mTask.isCancel();
    }

    /**
     * 
     * <p>Title: TODO.</p>
     * <p>Description: shutdown ThreadPool.</p>
     *
     */
    public void stop(){
        mExecutor.stop();
        mPrepare = false;
    }
    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return super.toString()
                + ";task = "
                + (mTask == null ? null : (mTask + ";taskId = " + mTask
                        .TaskId()));
    }
}
