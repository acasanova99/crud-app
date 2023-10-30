package com.skz.back.api;

import com.skz.back.dto.PersonDto;
import com.skz.back.dto.ResponseDto;
import com.skz.back.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.skz.back.dto.ResponseDto.successfulResponseFrom;

@RestController
@RequestMapping(value = "/person", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
public class PersonAPI {
    @Autowired
    PersonService personService;

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public ResponseDto<List<PersonDto>> getAll() {
        return successfulResponseFrom(personService.getAll());
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseDto<PersonDto> savePerson(@RequestBody final PersonDto personDto) {
        return successfulResponseFrom(personService.save(personDto));
    }

    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    public ResponseDto<PersonDto> getPerson(@PathVariable final Long id) {
        return successfulResponseFrom(personService.get(id));
    }

    @RequestMapping(value = "byemail/{email}", method = RequestMethod.GET)
    public ResponseDto<PersonDto> getPersonByEmail(@PathVariable final String email) {
        return successfulResponseFrom(personService.getByEmail(email));
    }

    @RequestMapping(value = "/email/{email}", method = RequestMethod.PUT)
    public ResponseDto<PersonDto> updatePerson(@PathVariable final String email, @RequestBody final PersonDto personDto) {
        return successfulResponseFrom(personService.update(email, personDto));
    }

    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    public ResponseDto<Void> deletePerson(@PathVariable final Long id) {
        personService.delete(id);
        return successfulResponseFrom(null);
    }

    @RequestMapping(value = "byemail/{email}", method = RequestMethod.DELETE)
    public ResponseDto<Void> deletePerson(@PathVariable final String email) {
        personService.deleteByEmail(email);
        return successfulResponseFrom(null);
    }
}
