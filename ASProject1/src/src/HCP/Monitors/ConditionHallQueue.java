package HCP.Monitors;

import HCP.Utils.UnboundedQueue;

import java.util.concurrent.locks.Condition;

public class ConditionHallQueue {
    private final UnboundedQueue<QueuePositionData> waitingQueue = new UnboundedQueue<>();

    public void await(Condition cond) throws InterruptedException {
        QueuePositionData queuePosition = new QueuePositionData(cond);
        waitingQueue.enqueue(queuePosition);
        while (queuePosition.isFlag()) {
            cond.await();
        }

    }

    public void signal() {
        QueuePositionData queuePosition = waitingQueue.dequeue();
        queuePosition.setFlag(false);
        queuePosition.getCond().signal();
    }

    private class QueuePositionData {
        private final Condition cond;
        private boolean flag = true;

        public QueuePositionData(Condition cond) {
            this.cond = cond;
        }

        public boolean isFlag() {
            return flag;
        }

        public void setFlag(boolean flag) {
            this.flag = flag;
        }

        public Condition getCond() {
            return cond;
        }
    }
}
