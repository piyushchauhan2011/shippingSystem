package org.piyush.dao;

import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class DaoFactory {

	private static DaoFactory df;
	private PackageDao pDao = null;
	static Log log = LogFactory.getLog(DaoFactory.class);
	String classname = this.getClass().getName();
	private DaoFactory(){
	}
	
	public static DaoFactory getInstance(){
		if (df == null)
			df = new DaoFactory();
		return df;
	}
	
	public PackageDao getPackageDao(){
		if (pDao == null){
			Properties properties = new Properties() ;
			try{
				properties.load(this.getClass().getResourceAsStream("/application.properties"));		
				String className = properties.getProperty("dao.PackageDaoName");
				if (className!=null){
					pDao = (PackageDao)Class.forName(className).newInstance();
					log.info("Using " + className + " to get ProductInfo...");
				}else{
					log.info("property not found, using default implementation");
					System.out.println("property not found, using default implementation");
					pDao = new PackageDaoMemImpl();
				}
			}catch (Exception e){ 
				log.info(e.getMessage());
				e.printStackTrace();
				pDao =  new PackageDaoMemImpl();
				System.out.println("Exception, using default implementation");
				return pDao;
			}
		}
		return pDao;
	}
}

