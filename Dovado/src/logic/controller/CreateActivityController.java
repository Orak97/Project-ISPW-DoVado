package logic.controller;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import logic.model.ContinuosActivity;
import logic.model.DateBean;
import logic.model.ExpiringActivity;
import logic.model.Factory;
import logic.model.Partner;
import logic.model.PeriodicActivity;
import logic.model.Place;
import logic.model.SuperActivity;
import logic.model.SuperUser;
import logic.model.User;

public class CreateActivityController {
	private SuperUser u;
	CreateActivityBean bean;
		
	CreateActivityController(SuperUser u, CreateActivityBean bean){
		this.u= u;
		this.bean = bean;
	}
	
	public void createActivity(String n, Place p) {
		//spaghetti code here!!!
		SuperActivity newActivity;
		switch(bean.getType()) {
		case continua:
			{	
				if(u instanceof User) {
					
					//A seconda di che tipo di utente � abbiamo un metodo di aggiunta attivit� al file JSON diverso;
					//se l'utente � normale l'attivit� non � certificata, mentre se lo � avremo un'attivit� certificata.
					//� importante nella ricostruzione delle attivit� ricavate dalla persistenza.
					
					newActivity=Factory.createNormalActivity(n, u, p, bean.getOpeningTime(), bean.getClosingTime());
					addActivityToJSON(p,newActivity,"no");
				}
				else {
					newActivity=Factory.createCertifiedActivity(n, u, p, bean.getOpeningTime(), bean.getClosingTime() );
					addActivityToJSON(p,newActivity,"yes");	
				}
			
		}
		break;
		case periodica:
			{
				if(u instanceof User) {
					//A seconda di che tipo di utente � abbiamo un metodo di aggiunta attivit� al file JSON diverso;
					//se l'utente � normale l'attivit� non � certificata, mentre se lo � avremo un'attivit� certificata.
					//� importante nella ricostruzione delle attivit� ricavate dalla persistenza.
					newActivity=Factory.createNormalActivity(n, u, p, bean.getOpeningTime(), bean.getClosingTime(), bean.getStartDate(), bean.getEndDate(), bean.getCadence());
					addActivityToJSON(p,newActivity,"no");
				}
				else {
					newActivity=Factory.createCertifiedActivity(n, u, p, bean.getOpeningTime(), bean.getClosingTime(), bean.getStartDate(), bean.getEndDate(), bean.getCadence());
					addActivityToJSON(p,newActivity,"yes");		
				}
			}
		break;
		case scadenza:
			{
				if(u instanceof User) {
					//A seconda di che tipo di utente � abbiamo un metodo di aggiunta attivit� al file JSON diverso;
					//se l'utente � normale l'attivit� non � certificata, mentre se lo � avremo un'attivit� certificata.
					//� importante nella ricostruzione delle attivit� ricavate dalla persistenza.
					newActivity=Factory.createNormalActivity(n, u, p, bean.getOpeningTime(), bean.getClosingTime(), bean.getStartDate(), bean.getEndDate());
					addActivityToJSON(p,newActivity,"no");
				}
				else { 
					newActivity=Factory.createCertifiedActivity(n, u, p, bean.getOpeningTime(), bean.getClosingTime(), bean.getStartDate(), bean.getEndDate());
					addActivityToJSON(p,newActivity,"yes");					
				}
			}
		break;
		}
		
	}
		
	private boolean addActivityToJSON(Place p,SuperActivity activity,String cert) {
		JSONParser parser = new JSONParser();
		FindActivityController fac = new FindActivityController();
		
		
		int i;
		try 
		{
			Object places = parser.parse(new FileReader("WebContent/places.json"));
			JSONObject place = (JSONObject) places;
			JSONArray placeArray = (JSONArray) place.get("places");
			
			Object activitiesParser = parser.parse(new FileReader("WebContent/activities.json"));
			JSONObject activitiesJOBJ = (JSONObject) activitiesParser;
			JSONArray activityArray = (JSONArray) activitiesJOBJ.get("activities");
			
			JSONObject result;
			JSONObject activityToAdd = new JSONObject(),activityIdToAdd = new JSONObject();

			
			activityToAdd.put("name", activity.getName());
			activityToAdd.put("creator", activity.getCreator().getUsername());
			if(activity.getFrequency() instanceof ContinuosActivity) {
				activityToAdd.put("opening", activity.getFrequency().getOpeningTime().toString());
				activityToAdd.put("closing", activity.getFrequency().getClosingTime().toString());
				activityToAdd.put("startdate", null);
				activityToAdd.put("enddate", null);
				activityToAdd.put("cadence", null);
				
			}
			if(activity.getFrequency() instanceof ExpiringActivity) {
				activityToAdd.put("opening", activity.getFrequency().getOpeningTime().toString());
				activityToAdd.put("closing", activity.getFrequency().getClosingTime().toString());
				activityToAdd.put("startdate", (((ExpiringActivity) (activity.getFrequency())).getStartDate().toString()));
				activityToAdd.put("enddate", (((ExpiringActivity) (activity.getFrequency())).getEndDate().toString()));
				activityToAdd.put("cadence", null);
				
			}
			if(activity.getFrequency() instanceof PeriodicActivity) {
				activityToAdd.put("opening", activity.getFrequency().getOpeningTime().toString());
				activityToAdd.put("closing", activity.getFrequency().getClosingTime().toString());
				activityToAdd.put("startdate", (((PeriodicActivity) (activity.getFrequency())).getStartDate().toString()));
				activityToAdd.put("enddate", (((PeriodicActivity) (activity.getFrequency())).getEndDate().toString()));
				activityToAdd.put("cadence", (((PeriodicActivity) (activity.getFrequency())).getCadence().toString()));
				
			}
			activityToAdd.put("certified", cert);

			if(activityArray!=null) {
				activityToAdd.put("id",activityArray.size());
				activityIdToAdd.put("id",activityArray.size());
			} else {
				activityToAdd.put("id",0);
				activityIdToAdd.put("id",0);
			}
			
			for(i=0;i<placeArray.size();i++) 
			{
				result = (JSONObject) placeArray.get(i);
				
				String namePrint = (String) result.get("name");
				String cityPrint = (String) result.get("city");
				String regionPrint = (String) result.get("region");
				
				if (activity.getPlace().getName().equals(namePrint) && activity.getPlace().getCity().equals(cityPrint) && activity.getPlace().getRegion().equals(regionPrint)) {
					
					JSONArray activitiesIdArray = (JSONArray) result.get("activities");
					
					if(fac.isInJSON(p, activity.getName(),activity.getCreator().getUsername()))	//Passando il posto in cui sto aggiungendo l'attivit�, 
						return false;	
					activitiesIdArray.add(activityIdToAdd); //Salvo l'id dell'attivit� al posto di appartenenza.
					result.put("activities", activitiesIdArray);
					
					FileWriter file = new FileWriter("WebContent/places.json");
					file.write(place.toString());
					file.flush();
					file.close();
					
					activityArray.add(activityToAdd);
					
					FileWriter file2 = new FileWriter("WebContent/activities.json");
					file2.write(activitiesJOBJ.toString());
					file2.flush();
					file2.close();
					
					return true;
				}
				
			}
			
		} 
		catch (FileNotFoundException e) {
			e.printStackTrace();
			return false;}
		catch (Exception e) {
			e.printStackTrace();
			return false;
			}
		return false;
	}
	
}
