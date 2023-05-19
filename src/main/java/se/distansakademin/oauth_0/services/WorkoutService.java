package se.distansakademin.oauth_0.services;

import org.springframework.stereotype.Service;
import se.distansakademin.oauth_0.models.Workout;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import se.distansakademin.oauth_0.repositories.WorkoutRepository;


@Service
public class WorkoutService {
    private final WorkoutRepository workoutRepository;

    @Autowired
    public WorkoutService(WorkoutRepository workoutRepository) {
        this.workoutRepository = workoutRepository;
    }

    public List<Workout> getAllWorkouts() {
        return workoutRepository.getAllWorkouts();
    }

    public Workout getWorkoutById(String id) {
        return workoutRepository.getWorkoutById(id);
    }

    public Workout addWorkout(Workout workout) {
        return workoutRepository.addWorkout(workout);
    }

    public void deleteWorkoutById(String id) {
        workoutRepository.deleteWorkoutById(id);
    }

    public Workout updateWorkout(String id, Workout updatedWorkout) {
        Workout workout = workoutRepository.updateWorkout(id);
        if (workout != null) {
            workout.setName(updatedWorkout.getName());

            return workoutRepository.addWorkout(workout);
        }
        return null;
    }
}
