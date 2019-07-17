package com.codegym.controller;

import com.codegym.model.Type;
import com.codegym.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.data.domain.Pageable;

@Controller
public class TypeController {

    @Autowired
    private TypeService typeService;

    @GetMapping("/types")
    public ModelAndView list(Pageable pageable){
        ModelAndView modelAndView=new ModelAndView("/type/list");
        modelAndView.addObject("types",typeService.findAll(pageable));
        return modelAndView;
    }

    @GetMapping("/create-type")
    public ModelAndView showCreate(){
        ModelAndView modelAndView=new ModelAndView("/type/create");
        modelAndView.addObject("type",new Type());
        return modelAndView;
    }

    @PostMapping("/create-type")
    public ModelAndView create(@ModelAttribute("type") Type type){
        typeService.save(type);
        ModelAndView modelAndView=new ModelAndView("/type/create");
        modelAndView.addObject("type",new Type());
        modelAndView.addObject("message","New type is created successfully");
        return modelAndView;
    }

    @GetMapping("/edit-type/{id}")
    public ModelAndView showEdit(@PathVariable("id")Long id){
        Type type=typeService.findById(id);
        ModelAndView modelAndView=new ModelAndView("/type/edit");
        modelAndView.addObject("type",type);
        return  modelAndView;
    }
    @PostMapping("/edit-type")
    public ModelAndView edit(@ModelAttribute("type") Type type){
        typeService.save(type);
        ModelAndView modelAndView=new ModelAndView("/type/edit");
        modelAndView.addObject("message","Updated type successfully");
        return modelAndView;
    }

    @GetMapping("/delete-type/{id}")
    public ModelAndView showDelete(@PathVariable("id") Long id){
        Type type=typeService.findById(id);
        ModelAndView modelAndView=new ModelAndView("/type/delete");
        modelAndView.addObject("type",type);
        return modelAndView;
    }

    @PostMapping("/delete-type")
    public String delete(@ModelAttribute("type") Type type){
        typeService.remove(type.getId());
        return "redirect:/types";
    }
}
