//package net.weg.taskmanager.repository;
//
//import net.weg.taskmanager.model.UserHierarchyTask;
//import net.weg.taskmanager.security.model.entity.Hierarchy;
//import net.weg.taskmanager.security.model.enums.Auth;
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.stereotype.Repository;
//
//@Repository
//public interface UserHierarchyTaskRepository extends JpaRepository<UserHierarchyTask, Long> {
//    boolean existsByUserIdAndTaskIdAndHierarchy_AuthsContaining(Long userId, Long taskId, Auth auth);
//    Hierarchy findByUserIdAndTaskIdAndHierarchy_AuthsContaining(Long userId, Long taskId, Auth auth);
//}
