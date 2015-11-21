package qotd

import backgroundjob.BackgroundJob
import backgroundjob.StupidBackgroundJob
import javax.annotation.PreDestroy

import org.springframework.web.bind.annotation.PathVariable;

class BackgroundJobsController {
    private List<BackgroundJob> jobs = new ArrayList<BackgroundJob>()
    private static int limit = 10

    def index() {
        def rendered = '<h1>List of jobs</h1><ul>'
        jobs.eachWithIndex { it, idx ->
            rendered += "<li>Job #${idx}: ${it.jobState} - completed: ${it.progress()}%</li>"
        }
        rendered += "</ul>"
      render text: rendered
    }

    def create() {
      jobs << new StupidBackgroundJob()
      render text: '<h1>Job created</h1>'
    }

    def start(@PathVariable("id") int id) {
        println "start() called with indice: ${id}"
        def selJob = jobs[id]
        if (! selJob) {
            render(status: 404, text: 'job not found')
            return
        }
        if (selJob.jobState == BackgroundJobState.NOT_STARTED) {
            selJob.start()
            render(text: "Job #${id} started")
        } else {
            render(status: 400, text: "invalid job state: ${selJob.jobState}")
        }
        return
    }

    def progress(@PathVariable("id") int id) {
        def selJob = jobs[id]
        if (! selJob) {
            render(status: 404, text: 'job not found')
        } else {
            render (text: "Job progress (percent): ${selJob.progress()}")
        }
        return
    }
    
    @PreDestroy
    def shutdown() {
        println "Destroying threads"
        jobs.each {
            it.join(100)
        }
    }
    
}
