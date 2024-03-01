//package net.weg.taskmanager.service;
//
//import lombok.AllArgsConstructor;
//import net.weg.taskmanager.repository.PropertiesRepository;
//import org.springframework.stereotype.Service;
//
//import java.util.Collection;
//
//@Service
//@AllArgsConstructor
//public class PropertyService {
//
//    private final PropertiesRepository propertiesRepository;
//
//    public Property findById(Integer id){return propertiesRepository.findById(id).get();}
//
//    public Collection<Property> findAll(){return propertiesRepository.findAll();}
//
//    public void delete(Integer id){
//        propertiesRepository.deleteById(id);}
//
//    public Property create(Property propriedade){return propertiesRepository.save(propriedade);}
//    public Property update(Property propriedade){return propertiesRepository.save(propriedade);}
//
//}
