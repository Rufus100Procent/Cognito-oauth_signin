package se.distansakademin.oauth_0.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import se.distansakademin.oauth_0.Model.Workout;
import se.distansakademin.oauth_0.Repository.WorkoutRepository;

import java.util.List;

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
}
