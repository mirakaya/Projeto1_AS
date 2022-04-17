package HCP.Monitors.WTH;

import HCP.Enums.PatientEvaluation;
import HCP.Utils.UnboundedQueue;

import java.util.HashMap;
import java.util.concurrent.locks.Condition;

public class ConditionEvaluationQueue {
    private final HashMap<PatientEvaluation, UnboundedQueue<QueuePositionData>> evaluationQueue = new HashMap<>();
    private final PatientEvaluation[] orderedEvals;

    public ConditionEvaluationQueue() {
        PatientEvaluation[] evals = PatientEvaluation.values();
        orderedEvals = new PatientEvaluation[evals.length - 1];

        for (int i = evals.length - 1, j = 0; i > 0; i--, j++) {
            orderedEvals[j] = evals[i];
        }

        for (PatientEvaluation eval: orderedEvals) {
            evaluationQueue.put(eval, new UnboundedQueue<>());
        }
    }

    public void await(Condition cond, PatientEvaluation evaluation) {
        try {
            QueuePositionData queuePosition = new QueuePositionData(cond);
            evaluationQueue.get(evaluation).enqueue(queuePosition);
            while (queuePosition.flag) {
                cond.await();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void signal() {
        for (PatientEvaluation eval: orderedEvals) {
            var queue = evaluationQueue.get(eval);
            if (!queue.isEmpty()) {
                var queuePosition = queue.dequeue();
                queuePosition.setFlag(false);
                queuePosition.getCond().signal();
            }
        }
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
