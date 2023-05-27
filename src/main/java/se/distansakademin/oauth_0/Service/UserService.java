package se.distansakademin.oauth_0.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import se.distansakademin.oauth_0.Model.Workout;
import se.distansakademin.oauth_0.Repository.WorkoutRepository;

import java.util.List;

@Repository
public class UserService {
    private WorkoutRepository workoutRepository;

    @Autowired
    public void WorkoutService(WorkoutRepository workoutRepository) {
        this.workoutRepository = workoutRepository;
    }

    public UserService(WorkoutRepository workoutRepository) {
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
