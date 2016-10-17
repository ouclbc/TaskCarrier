# TaskCarrier
##used for multi thread work

###How to use it：
1.	写一个类实现IRequestTask和IResponse方法（如果不需要线程处理返回结果，则不需要实现此方法）
如下所示：
1.  ```
public class test implements IRequestTask,IResponse {
    public test(){}
    @Override
    public int TaskId() {
        return this.hashCode();
    }
    @Override
    public DataHolder performRequest() {
        DataHolder result = new DataHolder();
        result.setDataChecker(new DefaultDataChecker());
        result.data="";
        return null;
    }

    @Override
    public boolean isCancel() {
        return false;
    }

    @Override
    public Priority getPriority() {
        return Priority.IMMEDIATE;
        
    }

    @Override
    public void cancel() {

    }

    @Override
    public void onResponse(DataHolder dataHolder) {
        
    }

    @Override
    public void onError(DataHolder dataHolder) {
        
    }
}
2.	实现TaskId方法，线程队列的id,唯一的，重复id加入不到线程队列里面去。
3.	实现Priority方法，有四个选项，表示线程的优先级。
Priority.IMMEDIATE; 
Priority.HIGH; 
Priority.NORMAL;
Priority.LOW;
4.	在performRequest处理耗时的操作，实现你要在线程中做的事情。如果线程返回数据要求不高，可以使用默认数据校验类DefaultDataChecker，也可以自定义自己的数据校验类，如下所示：
5.  ```
public class AdDataChecker implements IDataChecker {
    @Override
    public boolean isCorrect(Object data) {
        if (data == null)
            return false;
        ReplyInfo dataInfo = (ReplyInfo) data;
        if(dataInfo.getError()==null){
            return true;
        }
        return false;
    }

}
5.	在onResponse（正确结果）或者onError（performRequest返回null）中处理线程返回的结果。
6.	调用方法:test t = new test();
new TaskCarrier(t, t).prepare();

