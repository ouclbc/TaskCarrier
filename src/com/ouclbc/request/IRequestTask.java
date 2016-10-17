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

public interface IRequestTask {
    public enum Priority {
        LOW, NORMAL, HIGH, IMMEDIATE
    };

    public int TaskId();

    public DataHolder performRequest();

    public boolean isCancel();

    public Priority getPriority();
    
    public void cancel();
}
