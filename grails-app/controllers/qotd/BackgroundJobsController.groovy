package qotd

import backgroundjob.BackgroundJob
import backgroundjob.BackgroundJobState
import backgroundjob.StupidBackgroundJob

import javax.annotation.PreDestroy

import org.grails.web.json.JSONObject
import org.grails.web.json.JSONArray
import org.springframework.web.bind.annotation.PathVariable

class BackgroundJobsController {

    def backgroundJobsService
    
    def index(@PathVariable("format") String format) {
        if (format == "json") {
            def ret = new JSONArray()
            backgroundJobsService.getJobs().each {
                ret  << it.toJSON()
            }
            render text: ret.toString(), contentType: "application/json", encoding: "UTF-8"
            return
        }
        [jobs : backgroundJobsService.getJobs()]
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
        def job = null
        try {
            job = backgroundJobsService.start(id)
            render text: "Job #${id} started"
        } catch (NoSuchBackgroundJobException e) {
            render status: 404, text: 'job not found'
        } catch (InvalidStateBackgroundJobException e) {
            render status: 500, text: "Invalid job state"
        }
    }

    def progress(@PathVariable("id") int id) {
        try {
            def progress = backgroundJobsService.progress(id)
            render text: "Job progress (percent): ${selJob.progress()}"
        } catch (NoSuchBackgroundJobException e) {
            render status: 404, text: 'job not found'
        }
    }

}
