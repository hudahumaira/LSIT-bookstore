package Bookstore.Repositories;

import Bookstore.Models.Pet;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

@Repository
public class PetRepository {
    static HashMap<UUID, Pet> pets = new HashMap<>();

    public void add(Pet p){
        p.id = UUID.randomUUID();
        pets.put(p.id, p);
    }

    public Pet get(UUID id){
        return pets.get(id);
    }

    public void remove(UUID id){
        pets.remove(id);
    }

    public void update(Pet p){
        Pet x = pets.get(p.id);
        x.name = p.name;
        x.kind = p.kind;
    }

    public List<Pet> list(){
        return new ArrayList<>(pets.values());
    }
}
