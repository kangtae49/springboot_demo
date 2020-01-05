package schd;

import java.lang.reflect.Method;

import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class ScheduledJob implements Job {

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		JobDataMap jobDataMap = context.getJobDetail().getJobDataMap();
		Object service = jobDataMap.get("service");
		Method method  = (Method)jobDataMap.get("method");
		Object [] args = (Object [])jobDataMap.get("args");
		
		if (method == null) {
			System.out.println("method is null");
			return;
		}
		try {
			System.out.println("method:" + method.getName());
//			method.invoke(service);
			method.invoke(service, args);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
