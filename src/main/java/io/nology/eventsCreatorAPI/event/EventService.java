package io.nology.eventsCreatorAPI.event;

import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;


@Service
@Transactional
public class EventService {

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private ModelMapper modelMapper;

    public List<Event> getAll() {
        return this.eventRepository.findAll();
    }

    public Event createEvent(EventCreateDTO data) {
        Event newEvent = modelMapper.map(data, Event.class);
        Event created = this.eventRepository.save(newEvent);

        return created;
    }

    public Optional<Event> getById(Long id) {
        Optional<Event> foundEvent= eventRepository.findById(id);
        return foundEvent;
    }

    public boolean deleteById(Long id) {
        Optional<Event> foundEvent = this.eventRepository.findById(id);

        if(foundEvent.isPresent()) {
            this.eventRepository.delete(foundEvent.get());
            return true;
        }

        return false;
    }

    public Optional<Event> updateById(Long id, EventUpdateDTO data) {
        Optional<Event> foundEvent = this.getById(id);

        if (! foundEvent.isPresent()) {
            return foundEvent;
        }

        Event toUpdate = foundEvent.get();

        modelMapper.map(data, toUpdate);

        Event updatedEvent = this.eventRepository.save(toUpdate);

        return Optional.of(updatedEvent);
    }

    public List<Event> getAllByLabel(String label) {
        return this.eventRepository.findByLabel(label);
    }

    public List<Event> getAllByLocation(String location) {
        return this.eventRepository.findByLocation(location);
    }
}
