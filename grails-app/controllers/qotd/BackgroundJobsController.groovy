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
    
    def index() {
        [jobs : backgroundJobsService.getJobs()]
    }
 
    def get() {
        def ret = new JSONArray()
        backgroundJobsService.getJobs().each {
            ret  << it.toJSON()
        }
        
        render text: new JSONObject().put('jobs', ret).toString(),
            contentType: "application/json", encoding: "UTF-8"
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
            render status: 400, text: "Invalid job state"
        }
    }
    def remove(@PathVariable("id") int id) {
        def job = null
        try {
            backgroundJobsService.remove(id)
            render text: "Job #${id} removed"
        } catch (NoSuchBackgroundJobException e) {
            render status: 404, text: 'job not found'
        } catch (InvalidStateBackgroundJobException e) {
            render status: 400, text: "Invalid job state"
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
