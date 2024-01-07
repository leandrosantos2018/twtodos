package com.nmdsoluecoes.twtodos.Controllers;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

import com.nmdsoluecoes.twtodos.models.Todo;
import com.nmdsoluecoes.twtodos.repositories.TodoRepository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.server.ResponseStatusException;




                               

@Controller
public class TodoController {

   
   private final  TodoRepository todoRepository;

    public TodoController(TodoRepository todoRepository){
        this.todoRepository = todoRepository;

    }

    @GetMapping("/")
    public ModelAndView list(){
             
        return new ModelAndView(
            "todo/list", 
            Map.of("todos",todoRepository.findAll())
            );
    }

    @GetMapping("/create")
    public ModelAndView create(){

        return new ModelAndView("todo/form", Map.of("todo", new Todo()));
    }

    @PostMapping("/create")
    public String create(@Validated Todo td, BindingResult result) {
        if (result.hasErrors()) {
            return "todo/form";
        }

    var deadline = LocalDateTime.parse(td.getDeadline().toString(), DateTimeFormatter.ISO_LOCAL_DATE_TIME);
    td.setDeadline(deadline);
    
        todoRepository.save(td);
        return "redirect:/";
    }


    @GetMapping("/edit/{id}")
    public ModelAndView  edit(@Validated @PathVariable Long id) {
        var todo = todoRepository.findById(id);
        if(todo.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
         return new ModelAndView("todo/form", Map.of("todo",todo.get()));
    }
    
    @PostMapping("/edit/{id}")
    public String edit(@Validated Todo todo) {
        todoRepository.save(todo);
              return "redirect:/";
    }


    @GetMapping("/delete/{id}")
    public ModelAndView delete(@PathVariable Long id) {
         var todo = todoRepository.findById(id);
        if(todo.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return new ModelAndView("todo/delete",Map.of("todo",todo.get()));
    }
    
    @PostMapping("/delete/{id}")
    public String delete(Todo todo) {
        todoRepository.delete(todo);
        return "redirect:/";
    }
    
    

    
}
