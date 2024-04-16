package net.weg.taskmanager.controller;

import lombok.AllArgsConstructor;
import net.weg.taskmanager.model.entity.Team;
import net.weg.taskmanager.model.dto.get.GetTeamDTO;
import net.weg.taskmanager.service.TeamService;
import org.springframework.web.bind.annotation.*;

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
    @PostMapping
    public GetTeamDTO create(@RequestBody Team team){return service.create(team);}
    @PutMapping
    public GetTeamDTO update(@RequestBody Team team){
//        System.out.println(team);
        return service.update(team);}

    @GetMapping("/user/{userId}")
    public Collection<Team> findTeamsByUserId(@PathVariable Long userId){
        return service.findTeamsByUserId(userId);
    }

    @PatchMapping("/{teamId}")
    public GetTeamDTO patchTeamName(@PathVariable Long teamId, @RequestParam String name){
        return service.patchTeamName(teamId, name);
    }

}
