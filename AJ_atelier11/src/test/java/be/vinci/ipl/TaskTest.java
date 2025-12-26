package be.vinci.ipl;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TaskTest {

  private Task task1;

  @BeforeEach
  void setUp() {
    task1 = new Task("title1", "description1");
  }

  @Test
  void createTask() {
    assertAll(() -> assertEquals("title1", task1.getTitle()),
        () -> assertEquals("description1", task1.getDescription()));
  }

  @Test
  void createNullFieldsTask() {

    assertAll(() -> assertThrows(IllegalArgumentException.class,
            () -> new Task(null, "description1"))
        , () -> assertThrows(IllegalArgumentException.class,
            () -> new Task("title1", null)
        ));
  }

  @Test
  void createEmptyTitleTask() {
    assertThrows(IllegalArgumentException.class,
        () -> new Task(" ", "description1"));
  }

  @Test
  void completeTask() {
    assertTrue(task1.complete());
  }

  @Test
  void completeAlreadyCompletedTask() {
    task1.complete();
    assertFalse(task1.complete());
  }

  @Test
  void updateTitle() {
    assertAll(() -> assertTrue(task1.updateTitle("title one")),
        () -> assertEquals("title one", task1.getTitle()));
  }

  @Test
  void updateTitleWhenCompletedTask() {
    task1.complete();
    assertAll(() -> assertFalse(task1.updateTitle("title one")),
        () -> assertEquals("title1", task1.getTitle()));
  }

  @Test
  void updateTitleToEmptyOrNullString() {
    assertAll(() -> assertFalse(task1.updateTitle("")),
        () -> assertFalse(task1.updateTitle(null)));
  }

  @Test
  void updateDescription() {
    assertAll(() -> assertTrue(task1.updateDescription("description one")),
        () -> assertEquals("description one", task1.getDescription()));
  }

  @Test
  void updateDescriptionWhenCompletedTask() {
    task1.complete();
    assertAll(() -> assertFalse(task1.updateDescription("title one")),
        () -> assertEquals("description1", task1.getDescription()));
  }

  @Test
  void updateDescriptionToNull() {
    assertFalse(task1.updateDescription(null));
  }

}
