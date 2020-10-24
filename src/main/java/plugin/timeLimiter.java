package plugin;
import java.util.Calendar;
import java.util.prefs.*;

import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
//only for setting up server stugg
public final class timeLimiter  extends JavaPlugin{
	PlayerJoinEvent player;
	PlayerQuitEvent playerquit;
	String playerJoinedName;
	String playerQuitName;
	int serverStartupTime;
	int playerJoinedTime;
	playerActivity timer = new playerActivity();
	  

	  @Override
	    public void onEnable() {
		  timer.createCustomConfig();
		  timer.getData();

		  Calendar calendar = Calendar.getInstance();
		//serverStartupTime= preference.getPreviousDay
		  if(calendar.get(Calendar.DATE)!= timer.serverStartupTime) {
			  timer.clearAllData();
			  timer.ClearAllSavedData();
		  }
		
          
           if(player.getPlayer().getDisplayName() != null) {
        	   timer.setPause(false);
	             
	            	 
	             
	         	playerJoinedTime= ((calendar.get(Calendar.YEAR)*525600)+((calendar.get(Calendar.MONTH)+1)*43800)+((calendar.get(Calendar.DATE)*1440))+((calendar.get(Calendar.HOUR_OF_DAY)+8)*60)+(calendar.get(Calendar.MINUTE)));
			  playerJoinedName =player.getPlayer().getDisplayName();
			  
			  
			  
			  timer.checkDisconnectedUsers(playerJoinedName);
			  timer.TimerStart(playerJoinedName,playerJoinedTime);
			  if(playerQuitName==playerJoinedName) {
				  
				  timer.insertDisconnectedData(playerJoinedName);
			  }
			
			  if(timer.Timecomparing()>=30) {
				  String temp = timer.checkTimeExceed();
				  timer.banName(temp, "Spm is"+ " "+timer.spmCounter()+" "+"left. Not trying to stress you out, but just to give a heads up! “The art of reading and studying consists in remembering the essentials and forgetting what is not essential.”\n" + 
				  		"― Adolf Hitler", null);
				  
				
				 
				  //ban user
			  }
		  }
		
		playerQuitName= playerquit.getPlayer().getDisplayName();
		
		  getLogger().info("PluginEnabled!");
	        // TODO Insert logic to be performed when the plugin is enabled
	    }
	    
	    @Override
	    public void onDisable() {
	    	timer.saveData();
	    	getLogger().info("PluginDisabled");
	        // TODO Insert logic to be performed when the plugin is disabled
	    }
	}

