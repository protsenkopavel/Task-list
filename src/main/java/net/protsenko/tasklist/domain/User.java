package net.protsenko.tasklist.domain;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class User {

    Long id;

    String name;

    String password;

    List<Task> tasks;

}
