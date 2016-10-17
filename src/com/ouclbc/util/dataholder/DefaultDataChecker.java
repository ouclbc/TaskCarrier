package com.ouclbc.util.dataholder;

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

public class DefaultDataChecker implements IDataChecker {

    /*
     * (non-Javadoc)
     * 
     * @see com.hisense.util.dataholder.IDataChecker#isCorrect(java.lang.Object)
     */
    @Override
    public boolean isCorrect(Object data) {
        if (data == null)
            return false;
        return true;
    }

}
