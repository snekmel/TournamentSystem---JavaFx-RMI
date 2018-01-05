package Server.repositorys;

import Server.Server;

import java.io.FileInputStream;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RegistryRepository {



    public static Registry getRmiRegistry(){
        Registry returnRegistry = null;
        try{
            Properties properties = new Properties();
            FileInputStream propFile = new FileInputStream("src/main/java/Shared/config/rmi.prop");
            properties.load(propFile);

            returnRegistry = LocateRegistry.getRegistry(properties.getProperty("hostIp"),Integer.parseInt(properties.getProperty("rmiPort")));

        }catch (Exception e){
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, e);
        }


        return returnRegistry;

    }

}
