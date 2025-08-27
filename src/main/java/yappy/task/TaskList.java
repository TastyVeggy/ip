package yappy.task;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import yappy.task.exception.TaskListLoadBackupException;
import yappy.task.exception.TaskListSaveBackupException;
import yappy.task.exception.TaskNotFoundException;

public class TaskList {
    public static final String TASKS_SAVE_FILE = "data.dat";

    private ArrayList<Task> tasks;

    public TaskList() {
        this.tasks = new ArrayList<>();
    }

    public static TaskList usingBackup(String filepath) throws TaskListLoadBackupException {
        TaskList tasks = new TaskList();
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(filepath))) {
            @SuppressWarnings("unchecked")
            ArrayList<Task> loadedTasks = (ArrayList<Task>) in.readObject();
            for (Task task : loadedTasks) {
                tasks.add(task);
            }
            return tasks;
        } catch (IOException | ClassNotFoundException e) {
            throw new TaskListLoadBackupException(e, filepath);
        }
    }

    public void save(String filepath) throws TaskListSaveBackupException {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filepath))) {
            out.writeObject(tasks);
        } catch (IOException e) {
            throw new TaskListSaveBackupException(e);
        }
    }

    public void add(Task task) {
        tasks.add(task);
    }

    public ArrayList<Task> getTasks() {
        return this.tasks;
    }

    public Task markTask(int index) throws TaskNotFoundException {
        Task task = getTask(index);
        task.markAsDone();
        return task;
    }

    public Task unmarkTask(int index) throws TaskNotFoundException {
        Task task = getTask(index);
        task.unmarkAsDone();
        return task;
    }

    public Task deleteTask(int index) throws TaskNotFoundException {
        Task task = getTask(index);
        tasks.remove(index - 1);
        return task;
    }

    public Task getTask(int index) throws TaskNotFoundException {
        if (index > tasks.size() || index <= 0) {
            throw new TaskNotFoundException(index);
        }
        return tasks.get(index - 1);
    }

    /**
     * Returns a short list of tasks containing tasks with the desired keyword in the task
     * description.
     * 
     * @param keyword Keyword for which we wish to search for in the task description
     * @return Short-listed task
     */
    public TaskList getShortListWithKeyword(String keyword) {
        TaskList TaskShortList = new TaskList();

        for (Task task : this.tasks) {
            if (task.containsInDescription(keyword)) {
                TaskShortList.add(task);
            }
        }
        return TaskShortList;
    }

    public String getListStr() {
        String s = "";
        for (int i = 0; i < tasks.size(); i++) {
            s += String.format("%d.%s", i + 1, tasks.get(i));
            if (i != tasks.size() - 1) {
                s += ("\n");
            }
        }
        return s;
    }

    public int getSize() {
        return tasks.size();
    }
}
