package backgroundjob

import org.grails.web.json.JSONObject

enum BackgroundJobState {
    NOT_STARTED, RUNNING, INTERRUPTED, FINISHED
}

abstract class BackgroundJob extends Thread {
    protected BackgroundJobState jobState = BackgroundJobState.NOT_STARTED
    public abstract int progress()
    protected int identifier
    protected String jobName
    protected Date startDate
    protected Date endDate
    protected JSONObject report = new JSONObject()

    public toJSON() {
        JSONObject ret = new JSONObject()
        ret << [ jobState:   jobState,
                 progress:   progress(),
                 identifier: identifier,
                 jobName:    jobName,
                 startDate:  startDate ? startDate.getTime() : null,
                 endDate:    endDate ? endDate.getTime() : null,
                 report:     report ]
        return ret
    }
}
