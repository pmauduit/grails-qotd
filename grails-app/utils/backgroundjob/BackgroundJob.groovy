package backgroundjob

import org.grails.web.json.JSONObject

enum BackgroundJobState {
    NOT_STARTED, RUNNING, INTERRUPTED, FINISHED
}

abstract class BackgroundJob extends Thread {
    protected BackgroundJobState jobState = BackgroundJobState.NOT_STARTED
    public abstract int progress()
    
    public toJSON() {
        JSONObject ret = new JSONObject()
        ret << [jobState : jobState, progress : progress() ]
        return ret
    }
}
