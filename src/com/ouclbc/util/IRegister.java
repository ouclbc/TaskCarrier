package com.ouclbc.util;

/**
 * <p>
 * Title: IRegister.
 * </p>
 * <p>
 * Description: 注册监听器接口.
 * </p>
 * 
 * @author laozhang.
 * @version $Id$
 */
public interface IRegister<T> {
    /**
     * <p>
     * Title: registerListener.
     * </p>
     * <p>
     * Description: 注册监听器.
     * </p>
     * 
     * @param Listener
     *            监听器
     */
    public void registerListener(T Listener);

    /**
     * <p>
     * Title: unRegisterListener.
     * </p>
     * <p>
     * Description: 注销监听器.
     * </p>
     * 
     * @param Listener
     *            监听器
     */
    public void unRegisterListener(T Listener);
}