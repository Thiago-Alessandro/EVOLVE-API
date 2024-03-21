package net.weg.taskmanager.service;

import lombok.AllArgsConstructor;
import net.weg.taskmanager.model.entity.CardsPermission;
import net.weg.taskmanager.repository.CardsPermissionRepository;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
@AllArgsConstructor
public class CardsPermissionService {


    private final CardsPermissionRepository permissionOfCardsRepository;

    public CardsPermission findById(Long id){return permissionOfCardsRepository.findById(id).get();}

    public Collection<CardsPermission> findAll(){return permissionOfCardsRepository.findAll();}

    public void delete(Long id){
        permissionOfCardsRepository.deleteById(id);}

    public CardsPermission create(CardsPermission permissionOfCards){return permissionOfCardsRepository.save(permissionOfCards);}
    public CardsPermission update(CardsPermission permissionOfCards){return permissionOfCardsRepository.save(permissionOfCards);}

}
