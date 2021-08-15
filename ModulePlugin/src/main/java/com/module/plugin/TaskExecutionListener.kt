package com.module.plugin

import org.gradle.api.Project
import org.gradle.api.Task
import org.gradle.api.execution.TaskExecutionAdapter
import org.gradle.api.tasks.TaskState

class TaskExecutionListener(project: Project) : TaskExecutionAdapter() {
    override fun beforeExecute(task: Task) {
        super.beforeExecute(task)
        println("TaskExecutionListener beforeExecute task = $task")
    }

    override fun afterExecute(task: Task, state: TaskState) {
        super.afterExecute(task, state)
        println("TaskExecutionListener afterExecute task = $task")
    }
}