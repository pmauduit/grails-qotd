package backgroundjob


enum BackgroundJobState {
    NOT_STARTED, RUNNING, INTERRUPTED, FINISHED
}

abstract class BackgroundJob extends Thread {
    protected BackgroundJobState jobState = BackgroundJobState.NOT_STARTED
    public abstract int progress()
}
