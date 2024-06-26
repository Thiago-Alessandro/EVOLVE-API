package net.weg.taskmanager.controller;

import lombok.AllArgsConstructor;
import net.weg.taskmanager.model.dto.UserTeamDTO;
import net.weg.taskmanager.model.dto.UserTeamDTO2;
import net.weg.taskmanager.model.dto.get.GetTeamDTO;
import net.weg.taskmanager.model.dto.get.GetTeamNotificationDTO;
import net.weg.taskmanager.model.entity.Team;
import net.weg.taskmanager.model.entity.UserTeam;
import net.weg.taskmanager.security.model.entity.Role;
import net.weg.taskmanager.service.TeamService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.management.InvalidAttributeValueException;
import java.util.Collection;

@RestController
@RequestMapping("/team")
@AllArgsConstructor
public class TeamController {

    private final TeamService service;

    @GetMapping("/{teamId}")
    public GetTeamDTO findById(@PathVariable Long teamId){return service.findById(teamId);}

    @DeleteMapping("/{teamId}")
    public void delete(@PathVariable Long teamId){service.delete(teamId);}

    @PostMapping
    public GetTeamDTO create(@RequestBody Team team){
        return
            service.create(team);}

    @GetMapping("/user/{userId}")
    public Collection<UserTeamDTO2> findTeamsByUserId(@PathVariable Long userId){
        return service.findTeamsByUserId(userId);
    }



    @GetMapping("/{teamId}/notifications")
    public Collection<GetTeamNotificationDTO> getAllNotifications(@PathVariable Long teamId) {
        return service.getAllNotifications(teamId);
    }

//    @PutMapping
//    public GetTeamDTO update(@RequestBody Team team){
//        return service.update(team);}



    @PatchMapping("/{teamId}/name")
    public ResponseEntity<GetTeamDTO> patchName(@PathVariable Long teamId, @RequestParam String name){
        try {
            return ResponseEntity.ok(service.patchName(teamId, name));
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
        }
    }

    @PatchMapping("/{teamId}/image")
    public ResponseEntity<GetTeamDTO> patchImage(@PathVariable Long teamId, @RequestParam MultipartFile image){
        try {
            return ResponseEntity.ok(service.patchImage(teamId, image));
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
        }
    }

    @PatchMapping("/{teamId}/image/remove")
    public ResponseEntity<GetTeamDTO> patchImageRemove(@PathVariable Long teamId) {
        try {
            return ResponseEntity.ok(service.patchImageRemove(teamId));
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
        }
    }
    @PatchMapping("/{teamId}/imageColor")
    public ResponseEntity<GetTeamDTO> patchImageColor(@PathVariable Long teamId, @RequestParam String imageColor) throws InvalidAttributeValueException {
//        try {
            return ResponseEntity.ok(service.patchImageColor(teamId, imageColor));
//        } catch (Exception e){
//            return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
//        }
    }

    @PatchMapping("/{teamId}/participants")
    public ResponseEntity<GetTeamDTO> patchParticipants(@PathVariable Long teamId, @RequestBody Collection<UserTeamDTO> participants) throws InvalidAttributeValueException {
//        try {
        System.out.println("CHEGUEI AQUI NA CONTROLLER EIIIII");
            return ResponseEntity.ok(service.patchParticipants(teamId, participants));
//        } catch (Exception e){
//            return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
//        }
    }
    @PutMapping("/code/{teamId}/participant")
    public ResponseEntity<GetTeamDTO> patchParticipantsByCode(@PathVariable Long teamId, @RequestParam Long userId) throws InvalidAttributeValueException {
//        try {
        return ResponseEntity.ok(service.patchParticipantsByCode(teamId, userId));
//        } catch (Exception e){
//            return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
//        }
    }

    @PutMapping("/{teamId}/leaveTeam")
    public ResponseEntity<GetTeamDTO> patchParticipantsLeaveTeam(@PathVariable Long teamId, @RequestParam Long userId){
        return ResponseEntity.ok(service.patchParticipantsLeaveTeam(teamId, userId));
    }

    @PatchMapping("/{teamId}/roles")
    public ResponseEntity<GetTeamDTO> patchRoles(@PathVariable Long teamId, @RequestBody Collection<Role> roles){
        try {
            return ResponseEntity.ok(service.patchRoles(teamId, roles));
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
        }
    }
    @PatchMapping("/{teamId}/defaultRole")
    public ResponseEntity<GetTeamDTO> patchDefaultRole(@PathVariable Long teamId, @RequestBody Role deafaultRole) {
        try {
            return ResponseEntity.ok(service.patchDefaultRole(teamId, deafaultRole));
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
        }
    }
    @GetMapping("code/{teamCode}")
    public ResponseEntity<GetTeamDTO> getTeamByCode(@PathVariable String teamCode) {
        try {
            return ResponseEntity.ok(service.findByCode(teamCode));
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


//    @PatchMapping("/{teamId}/projects")
//    public ResponseEntity<GetTeamDTO> patchProjects(@PathVariable Long teamId, @RequestBody Collection<Project> projects){
//        try {
//            return ResponseEntity.ok(service.patchProjects(teamId, projects));
//        } catch (Exception e){
//            return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
//        }
//    }//projeto? não aparentemente

    @PatchMapping("/{teamId}/{notificationId}")
    public GetTeamDTO patchReadedNotification(@PathVariable Long teamId, @PathVariable Long notificationId) {
        return service.patchReadedNotification(teamId,notificationId);
    }


    @DeleteMapping("/clean/{loggedUserId}")
    public void cleanAllUserNotifications(@PathVariable Long loggedUserId) {
        service.cleanAllUserNotifications(loggedUserId);

    }

}
