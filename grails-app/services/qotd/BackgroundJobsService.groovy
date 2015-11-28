package qotd

import java.util.List

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

    public List<BackgroundJob> getJobs() {
        return jobs
    }

    public BackgroundJob create() throws RuntimeException {
        int runningJobs = jobs.findAll { it.jobState == BackgroundJobState.RUNNING }.size()
        
        if (runningJobs >= limit) {
            throw new MaxBackgroundJobsReachedException()
        }
        def ret = new StupidBackgroundJob(jobs.size())
        jobs << ret
        return ret
    }

    private BackgroundJob getJob(int id) throws RuntimeException {
        def selJob = jobs[id]
        if (! selJob) {
            throw new NoSuchBackgroundJobException()
        }
        return selJob
    }

    public BackgroundJob start(int id) throws RuntimeException {
        def selJob = getJob(id)
        if (selJob.jobState != BackgroundJobState.NOT_STARTED) {
          throw new InvalidStateBackgroundJobException()
        }
        selJob.start()
        return selJob
    }

    public int progress(int id) throws RuntimeException {
        def selJob = getJob(id)
        return selJob.progress()
    }

    @PreDestroy
    def shutdown() {
        jobs.each {
            it.join(100)
        }
    }
}
