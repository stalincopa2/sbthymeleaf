package net.osgg.crudthymeleaf.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import net.osgg.crudthymeleaf.entities.Receta;

public interface RecetaRepo extends CrudRepository <Receta, Long>{

	List<Receta> findByNombre(String nombre); 
	
	List<Receta> findByPreparacion(String preparacion);
}
