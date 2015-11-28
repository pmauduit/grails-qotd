package backgroundjob


class StupidBackgroundJob extends BackgroundJob {

    private int count = 0
    private int delay = new Random().nextInt(100) + 50

    public StupidBackgroundJob(int id) {
        identifier = id
        jobName = this.getClass().getName()
    }
    
    @Override
    public void run() {
        startDate = new Date()
        jobState = BackgroundJobState.RUNNING
        while (count < delay) {
            System.sleep(1000)
            count++
        }
        endDate = new Date()
        report.put("htmlReport", "<p>I did nothing during \
            ${1.0 * (endDate.getTime() - startDate.getTime()) / 1000.0} seconds</p>")
        jobState = BackgroundJobState.FINISHED
    }

    @Override
    public int progress() {
        return (count * 100) / delay
    }
}
