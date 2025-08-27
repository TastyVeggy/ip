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

/**
 * Represents a list of tasks.
 */
public class TaskList {
    public static final String TASKS_SAVE_FILE = "data.dat";

    private ArrayList<Task> tasks;

    /**
     * Creates an empty TaskList.
     */
    public TaskList() {
        this.tasks = new ArrayList<>();
    }

    /**
     * Creates a TaskList consisting of tasks from a backup file.
     * 
     * @param filepath Filepath of the backup file.
     * @return The task lists created from the backup file.
     * @throws TaskListLoadBackupException If there is error in loading task
     * list from the backup file.
     */
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

    /**
     * Save TaskList in a backup file so it can be restored in the future.
     * 
     * @param filepath Filepath of the backup file.
     * @throws TaskListSaveBackupException If there is an error in saving the
     * task list into the backup file.
     */
    public void save(String filepath) throws TaskListSaveBackupException { try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filepath))) {
			out.writeObject(tasks);
		} catch (IOException e) {
            throw new TaskListSaveBackupException(e);
		}
    }
    

    /**
     * Add a Task into the task list.
     * 
     * @param task Task to be added to the task list.
     */
    public void add(Task task) {
        tasks.add(task);
    }

    /**
     * Returns the list of tasks within the TaskList.
     * 
     * @return the list of tasks within the TaskList.
     */
    public ArrayList<Task> getTasks() {
        return this.tasks;
    }

    /**
     * Mark a Task as done.
     * 
     * @param index Index of the Task to be marked with one-based indexing.
     * @return The Task after it is marked.
     * @throws TaskNotFoundException If the index is out of bounds and no
     * corresponding task can be found.
     */
    public Task markTask(int index) throws TaskNotFoundException {
        Task task = getTask(index);
        task.markAsDone();
        return task;
   }

    /**
     * Unmark a Task as done (ie. Mark it as undone).
     * 
     * @param index Index of the Task to be unmarked with one-based indexing.
     * @return The Task after it is unmarked.
     * @throws TaskNotFoundException If the index is out of bounds and no
     * corresponding task can be found.
     */
    public Task unmarkTask(int index) throws TaskNotFoundException {
        Task task = getTask(index);
        task.unmarkAsDone();
        return task;
    }

    /**
     * Delete a Task from the TaskList.
     * 
     * @param index Index of the Task to be deleted with one-based indexing.
     * @return The Task that has been deleted.
     * @throws TaskNotFoundException If the index is out of bounds and no
     * corresponding task can be found.
     */
    public Task deleteTask(int index) throws TaskNotFoundException {
        Task task = getTask(index);
        tasks.remove(index - 1);
        return task;
    }

    /**
     * Returns a Task from the TaskList.
     * 
     * @param index Index of the Task to be returned with one-based indexing.
     * @return The Task that has been selected.
     * @throws TaskNotFoundException If the index is out of bounds and no
     * corresponding task can be found.
     */
    public Task getTask(int index) throws TaskNotFoundException {
        if (index > tasks.size() || index <= 0) {
            throw new TaskNotFoundException(index);
        }
        return tasks.get(index - 1);
    }

    /**
     * Returns a string representation of the task list.
     * 
     * @return String representation of the task list.
     */
    public String getListStr()  {
        String s = "";
        for (int i = 0; i < tasks.size(); i++) {
            s += String.format("%d.%s", i + 1, tasks.get(i));
            if (i != tasks.size() - 1) {
                s += ("\n");
            }
        }
        return s;
    }

    /**
     * Returns the number of tasks stored in the TaskList.
     * 
     * @return The number of tasks stored in the TaskList.
     */
    public int getSize() {
        return tasks.size();
    }
}
