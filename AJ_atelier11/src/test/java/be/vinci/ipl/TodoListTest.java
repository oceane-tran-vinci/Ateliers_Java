package be.vinci.ipl;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TodoListTest {

  private TodoList todoList;
  private TodoList todoListWithTask1Task2;
  private Task task1;
  private Task task2;


  @BeforeEach
  void setUp() {
    todoList = new TodoList();
    task1 = new Task("title 1", "description task 1");
    task2 = new Task("title 2", "description task 2");
    todoListWithTask1Task2 = new TodoList();
    todoListWithTask1Task2.addTask(task1);
    todoListWithTask1Task2.addTask(task2);

  }

  @Test
  void addTask() {
    assertAll(() -> assertTrue(todoList.addTask(task1)),
        () -> assertTrue(todoList.containsTask(task1)));
  }

  @Test
  void addNullTask() {
    Task nullTask = null;
    assertAll(() -> assertFalse(todoList.addTask(nullTask)),
        () -> assertFalse(todoList.containsTask(nullTask)));

  }

  @Test
  void addExistingTask() {
    todoList.addTask(task1);
    Task task1Bis = new Task("title 1", "description task 1");
    assertFalse(todoList.addTask(task1Bis));
  }

  @Test
  void removeTask() {
    todoList.addTask(task1);
    assertAll(() -> assertTrue(todoList.removeTask(task1)),
        () -> assertFalse(todoList.containsTask(task1)));
  }

  @Test
  void removeUnexistingTask() {
    todoList.addTask(task1);
    assertFalse(todoList.removeTask(task2));
  }

  @Test
  void removeClonedTask() {
    todoList.addTask(task1);
    Task task1Cloned = new Task("title 1", "description task 1");

    assertAll(() -> assertTrue(todoList.removeTask(task1Cloned)),
        () -> assertFalse(todoList.containsTask(task1)));
  }

  @Test
  void findTask() {
    assertEquals(task1, todoListWithTask1Task2.findTask(task1));
  }

  @Test
  void findUnexistingTask() {
    Task task3 = new Task("title 3", "description task 3");

    assertNull(todoListWithTask1Task2.findTask(task3));
  }

  @Test
  void updateTodoListTask() {
    Task task1Clone = new Task("title 1", "description task 1");

    Task taskOne = new Task("title one", "description task one");

    assertAll(() -> assertTrue(todoListWithTask1Task2.updateTask(task1, taskOne)),
        () -> assertTrue(todoListWithTask1Task2.containsTask(taskOne)),
        () -> assertFalse(todoListWithTask1Task2.containsTask(task1Clone)));
  }

  @Test
  void updateTodoListUnexistingTask() {
    Task unexistingTask = new Task("title 4", "description task 4");

    Task taskOne = new Task("title one", "description task one");

    assertAll(() -> assertFalse(todoListWithTask1Task2.updateTask(unexistingTask, taskOne)),
        () -> assertFalse(todoListWithTask1Task2.containsTask(unexistingTask)));
  }

  @Test
  void updateTodoListwithNullTask() {
    Task nullTask = null;

    assertFalse(todoListWithTask1Task2.updateTask(task2, nullTask));

  }

  @Test
  void updateTodoListWhenCompletedTask() {
    Task completedTask = new Task("title", "description task");
    completedTask.complete();
    Task updatedTask = new Task("title updated", "description task updated");


    assertAll(() -> assertFalse(todoList.updateTask(completedTask, updatedTask)),
        () -> assertFalse(todoList.containsTask(updatedTask)));
  }
}
