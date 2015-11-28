package qotd

import java.util.List
import java.util.concurrent.atomic.AtomicInteger;

import javax.annotation.PreDestroy

import backgroundjob.BackgroundJob
import backgroundjob.BackgroundJobState
import backgroundjob.StupidBackgroundJob

class NoSuchBackgroundJobException extends RuntimeException {
    String message = "No such background job"

}

class MaxBackgroundJobsReachedException extends RuntimeException {
    String message = "Unable to create a new background job: Maximum background jobs reached"
}

class InvalidStateBackgroundJobException extends RuntimeException {
    String message = "Invalid state for backgroundjob"
}

class BackgroundJobsService {
    private List<BackgroundJob> jobs = new ArrayList<BackgroundJob>()
    /**
     * Maximum limit of running jobs simultaneously
     */
    private static int limit = 5
    private AtomicInteger currentIdx = new AtomicInteger(0)

    public List<BackgroundJob> getJobs() {
        return jobs
    }
    
    public BackgroundJob findJob(int id) {
        def ret =  jobs.find { it.identifier == id }
        if (! ret) {
            throw new NoSuchBackgroundJobException()
        }
        return ret
    }

    public synchronized BackgroundJob create() throws RuntimeException {
        int runningJobs = jobs.findAll { it.jobState == BackgroundJobState.RUNNING }.size()
        
        if (runningJobs >= limit) {
            throw new MaxBackgroundJobsReachedException()
        }
        def ret = new StupidBackgroundJob(currentIdx.incrementAndGet().toInteger())
        jobs << ret
        return ret
    }

    private BackgroundJob getJob(int id) throws RuntimeException {
        def selJob = findJob(id)
        if (! selJob) {
            throw new NoSuchBackgroundJobException()
        }
        return selJob
    }

    public BackgroundJob start(int id) throws RuntimeException {
        def selJob = findJob(id)
        if (selJob.jobState != BackgroundJobState.NOT_STARTED) {
          throw new InvalidStateBackgroundJobException()
        }
        selJob.start()
        return selJob
    }
    
    public synchronized void remove(int id) throws RuntimeException {
        def selJob = findJob(id)
        if (selJob.jobState != BackgroundJobState.FINISHED) {
            throw new InvalidStateBackgroundJobException()
          }
        jobs.remove(selJob)
    }

    public int progress(int id) throws RuntimeException {
        def selJob = findJob(id)
        return selJob.progress()
    }

    @PreDestroy
    def shutdown() {
        jobs.each {
            it.join(100)
        }
    }
}
