package catalog.webfacade;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nullable;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.concurrent.Callable;

public class ServiceResult {

    private static final Logger LOG = LoggerFactory.getLogger(ServiceResult.class);

    public static ServiceResult guardedInvoke(Callable callable){
        try{
            return ServiceResult.success(callable.call());
        }catch (Exception ex){
            LOG.error("Error while invoking service" ,ex);
            return ServiceResult.failed(ex);
        }
    }

    public static ServiceResult guardedInvoke(Runnable callable){
        try{
            callable.run();
            return ServiceResult.success();
        }catch (Exception ex){
            LOG.error("Error while invoking service" ,ex);
            return ServiceResult.failed(ex);
        }
    }

    private static ServiceResult success(Object result){
        return new ServiceResult(true, result, null);
    }

    private static ServiceResult success(){
        return new ServiceResult(true, null, null);
    }

    private static ServiceResult failed(Exception ex){
        return new ServiceResult(true, null, ex);
    }

    private ServiceResult(boolean success, @Nullable Object result, @Nullable Exception exception) {
        this.success = success;
        this.result = result;
        this.errorMessage = exception != null ? exception.getMessage() : "";
        StringWriter out = new StringWriter();
        if (exception != null) {
            exception.printStackTrace(new PrintWriter(out));
            this.stacktrace = out.toString();
        }
    }

    public boolean isSuccess() {
        return success;
    }

    @Nullable
    public Object getResult() {
        return result;
    }


    public String getErrorMessage() {
        return errorMessage;
    }

    public String getStacktrace() {
        return stacktrace;
    }

    private boolean success;

    @Nullable
    private Object result;

    private String errorMessage;

    private String stacktrace;


}
