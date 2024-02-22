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

    @GetMapping("/{id}")
    public Team findById(@PathVariable Integer id){return teamService.findById(id);}
    @GetMapping
    public Collection<Team> findAll(){return teamService.findAll();}
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id){
        teamService.delete(id);}
    @PostMapping
    public Team create(@RequestBody Team team){return teamService.create(team);}
    @PutMapping
    public Team update(@RequestBody Team team){
//        System.out.println(team);
        return teamService.update(team);}

}
