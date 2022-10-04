package com.backend.sga.rest;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.annotation.RequestScope;

import com.backend.sga.model.Ambiente;
import com.backend.sga.model.Aula;
import com.backend.sga.model.Erro;
import com.backend.sga.model.Periodo;
import com.backend.sga.model.Professor;
import com.backend.sga.model.RecebeAula;
import com.backend.sga.model.Sucesso;
import com.backend.sga.model.UnidadeCurricular;
import com.backend.sga.repository.AulaRepository;
import com.backend.sga.repository.ProfessorRepository;

//CrossOrigin serve para que o projeto receba JSON
@CrossOrigin
@RestController
@RequestMapping("/api/aula")
public class AulaRestController {
	
	@Autowired
	private AulaRepository aulaRepository;
	
	@Autowired
	private ProfessorRepository professorRepository;
	
	
	/*// método para cadastrar uma aula 
	@RequestMapping(value = "", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> criarAula(@RequestBody Aula aula, HttpServletRequest request){
		
		if(aula != null) { // verifica se a aula não está nula
		aulaRepository.save(aula); // salva a aula no banco de dados
			Sucesso sucesso = new Sucesso(HttpStatus.OK, "Sucesso"); // moldando a mensagem de sucesso
			return new ResponseEntity<Object>(sucesso, HttpStatus.OK); // retornando a mensagem de sucesso
		}else {
			Erro erro = new Erro(HttpStatus.INTERNAL_SERVER_ERROR, "não foi possível cadastrar uma aula", null); // moldando a mensagem de erro
			return new ResponseEntity<Object>(erro, HttpStatus.INTERNAL_SERVER_ERROR); // retornando a mensagem de erro
		}
		
	}*/
	
	//AJEITAR O METODO DE SALVAR AS AULAS
	// método para cadastrar uma aula 
	@RequestMapping(value = "", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> criarAula(@RequestBody RecebeAula recebeAula , HttpServletRequest request){
		
		//criando a aula(trazendo ela)
		Aula aula = new Aula();
		
		//setando os valores que precisam no cadastro de aula
		aula.setCodTurma(recebeAula.getCodTurma());
		aula.setAmbiente(recebeAula.getAmbiente());
		aula.setCargaDiaria(recebeAula.getCargaDiaria());
		aula.setPeriodo(recebeAula.getPeriodo());
		aula.setProfessor(recebeAula.getProfessor());
		aula.setUnidadeCurricular(recebeAula.getUnidadeCurricular());
		
		boolean dia[] = recebeAula.getDiaSemana();
		
		ArrayList<Integer> guardaDia = null;
		
		
		
		/*if (dia[0] == true) {
			guardaDia.add(Calendar.SUNDAY);//dom
		}
		if(dia[1] == true) {
			guardaDia.add(Calendar.MONDAY);//seg
		}
		if(dia[2] == true) {
			guardaDia.add(Calendar.TUESDAY);//ter
		}
		if(dia[3] == true) {
			guardaDia.add(Calendar.WEDNESDAY);//qua
		}
		if(dia[4] == true) {
			guardaDia.add(Calendar.THURSDAY);//qui
		}
		if(dia[5] == true) {
			guardaDia.add(Calendar.FRIDAY);//sex
		}
		if(dia[6] == true){
			guardaDia.add(Calendar.SATURDAY);//sab
		}*/
		
		//começando a logica da dias
		//for (int i = 0; i < dia.length; i++) {
		//}
		
		//retornando uma listagem de aula
		List<Aula> listaAula = aulaRepository.diaSemanal(recebeAula.getDataInicio());
		
		//quando a hora chegar a igual a '0' ela para
		/*while (aula.getUnidadeCurricular().getHoras() > 0) {
			
			for (int i = 0; i < listaAula.size(); i++) {
				
				listaAula.add(recebeAula.getDataInicio());
				
			}
			
		}*/
		
		
		return null;
		
	}
	
	// método que deleta a aula pelo ID
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Object> deletarAula(@RequestBody Aula aula, @PathVariable("id") Long id, HttpServletRequest request){
		if(aula.getId() == id) { // verifica se o id da aula é igual o id selecionado
			aulaRepository.deleteById(id); // deleta a aula do banco de dados
			Sucesso sucesso = new Sucesso(HttpStatus.OK, "Sucesso"); // moldando a mensagem de sucesso
			return new ResponseEntity<Object>(sucesso, HttpStatus.OK); // retornando a mensagem de sucesso
		}else {
			Erro erro = new Erro(HttpStatus.INTERNAL_SERVER_ERROR, "Não foi possível deletar a aula", null); // moldando a mensagem de erro
			return new ResponseEntity<Object>(erro, HttpStatus.INTERNAL_SERVER_ERROR); // retornando a mensagem de erro
		}
	}
	
	// método que lista todas as aulas
	@RequestMapping(value = "", method = RequestMethod.GET)
	public Iterable<Aula> listarAulas(){
		return aulaRepository.findAll(); // retorna a lista de todos os comonentes do banco de dados
	}
	
	//metodo para alterar
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> atualizarAula(@PathVariable("id") Long id, @RequestBody Aula aula, HttpServletRequest request){
		if (aula.getId() != id) {
			Erro erro = new Erro(HttpStatus.INTERNAL_SERVER_ERROR, "ID inválido", null);
			return new ResponseEntity<Object>(erro, HttpStatus.INTERNAL_SERVER_ERROR);
		}else {
			aulaRepository.save(aula);
			Sucesso sucesso = new Sucesso(HttpStatus.OK, "Sucesso");
			return new ResponseEntity<Object>(sucesso, HttpStatus.OK);
		}
	}
	
	
	
}

