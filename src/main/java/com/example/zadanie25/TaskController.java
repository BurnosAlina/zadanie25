package com.example.zadanie25;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class TaskController {

    private TaskRepository taskRepository;

    public TaskController(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @GetMapping("/")
    String home(Model model) {
        model.addAttribute("task", new Task());
        return "index";
    }

    @PostMapping("/add")
    String add(Task task) {
        taskRepository.save(task);
        return "redirect:/";
    }

    @GetMapping("/notDoneList")
    String notDoneList(Model model) {
        List<Task> notDoneTasks = taskRepository.findAllByIsDoneOrderByDeadline(false);
        model.addAttribute("tasks", notDoneTasks);
        return "tasksList";
    }

    @GetMapping("/doneList")
    String doneList(Model model) {
        List<Task> notDoneTasks = taskRepository.findAllByIsDoneOrderByDeadline(true);
        model.addAttribute("tasks", notDoneTasks);
        return "tasksList";
    }

    @GetMapping("/done/{id}")
    String showTaskInfo(@PathVariable Long id) {
        Task task = taskRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid task id: " + id));
        task.setIsDone(true);
        taskRepository.save(task);
        return "redirect:/";
    }

    @GetMapping("/edit/{id}")
    String editTask(@PathVariable Long id, Model model) {
        Task task = taskRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid task id: " + id));
        model.addAttribute("task", task);
        return "edit";
    }

    @PostMapping("/edit/{id}")
    String edit(Task task) {
        taskRepository.save(task);
        return "redirect:/";
    }
}
