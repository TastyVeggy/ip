# Yappy User Guide

<p align="center">
    <img src='./Ui.png' alt="Yappy preview" style="width:400px; height: auto;">
    <br />
    <b>Yappy preview</b>
</p>



## Introduction

Yappy is a **todo list chatbot** that helps you organize and manage tasks through simple text commands.
You can quickly add tasks, set deadlines, track events or search for items.

Yappy supports these task types:

* **Todo** – a simple task to complete
* **Deadline** – a task with a due date
* **Event** – a task scheduled at a specific time
* **Fixed Duration Task** – a task with a set start and end time

### Quick overview of supported commands and their usage

> [!NOTE]
>* Regarding the command format:
>   * Unbracketed words within `<>` are the parameters to be supplied by the user.  
>      e.g. in `todo <description>`, `description` is a parameter which can be used as `todo buy groceries`.  
>   * Words within `()` within a `<>` indicates that it is the type of the parameter enclosed within `<>`.  
>      e.g. in `mark <task index (int)>`, `int` is the type of the parameter `task index`.  
>      For parameters with unspecified type, it can be assumed to be a `string`.    
>   * Parameters are **non-optional** and **non-empty**, and need to be strictly in the order prescribed within the format.     
>   * Extraneous parameters for commands that do not take in parameters (such as `bye` and `list`) will not be executed successfully. 
>* For arguments of `datetime` type, there are currently two supported formats:
>   * dd-MM-yyyy HH:mm (eg. `07-08-2025 07:00`)
>   * d MMM yyyy, HH:mm (eg. `7 Aug 2025, 07:00`)
>* For arguments of `duration` type, the supported format is XhYmZs (eg. `2h15m30s`) or any subset of hours, mins and sec (ie. `2h` or `15m30s` or `2h30s`) are also supported.

* `list`
* `todo <description>`
* `deadline <description> /by <deadline (datetime)>`
* `event <description> /from <start time (datetime)> /to <end time (datetime)>`
* `fixed_duration <description> /time <duration (duration)>`
* `mark <task index (int)>`
* `unmark <task index (int)>`
* `delete <task index (int)>`
* `find <keyword>`
* `bye`

## Features


### Listing tasks: `list`
Lists all tasks stored within the todo list.

Format: `list`

### Adding a todo task: `todo`
Adds a todo (simple task with no deadlines or duration) to the todo list.

Format:`todo <description>`

Example: `todo buy grocery`

### Adding a task with deadline: `deadline`
Adds a task with deadline to the todo list.

Format: `deadline <description> /by <deadline (datetime)>`

Example: `deadline submit report /by 25 Nov 2025, 11:00`

* `fixed_duration <description> /time <duration (duration)>`
* `mark <task index (int)>`
* `unmark <task index (int)>`
* `delete <task index (int)>`
* `find <keyword>`
* `bye`

### Adding an event: `event`

Adds an event that happen during a specific period to the todo list.

Format: `event <description> /from <start time (datetime)> /to <end time (datetime)>`

Example: `event dental appointment /from 7 Dec 2025, 09:00 /to 07-12-2025 10:00`


### Adding a fixed-duration task: `fixed_duration`

Adds a task with a fixed duration but without start/end date to the todo list.

Format: `fixed_duration <description> /time <duration (duration)>`

Example: `fixed_duration water the plants /time 30m`


### Marking a task as done: `mark`

Marks a task within the todo list as done.

Format: `mark <task index(int)>`

Example: `mark 1`

### Unmarking a task as done: `unmark`

Unmarks a task within the todo list as done (i.e. the task is not done yet).

Format: `unmark <task index(int)>`

Example: `unmark 1`

### Deleting a task: `delete`

Deletes a task from the todo list.

Format: `delete <task index(int)>`

Example: `delete 1`

### Find tasks with keyword in description: `find`

Finds tasks within the todo list with the given keyword (case-sensitive).

Format: `find <keyword>`

Example: `find mum`

### Exiting the program: `bye`

Exits the program.

Format: `bye`
