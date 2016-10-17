package com.ouclbc.util.dataholder;

/**
 * <p>
 * Title: ICheckHandler.
 * </p>
 * <p>
 * Description: 检测数据正确性接口.
 * </p>
 * 
 * @author laozhang.
 * @version $Id$
 */

public interface IDataChecker {
    public boolean isCorrect(Object data);
}
