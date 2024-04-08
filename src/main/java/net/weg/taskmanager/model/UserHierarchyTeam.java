//package net.weg.taskmanager.model;
//
//
//import jakarta.persistence.*;
//import lombok.AllArgsConstructor;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//import net.weg.taskmanager.security.model.entity.ProfileAcess;
//
//@Entity
//@AllArgsConstructor
//@Data
//@IdClass(UserHierarchyTeamId.class)
//@NoArgsConstructor
//public class UserHierarchyTeam {
//    @Id
//    private Long userId;
//    @Id
//    private Long teamId;
//
//    @ManyToOne
//    @JoinColumn(name = "userId", insertable = false, updatable = false)
//    private User user;
//    @ManyToOne
//    @JoinColumn(name = "teamId", insertable = false, updatable = false)
//    private Team team;
//
//    @ManyToOne
//    private ProfileAcess hierarchy;
//}