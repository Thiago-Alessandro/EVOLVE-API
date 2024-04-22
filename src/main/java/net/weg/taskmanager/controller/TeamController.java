package net.weg.taskmanager.controller;

import lombok.AllArgsConstructor;
import net.weg.taskmanager.model.Team;
import net.weg.taskmanager.service.TeamService;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/team")
@AllArgsConstructor
public class TeamController {

    private final TeamService teamService;

    @GetMapping("/{teamId}")
    public Team findById(@PathVariable Long teamId){return teamService.findById(teamId);}
    @GetMapping
    public Collection<Team> findAll(){return teamService.findAll();}
    @DeleteMapping("/{teamId}")
    public void delete(@PathVariable Long teamId){
        teamService.delete(teamId);}
    @PostMapping
    public Team create(@RequestBody Team team){return teamService.create(team);}
    @PutMapping
    public Team update(@RequestBody Team team){
        return teamService.update(team);}

}
