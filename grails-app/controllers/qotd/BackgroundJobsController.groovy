package qotd

import backgroundjob.BackgroundJob
import backgroundjob.BackgroundJobState;
import backgroundjob.StupidBackgroundJob

import javax.annotation.PreDestroy

import org.springframework.web.bind.annotation.PathVariable;

class BackgroundJobsController {

    def backgroundJobsService
    
    def index() {
        def rendered = '<h1>List of jobs</h1><ul>'
        backgroundJobsService.getJobs().eachWithIndex { it, idx ->
            rendered += "<li>Job #${idx}: ${it.jobState} - completed: ${it.progress()}%</li>"
        }
        rendered += "</ul>"
      render text: rendered
    }

    def create() {
        try {
            backgroundJobsService.create()
            render text: 'Job created'
        } catch (MaxBackgroundJobsReachedException e) {
            render status: 500, text: 'Max number of jobs reached'
        }
    }

    def start(@PathVariable("id") int id) {
        try {
            backgroundJobsService.start(id)
            render(text: "Job #${id} started")            
        } catch (NoSuchBackgroundJobException e) {
            render(status: 404, text: 'job not found')
        } catch (InvalidStateBackgroundJobException e) {
            render(status: 400, text: "Invalid job state ${selJob.jobState} for job #${id}")
        }
    }

    def progress(@PathVariable("id") int id) {
        try {
            def progress = backgroundJobsService.progress(id)
            render (text: "Job progress (percent): ${selJob.progress()}")
        } catch (NoSuchBackgroundJobException e) {
            render(status: 404, text: 'job not found')
        }
    }
    
    @PreDestroy
    def shutdown() {
        println "Destroying threads"
        jobs.each {
            it.join(100)
        }
    }
    
}
