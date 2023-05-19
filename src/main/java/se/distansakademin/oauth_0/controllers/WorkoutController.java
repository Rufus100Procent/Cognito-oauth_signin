package se.distansakademin.oauth_0.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import se.distansakademin.oauth_0.models.Workout;
import se.distansakademin.oauth_0.services.WorkoutService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/workouts")
public class WorkoutController {
    private final WorkoutService workoutService;

    @Autowired
    public WorkoutController(WorkoutService workoutService) {
        this.workoutService = workoutService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<Workout>> getAllWorkouts() {
        List<Workout> workouts = workoutService.getAllWorkouts();
        return new ResponseEntity<>(workouts, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Workout> getWorkoutById(@PathVariable String id) {
        Optional<Workout> workout = Optional.ofNullable(workoutService.getWorkoutById(id));
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/create")
    public ResponseEntity<Workout> addWorkout(@RequestBody Workout workout) {
        Workout createdWorkout = workoutService.addWorkout(workout);
        return new ResponseEntity<>(createdWorkout, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteWorkoutById(@PathVariable String id) {
        workoutService.deleteWorkoutById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Workout> updateWorkout(@PathVariable String id, @RequestBody Workout workout) {
        Workout updatedWorkout = workoutService.updateWorkout(id, workout);
        if (updatedWorkout != null) {
            return new ResponseEntity<>(updatedWorkout, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
