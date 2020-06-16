package net.osgg.crudthymeleaf.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;


import net.osgg.crudthymeleaf.entities.Receta;
import net.osgg.crudthymeleaf.repository.RecetaRepo;
import net.osgg.crudthymeleaf.service.PictureService;


@Controller
@RequestMapping("/recetas/")
public class RecetaControlador {

	 @Autowired
	 private RecetaRepo repo;
	 
	 @Autowired
	    PictureService picService;
	 
	 @RequestMapping("/")
	 public String index() {
		return "index";
	 }
	 
	 @GetMapping("signup")
	 public String showSignUpForm(Receta receta) {
	     return "add_recipe";
	 }

	 
	 @GetMapping("list")
	 public String showRecipes(Model model) {
		 model.addAttribute("recipes", repo.findAll());
	     return "list_recipes";
	 }

	 @PostMapping("add")
	 //public String uploadToDB3(@ModelAttribute  Receta receta, @RequestParam("file") MultipartFile file) {
	 public String addRecipe(@ModelAttribute Receta receta, BindingResult result, Model model, @RequestParam("file") MultipartFile file) {
	     if (result.hasErrors()) {
	        return "add_recipe";
	     }
	     
	     UUID idPic = UUID.randomUUID();
	     picService.uploadPicture(file, idPic);
	     receta.setFoto(idPic);
	     repo.save(receta);
	     
	     return "redirect:list";
	 }

	 @GetMapping("edit/{id}")
	 public String showUpdateForm(@PathVariable("id") Long id, Model model) {
	     Receta receta = repo.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid recipe Id:" + id));
	     model.addAttribute("recipe", receta);
	     return "update_recipe";
	 }

	 @PostMapping("update/{id}")
	 //public String addRecipe(@ModelAttribute Receta receta, BindingResult result, Model model, @RequestParam("file") MultipartFile file) {
	 public String updateRecipe(@PathVariable("id") Long id, Receta receta, BindingResult result, Model model, @RequestParam("file") MultipartFile file) {
	     if (result.hasErrors()) {
	          receta.setId(id);
	          return "update_recipe";
	     }

	     System.out.println(receta.getId() + " " + receta.getNombre() +  " " + receta.getPreparacion() + 
	    		 " " + receta.getFoto() + " " + receta.getDificultad() + " " + file.getOriginalFilename() );
	     
	     if (!file.isEmpty()) {
	    	 picService.deletePicture(receta.getFoto());
		     UUID idPic = UUID.randomUUID();
		     picService.uploadPicture(file, idPic);
		     receta.setFoto(idPic);
	     }
	     repo.save(receta);
	     return "redirect:/recetas/list";
	 }

	 @GetMapping("delete/{id}")
	 public String deleteRecipe(@PathVariable("id") Long id, Model model) {
	     Receta receta = repo.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid recipe Id:" + id));
	     repo.delete(receta);
	     picService.deletePicture(receta.getFoto());
	     model.addAttribute("recipes", repo.findAll());
	     return "list_recipes";
	 }
}
