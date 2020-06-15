package net.osgg.crudthymeleaf.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;


import net.osgg.crudthymeleaf.entities.Receta;
import net.osgg.crudthymeleaf.repository.RecetaRepo;


@Controller
@RequestMapping("/recetas/")
public class RecetaControlador {

	 @Autowired
	 private RecetaRepo repo;
	 
	 
	 @RequestMapping("/")
	 public String index() {
		return "index";
	 }
	 
	 @GetMapping("signup")
	 public String showSignUpForm(Receta receta) {
	     return "add_recipe";
	 }

	 
	 @GetMapping("list")
	 public String showUpdateForm(Model model) {
		 model.addAttribute("recipes", repo.findAll());
	     return "list_recipes";
	 }

	 @PostMapping("add")
	 public String addStudent(Receta receta, BindingResult result, Model model) {
	     if (result.hasErrors()) {
	        return "add_recipe";
	     }
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
	 public String updateRecipe(@PathVariable("id") Long id, Receta receta, BindingResult result, Model model) {
	     if (result.hasErrors()) {
	          receta.setId(id);
	          return "update_recipe";
	     }

	     repo.save(receta);
	        model.addAttribute("recipes", repo.findAll());
	        return "list_recipes";
	 }

	 @GetMapping("delete/{id}")
	 public String deleteStudent(@PathVariable("id") Long id, Model model) {
	     Receta receta = repo.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid recipe Id:" + id));
	     repo.delete(receta);
	     model.addAttribute("recipes", repo.findAll());
	     return "list_recipes";
	 }
}
