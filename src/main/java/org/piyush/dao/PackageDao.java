package org.piyush.dao;

import java.util.List;
import org.piyush.models.Package;

public interface PackageDao {
	public List<Package> getAllPackages();
	public void addPackage(Package p);
	public Package getPackageById(long packageId);
	public void deletePackage(long packageId);	
}