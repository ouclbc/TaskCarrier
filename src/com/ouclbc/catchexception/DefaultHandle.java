package com.ouclbc.catchexception;

import android.content.Context;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collections;

/**
 * <p>
 * Title: DefaultHandle.
 * </p>
 * <p>
 * Description: 默认异常处理实现，将tracestack保存到文件中，最大数量为10个，超过将最旧的删除.
 * </p>
 * 
 * @author
 * @version $Id$
 */
class DefaultHandle implements IExceptionHandle {
    private static final int MAX_COUNT = 10;

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.hisense.catchexception.IExceptionHandle#performException(java.lang
     * .Throwable, android.content.Context)
     */
    @Override
    public void performException(Throwable exception, Context context) {
        String path = context.getFilesDir().getAbsolutePath() + "/traces/";
        File dir = new File(path);
        if (!dir.exists()) {
            dir.mkdir();
        }
        File file = new File(path + System.currentTimeMillis());
        try {
            file.createNewFile();
            PrintStream out = new PrintStream(file);
            exception.printStackTrace(out);
            out.flush();
            out.close();
            file = new File(path);
            String[] filenames = file.list();
            if (filenames != null && filenames.length > MAX_COUNT) {
                ArrayList<Long> fileList = new ArrayList<Long>();
                for (String name : filenames) {
                    fileList.add(Long.parseLong(name));
                }
                Collections.sort(fileList);
                for (int i = 0; i < filenames.length - MAX_COUNT; i++) {
                    new File(fileList.get(i) + "").delete();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(0);
        }
    }
}