package com.example.taskmate.ui;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class TaskData implements Parcelable {

    private String taskTitle, taskDescription;
    private boolean isTaskCompleted;
    private String taskDueDate;

    public TaskData(String taskTitle, String taskDescription, boolean isTaskCompleted, String taskDueDate) {
        this.taskTitle = taskTitle;
        this.taskDescription = taskDescription;
        this.isTaskCompleted = isTaskCompleted;
        this.taskDueDate = taskDueDate;
    }

    protected TaskData(Parcel in) {
        taskTitle = in.readString();
        taskDescription = in.readString();
        isTaskCompleted = in.readByte() != 0;
        taskDueDate = in.readString();
    }

    public static final Creator<TaskData> CREATOR = new Creator<TaskData>() {
        @Override
        public TaskData createFromParcel(Parcel in) {
            return new TaskData(in);
        }

        @Override
        public TaskData[] newArray(int size) {
            return new TaskData[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeString(taskTitle);
        parcel.writeString(taskDescription);
        parcel.writeByte((byte) (isTaskCompleted ? 1 : 0));
        parcel.writeString(taskDueDate);
    }

    public String getTaskTitle() {
        return taskTitle;
    }

    public void setTaskTitle(String taskTitle) {
        this.taskTitle = taskTitle;
    }

    public String getTaskDescription() {
        return taskDescription;
    }

    public void setTaskDescription(String taskDescription) {
        this.taskDescription = taskDescription;
    }

    public boolean isTaskCompleted() {
        return isTaskCompleted;
    }

    public void setTaskCompleted(boolean taskCompleted) {
        isTaskCompleted = taskCompleted;
    }

    public String getTaskDueDate() {
        return taskDueDate;
    }

    public void setTaskDueDate(String taskDueDate) {
        this.taskDueDate = taskDueDate;
    }
}
