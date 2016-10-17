package com.ouclbc.util.dataholder;

public class CustomDataChecker implements IDataChecker {

    @Override
    public boolean isCorrect(Object data) {
        if (data.equals("abc"))
            return false;
        else
            return true;
    }

}
