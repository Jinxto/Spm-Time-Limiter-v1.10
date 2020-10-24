package plugin;
//my code goes here

import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.Set;
import java.util.prefs.Preferences;

import org.bukkit.BanList;
import org.bukkit.OfflinePlayer;
import org.bukkit.Server;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class playerActivity  extends JavaPlugin{
	Preferences prefs;
	protected String PlayerName;
	protected int TimeInitialized;
	
	 protected String thisPlayer;
	protected int TimeLimit;
	protected int getCurrentTime;
	protected int PlayerJoinedTime;
	protected int comparing; 
	protected int totalTime;
	protected boolean pause;
	protected int count;
	protected int discount;
	protected String database[];
	protected int TimeTotal[];
	protected String DisconnectedUser[];
	protected int DisconnectedTime[];
	protected int dateTime[];
    protected File customConfigFile;
    protected FileConfiguration customConfig;
    Calendar calendar = Calendar.getInstance();
    int	serverStartupTime = calendar.get(Calendar.DATE);

	Server s;

	public void TimerStart(String playaname, int time) {
	               this.getCurrentTime = time;
	               this.thisPlayer= playaname;
	               if(!pause) {
	               database[count] = thisPlayer;
	               dateTime[count]= getCurrentTime;
	               count++;
	              
	               totalTime=TimeTotal[count-1];
		               
	               } 
	}
	  
	  public int gettotalTime() {
		  return totalTime;
	  }
	  
	public void insertDisconnectedData(String x) {
		int tempdatabase[]= null;
	 for(int i = 0; i<count; i++) {
		   if(x== database[i]) {
			   DisconnectedUser[discount]=database[i];
			   DisconnectedTime[discount]=TimeTotal[i];
			   for(int k= i; k<count-1; k++) {
				   database[k]= database[k+1];
				   TimeTotal[k]=TimeTotal[k+1];
				   dateTime[k]= dateTime[k+1];
			   }
			   count--;
			   discount++;
					
					
				}
		   
				
			}
			/* DisconnectedUser[discount]= database[count-1];
			 DisconnectedTime[discount]= TimeTotal[count-1];
		
			 database[count-1]= null;
			 TimeTotal[count-1]=0;
			 count--;
			 discount++;
			 */
		}
		
	
	public void checkDisconnectedUsers(String x) { //check if theres disconnected user rejoin back if theres , time got from that disarray back to the variable
		//to continue the count
		for(int j = 0; j<discount; j++ ) {
			if(x== DisconnectedUser[j]) {
				 

				pause = true;
				database[count] = DisconnectedUser[j];
				TimeTotal[count]=DisconnectedTime[j];
				dateTime[count]=((calendar.get(Calendar.YEAR)*525600)+((calendar.get(Calendar.MONTH)+1)*43800)+((calendar.get(Calendar.DATE)*1440))+((calendar.get(Calendar.HOUR_OF_DAY)+8)*60)+(calendar.get(Calendar.MINUTE)));
				count++;
				for(int i = j; i<discount-1; i++) {
					DisconnectedUser[j]= DisconnectedUser[j+1];
					DisconnectedTime[j]=DisconnectedTime[j+1];
					
				}
				discount--;
				//count no need ++ cuz u have it on the next code which basically repeats everything
				//initialize at front part so time total value gets inputted first hand
			}
			
		}
	} public String checkTimeExceed() {
		for(int l=0; l<count; l++) {
			if(TimeTotal[l]>30) {
				String Temp = database[l];
				return Temp;
			}
		}
		return null;
	}

public void banName(String name, String reason, String source) {
	Date dt = new Date();
	Calendar c = Calendar.getInstance(); 
	c.setTime(dt);
	c.add(Calendar.DATE, 1); 
	dt = c.getTime();

	
  s.getBanList(BanList.Type.NAME).addBan(name, reason, dt, source);
}
public String spmCounter(){
    Calendar calendar = Calendar.getInstance();
double j = (737665+ 51) -((calendar.get(Calendar.YEAR)*365)+((calendar.get(Calendar.MONTH)+1)*30.4167)+(calendar.get(Calendar.DATE)));

int k= (int)Math.round(j);
String lol = Integer.toString(k);

return lol;
}
public void clearAllData() {
	count = 0;
	discount = 0;
	database= null;
	TimeTotal= null;
	DisconnectedUser= null;
	DisconnectedTime= null;
}

public void  saveData() { 

	customConfig.set("serverStartupTime", serverStartupTime);
	customConfig.set("count", count);
	customConfig.set("discount", discount);
	for(int i=0 ; i<count; i++) {
	customConfig.set("database",database[i]);
	customConfig.set("timeTotal", TimeTotal[i]);
	}
	for(int j=0; j<discount; j++) {
		customConfig.set("disconnectedusers", DisconnectedUser[j]);
		customConfig.set("disconnectedTime",DisconnectedTime[j]);
	}
	
   try {
	customConfig.save(customConfigFile);
} catch (IOException e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
}
	}
public void createCustomConfig() {
    customConfigFile = new File(getDataFolder(), "custom.yml");
    if (!customConfigFile.exists()) {
        customConfigFile.getParentFile().mkdirs();
        saveResource("custom.yml", false);
     }
    customConfig= new YamlConfiguration();
    try {
        customConfig.load(customConfigFile);
    } catch (IOException | InvalidConfigurationException e) {
        e.printStackTrace();
    }
	}
public void getData() {
	serverStartupTime = customConfig.getInt("serverStartupTime");
	count = customConfig.getInt("count");
	discount= customConfig.getInt("discount");//get all Data from the arrays
  for(int i=0; i<count; i++) {
	  database[i]=  customConfig.getString("database");
	  TimeTotal[i] = customConfig.getInt("timeTotal");
  }
  for(int k=0; k<discount; k++) {
	  DisconnectedUser[k]= customConfig.getString("disconnectedusers");
	  DisconnectedTime[k]= customConfig.getInt("disconnectedTime");
  }
}
public void ClearAllSavedData() {
	customConfig.set("count",null);
	customConfig.set("discount", null);
	customConfig.set("database", null);
	customConfig.set("timeTotal", null);
	customConfig.set("disconnectedusers", null);
	customConfig.set("disconnectedTime", null);
}public int Timecomparing(){
	Calendar calendar = Calendar.getInstance();
	 for(int i=0; i<count; i++) {
  	   TimeTotal[i]+=((calendar.get(Calendar.YEAR)*525600)+((calendar.get(Calendar.MONTH)+1)*43800)+((calendar.get(Calendar.DATE)*1440))+((calendar.get(Calendar.HOUR_OF_DAY)+8)*60)+(calendar.get(Calendar.MINUTE)))-dateTime[i];
  	     return TimeTotal[i];
     }
	 return 29;
}
public boolean setPause(boolean x) {
	this.pause= x;
	return pause;
}
}
	 


