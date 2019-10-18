package com.getjoke.application;

import java.util.List;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.client.RestTemplate;

import com.getjoke.models.Joke;
import com.getjoke.models.JokeFilterForm;

@Controller
public class JokesController {
	
	private final String Url = "https://official-joke-api.appspot.com/jokes";
	private RestTemplate restTemplate = new RestTemplate();

	@GetMapping("/jokes")
	public String jokeFilterForm(Model model) {
		model.addAttribute("jokeFilterForm", new JokeFilterForm());
		return "form";
	}
	
	@PostMapping("/jokes")
	public String jokeFilterFormSubmit(@ModelAttribute JokeFilterForm form, Model model) {

		if (form.getType().equals("no-pref")) {
			if (form.getOneOrTen().equals("one")) {
				Joke joke = restTemplate.getForObject(Url + "/random", Joke.class);
				model.addAttribute("joke", joke);
				return "joke-result";
			}
			else {
				ResponseEntity<List<Joke>> jokesResponse = restTemplate.exchange(
						Url + "/ten",
						HttpMethod.GET,
						null,
						new ParameterizedTypeReference<List<Joke>>() {});
				List<Joke> jokes = jokesResponse.getBody();
				model.addAttribute("jokes", jokes);
				return "jokes-result";
			}
		}
		else { // filtering by type is weird; a list of Joke is always returned
			ResponseEntity<List<Joke>> jokesResponse = restTemplate.exchange(
					String.format(Url + "/%s/" + (form.getOneOrTen().equals("one") ? "random" : "ten"), form.getType()), 
					HttpMethod.GET, 
					null, 
					new ParameterizedTypeReference<List<Joke>>() {});
			List<Joke> jokes = jokesResponse.getBody();
			model.addAttribute("jokes", jokes);
			return "jokes-result";
		}

	}
}
