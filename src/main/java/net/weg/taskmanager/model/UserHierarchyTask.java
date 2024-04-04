//package net.weg.taskmanager.model;
//
//import jakarta.persistence.*;
//import lombok.AllArgsConstructor;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//import net.weg.taskmanager.security.model.entity.Hierarchy;
//
//@Entity
//@AllArgsConstructor
//@Data
//@IdClass(UserHierarchyTaskId.class)
//@NoArgsConstructor
//public class UserHierarchyTask {
//    @Id
//    private Long userId;
//    @Id
//    private Long taskId;
//
//    @ManyToOne
//    @JoinColumn(name = "userId", insertable = false, updatable = false)
//    private User user;
//    @ManyToOne
//    @JoinColumn(name = "taskId", insertable = false, updatable = false)
//    private Task task;
//
//    @ManyToOne
//    private Hierarchy hierarchy;
//
//    //posso apagar (dentro da tarefa ele pode fazer o que quiser, somente dntro dela.
//
//}