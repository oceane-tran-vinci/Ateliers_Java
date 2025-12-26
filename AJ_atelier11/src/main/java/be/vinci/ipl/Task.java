package be.vinci.ipl;

import java.util.Objects;

public class Task {
  private String title;
  private String description;

  private boolean completed;

  public Task(String title, String description) {
    if (title == null) {
      throw new IllegalArgumentException("title cannot be null");
    }
    if (description == null) {
      throw new IllegalArgumentException("description cannot be null");
    }
    if (title.isBlank()) {
      throw new IllegalArgumentException("title cannot be empty");
    }
    this.title = title;
    this.description = description;
  }

  public String getTitle() {
    return title;
  }

  public String getDescription() {
    return description;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    Task task = (Task) o;

    if (!Objects.equals(title, task.title)) {
      return false;
    }
    return Objects.equals(description, task.description);
  }

  @Override
  public int hashCode() {
    int result = title != null ? title.hashCode() : 0;
    result = 31 * result + (description != null ? description.hashCode() : 0);
    return result;
  }

  public boolean complete() {
    if (completed) {
      return false;
    }
    completed = true;
    return true;
  }

  public boolean updateTitle(String newTitle) {
    if (completed) {
      return false;
    }
    if (newTitle == null || newTitle.isBlank()) {
      return false;
    }

    title = newTitle;
    return true;
  }

  public boolean updateDescription(String newDescription) {
    if (newDescription == null)
      return false;
    if (completed) {
      return false;
    }
    description = newDescription;
    return true;
  }

  public boolean isCompleted() {
    return completed;
  }
}
