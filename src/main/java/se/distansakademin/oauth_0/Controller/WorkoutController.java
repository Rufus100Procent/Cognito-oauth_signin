package se.distansakademin.oauth_0.Controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import se.distansakademin.oauth_0.Model.Workout;
import se.distansakademin.oauth_0.Service.WorkoutService;

import java.util.List;

@RequestMapping("/workouts")
@Controller
public class WorkoutController {
    private final WorkoutService workoutService;

    @Autowired
    public WorkoutController(WorkoutService workoutService) {
        this.workoutService = workoutService;
    }

    @GetMapping("/all")
    public String getAllWorkouts(Model model) {
        List<Workout> workouts = workoutService.getAllWorkouts();
        model.addAttribute("workouts", workouts);
        return "workout/details";
    }

    @GetMapping("/{id}")
    public String getWorkoutById(@PathVariable String id, Model model) {
        Workout workout = workoutService.getWorkoutById(id);
        if (workout != null) {
            model.addAttribute("workout", workout);
            return "workout/details";
        }
        return "404";
    }

    @GetMapping("/new")
    public String createWorkoutForm(Model model) {
        model.addAttribute("workout", new Workout());
        return "workout/workoutform";
    }


    @PostMapping("/new")
    public String addWorkout(@ModelAttribute Workout workout) {
        Workout createdWorkout = workoutService.addWorkout(workout);
        return "redirect:/all" + createdWorkout.getId();
    }

    @PostMapping("/{id}/delete")
    public String deleteWorkoutById(@PathVariable String id) {
        workoutService.deleteWorkoutById(id);
        return "redirect:/workouts/all";
    }
}