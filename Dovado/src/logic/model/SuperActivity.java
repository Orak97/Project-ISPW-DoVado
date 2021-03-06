package logic.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collection;

import logic.controller.CreateActivityBean;

public abstract class SuperActivity {
	private Long id;
	private String name;
	private SuperUser creator;
	private Place place;
	private FrequencyOfRepeat frequencyOfRepeat;
	private Channel channel;
	private ArrayList<String> preferences;
	
	public SuperActivity(String nome, SuperUser user, Place place) {
		//chiamare questo metodo quando si vuole creare una attività continua!
		this.name= nome;
		this.creator = user;
		this.place = place;
		this.preferences = new ArrayList<String>();
		this.frequencyOfRepeat = new ContinuosActivity(null,null);
		this.channel = new Channel();
	}
	
	public SuperActivity(String nome, SuperUser c, Place p, LocalTime openingTime, LocalTime closingTime) {
		//chiamare questo metodo quando si vuole creare una attività continua con orario apertura e chiusura
		this(nome,c,p);
		this.frequencyOfRepeat = new ContinuosActivity(openingTime,closingTime);
	}
	
	public SuperActivity(String nome, SuperUser c, Place p, LocalTime openingTime, LocalTime closingTime, LocalDate startDate, LocalDate endDate) {
		//chiamare questo metodo quando si vuole creare una attività a scadenza!
		this(nome,c,p);
		this.frequencyOfRepeat = new ExpiringActivity(openingTime,closingTime,startDate,endDate);
	}
	
	public SuperActivity(String nome, SuperUser c, Place p, LocalTime openingTime, LocalTime closingTime, LocalDate startDate, LocalDate endDate, Cadence cadence) {
		//chiamare questo metodo quando si vuole creare una attività periodica !
		this(nome,c,p);
		this.frequencyOfRepeat = new PeriodicActivity(openingTime,closingTime,startDate,endDate,cadence);
	}
	
	
	//Non so se vanno bene getters e setters qui, dato che abbiamo tutte le attivit? principalmente salvate nel JSON potremmo usare getters e setters di un bean apposito.

	public String getName() {
		return this.name;
	}
	public Place getPlace() {
		return this.place;
	}
	public Long getId() {
		return this.id;
	}
	public void setId(Long id) {
		this.id=id;
	}
	public FrequencyOfRepeat getFrequency() {
		return this.frequencyOfRepeat;
	}
	public void setFrequency(FrequencyOfRepeat frequencyOfRepeat) {
		this.frequencyOfRepeat=frequencyOfRepeat;
	}
	public void setName(String name) {
		this.name = name;
	}
	public SuperUser getCreator() {
		return this.creator;
	}
	public void setCreator(SuperUser creator) {
		this.creator = creator;
	}
	
	public Channel getChannel() {
		return this.channel;
	}
	
	public boolean playableOnThisDate(LocalDateTime timestamp) {
		/*
		 * Metodo usato per capire se questa attività è fattibile in un dato giorno
		 * 
		 * 
		 */
		if (frequencyOfRepeat.checkPlayability(timestamp)) return true;
		else return false;
	}

	public ArrayList<String> getPreferences() {
		return this.preferences;
	}
	
	public void setPreferences(ArrayList<String> newPreferences) {
		this.preferences = newPreferences;
	}
}
