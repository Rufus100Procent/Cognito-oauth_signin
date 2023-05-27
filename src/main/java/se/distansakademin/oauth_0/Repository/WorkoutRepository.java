package se.distansakademin.oauth_0.Repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;
import se.distansakademin.oauth_0.Model.Workout;

import java.util.List;

@Repository
public class WorkoutRepository {
    private final MongoTemplate mongoTemplate;

    @Autowired
    public WorkoutRepository(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    public List<Workout> getAllWorkouts() {
        return mongoTemplate.findAll(Workout.class);
    }

    public Workout getWorkoutById(String id) {
        return mongoTemplate.findById(id, Workout.class);
    }

    public Workout addWorkout(Workout workout) {
        return mongoTemplate.insert(workout);
    }

    public void deleteWorkoutById(String id) {
        Query query = new Query(Criteria.where("id").is(id));
        mongoTemplate.remove(query, Workout.class);
    }

    public Workout updateWorkout(String workout) {
        Workout workout1 = new Workout();
        return mongoTemplate.save(workout1);
    }
}
