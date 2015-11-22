package backgroundjob


class StupidBackgroundJob extends BackgroundJob {

    private int count = 0
    private int delay = new Random().nextInt(100) + 50

    public StupidBackgroundJob(int id) {
        identifier = id
    }
    
    @Override
    public void run() {
        jobState = BackgroundJobState.RUNNING
        while (count < delay) {
            System.sleep(1000)
            count++
        }
        jobState = BackgroundJobState.FINISHED
    }

    @Override
    public int progress() {
        return (count * 100) / delay
    }
}
