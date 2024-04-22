package net.weg.taskmanager.controller;

import lombok.AllArgsConstructor;
import net.weg.taskmanager.model.entity.Team;
import net.weg.taskmanager.model.dto.get.GetTeamDTO;
import net.weg.taskmanager.model.entity.User;
import net.weg.taskmanager.service.TeamService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.management.InvalidAttributeValueException;
import java.util.Collection;

@RestController
@RequestMapping("/team")
@AllArgsConstructor
public class TeamController {

    private final TeamService service;

    @GetMapping("/{id}")
    public GetTeamDTO findById(@PathVariable Long id){return service.findById(id);}
    @GetMapping
    public Collection<GetTeamDTO> findAll(){return service.findAll();}
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id){
        service.delete(id);}
    @PostMapping("/admin/{adminId}")
    public GetTeamDTO create(@PathVariable Long adminId){return service.create(adminId);}
    @PutMapping
    public GetTeamDTO update(@RequestBody Team team){
        return service.update(team);
    }

    @GetMapping("/user/{userId}")
    public Collection<Team> findTeamsByUserId(@PathVariable Long userId){
        return service.findTeamsByUserId(userId);
    }


    @PatchMapping("/{teamId}/name")
    public GetTeamDTO patchTeamName(@PathVariable Long teamId, @RequestParam String name) throws InvalidAttributeValueException {
        return service.patchTeamName(teamId, name);
    }

    @PatchMapping("/{teamId}/participants")
    public GetTeamDTO patchParticipants(@PathVariable Long teamId, @RequestParam Collection<User> participants) throws InvalidAttributeValueException {
        return service.patchParticipants(teamId, participants);
    }

    @PatchMapping("/{teamId}/imageColor")
    public GetTeamDTO patchImageColor(@PathVariable Long teamId, @RequestParam String imageColor) throws InvalidAttributeValueException {
        return service.patchImageColor(teamId, imageColor);
    }

    @PatchMapping("/{teamId}/image")
    public GetTeamDTO patchImage(@PathVariable Long teamId, @RequestParam MultipartFile image) throws InvalidAttributeValueException {
        return service.patchImage(teamId, image);
    }

}
