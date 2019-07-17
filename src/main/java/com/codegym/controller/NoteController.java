package com.codegym.controller;


import com.codegym.model.Note;
import com.codegym.model.Type;
import com.codegym.service.NoteService;
import com.codegym.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.data.domain.Pageable;

import java.util.Optional;


@Controller
public class NoteController {

    @Autowired
    private NoteService noteService;

    @Autowired
    private TypeService typeService;

    @ModelAttribute("types")
    public Iterable<Type> types(Pageable pageable) {
        return typeService.findAll(pageable);
    }

    @GetMapping("/notes")
    public ModelAndView list(@RequestParam("s") Optional<String> s, @PageableDefault(size = 10) Pageable pageable) {
        Page<Note> notes;
        if (s.isPresent()) {
            notes = noteService.findAllByTitleContainingOrContentContaining(s.get(), s.get(), pageable);
        } else {
            notes = noteService.findAll(pageable);
        }
        ModelAndView modelAndView = new ModelAndView("/note/list");
        modelAndView.addObject("notes", notes);
        return modelAndView;
    }

    @GetMapping("/create-note")
    public ModelAndView showCreate() {
        ModelAndView modelAndView = new ModelAndView("/note/create");
        modelAndView.addObject("note", new Note());
        return modelAndView;
    }

    @PostMapping("/create-note")
    public ModelAndView create(@ModelAttribute("note") Note note) {
        noteService.save(note);
        ModelAndView modelAndView = new ModelAndView("/note/create");
        modelAndView.addObject("note", new Note());
        modelAndView.addObject("message", "New note is created successfully");
        return modelAndView;
    }

    @GetMapping("/edit-note/{id}")
    public ModelAndView showEdit(@PathVariable("id") Long id) {
        Note note = noteService.findById(id);
        ModelAndView modelAndView = new ModelAndView("/note/edit");
        modelAndView.addObject("note", note);
        return modelAndView;
    }

    @PostMapping("/edit-note")
    public ModelAndView edit(@ModelAttribute("note") Note note) {
        noteService.save(note);
        ModelAndView modelAndView = new ModelAndView("/note/edit");
        modelAndView.addObject("message", "Updated note successfully");
        return modelAndView;
    }

    @GetMapping("/delete-note/{id}")
    public ModelAndView showDelete(@PathVariable("id") Long id) {
        Note note = noteService.findById(id);
        ModelAndView modelAndView = new ModelAndView("/note/delete");
        modelAndView.addObject("note", note);
        return modelAndView;
    }

    @PostMapping("/delete-note")
    public String delete(@ModelAttribute("note") Note note) {
        noteService.remove(note.getId());
        return "redirect:/notes";
    }

    @GetMapping("/view-note/{id}")
    public ModelAndView view(@PathVariable("id") Long id) {
        Note note = noteService.findById(id);
        String s=note.getContent();
        ModelAndView modelAndView = new ModelAndView("/note/view");
        modelAndView.addObject("note", note);
        return modelAndView;
    }

}
