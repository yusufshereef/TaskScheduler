package taskscheduler.datastructures.stack;

public class UndoStack {
    private static class UndoNode {
        UndoAction action;
        UndoNode next;

        UndoNode(UndoAction action) {
            this.action = action;
        }
    }

    private UndoNode top;

    public void push(UndoAction action) {
        UndoNode node = new UndoNode(action);
        node.next = top;
        top = node;
    }

    public UndoAction pop() {
        if (top == null) {
            return null;
        }

        UndoAction action = top.action;
        top = top.next;
        return action;
    }

    public boolean isEmpty() {
        return top == null;
    }
}

