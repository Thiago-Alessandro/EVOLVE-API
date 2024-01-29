package net.weg.taskmanager.service;

import lombok.AllArgsConstructor;
import net.weg.taskmanager.model.PermissionOfCards;
import net.weg.taskmanager.repository.PermissionOfCardsRepository;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
@AllArgsConstructor
public class PermissionOfCardsService {


    private final PermissionOfCardsRepository permissionOfCardsRepository;

    public PermissionOfCards findById(Integer id){return permissionOfCardsRepository.findById(id).get();}

    public Collection<PermissionOfCards> findAll(){return permissionOfCardsRepository.findAll();}

    public void delete(Integer id){
        permissionOfCardsRepository.deleteById(id);}

    public PermissionOfCards create(PermissionOfCards permissionOfCards){return permissionOfCardsRepository.save(permissionOfCards);}
    public PermissionOfCards update(PermissionOfCards permissionOfCards){return permissionOfCardsRepository.save(permissionOfCards);}

}
