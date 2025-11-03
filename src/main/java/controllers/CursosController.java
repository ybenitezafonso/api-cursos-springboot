package controllers;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.cursos.model.Curso;

/**
 * Controlador REST que gestiona operaciones sobre cursos.
 * 
 * Permite:
 * <ul>
 * <li>Listar todos los cursos</li>
 * <li>Buscar cursos por nombre</li>
 * <li>Añadir nuevos cursos</li>
 * <li>Eliminar cursos existentes</li>
 * </ul>
 *
 * Este controlador utiliza una lista en memoria para simular una base de datos.
 * 
 * @author Yaiza Benítez Afonso
 * @version 1.0
 */

@RestController
public class CursosController {

	/** Lista que almacena los cursos disponibles */
	private List<Curso> cursos;

	/**
	 * Inicializa la lista de cursos con valores de ejemplo. Se ejecuta
	 * automáticamente al crear el controlador.
	 */
	@PostConstruct
	public void init() {
		cursos = new ArrayList<>();
		cursos.add(new Curso("Spring", 25, "tarde"));
		cursos.add(new Curso("Spring boot", 20, "tarde"));
		cursos.add(new Curso("Python", 30, "tarde"));
		cursos.add(new Curso("Java EE", 50, "fin de semana"));
		cursos.add(new Curso("Java básico", 30, "mañana"));
	}

	/**
	 * Devuelve la lista completa de cursos en formato XML.
	 * 
	 * @return Lista de todos los cursos disponibles.
	 */
	@GetMapping(value = "cursos", produces = MediaType.APPLICATION_XML_VALUE)
	public List<Curso> getCursos() {
		return cursos;
	}

	/**
	 * Devuelve un curso de ejemplo en formato XML.
	 * 
	 * @return Un objeto de prueba.
	 */
	@GetMapping(value = "curso", produces = MediaType.APPLICATION_XML_VALUE)
	public Curso getCurso() {
		return new Curso("Java", 100, "Mañana");
	}

	/**
	 * Busca cursos cuyo nombre contenga la cadena indicada.
	 * 
	 * @param nombre Parte del nombre a buscar (sensible a mayúsculas/minúsculas).
	 * @return Lista de cursos que coinciden con el criterio, en formato JSON.
	 */
	@GetMapping(value = "cursos/{name}", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Curso> buscarCursos(@PathVariable("name") String nombre) {
		List<Curso> aux = new ArrayList<>();
		for (Curso c : cursos) {
			if (c.getNombre().contains(nombre)) {
				aux.add(c);
			}
		}
		return aux;
	}

	/**
	 * Elimina un curso según su nombre.
	 * 
	 * @param nombre Nombre exacto del curso a eliminar.
	 */
	@DeleteMapping(value = "curso/{name}")
	public void eliminarCurso(@PathVariable("name") String nombre) {
		cursos.removeIf(c -> c.getNombre().equals(nombre));
	}

	/**
	 * Añade un nuevo curso a la lista.
	 * 
	 * @param curso Objeto con los datos del nuevo curso.
	 */
	@PostMapping(value = "curso", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Curso> altaCurso(@RequestBody Curso curso) {
		cursos.add(curso);
		return cursos;
	}

	/**
	 * Modifica un curso existente de la lista.
	 * 
	 * @param curso Objeto con los datos del curso a modificar
	 * @return Lista de todos los cursos con la última modificación.
	 */
	@PutMapping(value = "curso", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Curso> actualizaCurso(@RequestBody Curso curso) {
		for (int i = 0; i < cursos.size(); i++) {
			if (cursos.get(i).getNombre().equals(curso.getNombre())) {
				cursos.set(i, curso);
			}
		}
		return cursos;
	}
}
