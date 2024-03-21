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

    private final TeamService teamService;

    @GetMapping("/{id}")
    public GetTeamDTO findById(@PathVariable Long id){return teamService.findById(id);}
    @GetMapping
    public Collection<GetTeamDTO> findAll(){return teamService.findAll();}
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id){
        teamService.delete(id);}
    @PostMapping
    public GetTeamDTO create(@RequestBody Team team){return teamService.create(team);}
    @PutMapping
    public GetTeamDTO update(@RequestBody Team team){
//        System.out.println(team);
        return teamService.update(team);}

}
