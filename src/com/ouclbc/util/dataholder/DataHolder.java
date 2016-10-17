package com.ouclbc.util.dataholder;

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

public class DataHolder {
    public Object data;
    private IDataChecker mDataChecker = new DefaultDataChecker();

    public boolean isCorrect() {
        return mDataChecker.isCorrect(data);
    }

    public void setDataChecker(IDataChecker dataChecker) {
        mDataChecker = dataChecker;
    }
}
