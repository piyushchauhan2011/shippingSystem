package org.piyush.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import org.piyush.models.Package;

public class PackageDaoMemImpl implements PackageDao {

	private List<Package> packages;
	private final AtomicLong counter = new AtomicLong();
	
	public PackageDaoMemImpl(){
		packages = new ArrayList<Package>();
		
		Package p = new Package();
		p.setOrderId(1);
		p.setStatus("in-transit");
		p.setTrackingNumber("aklsdjf897asdf7asdfasdf");
		p.setDeliveryAddress("Unit 3, 31-32 Crown Street, Homebush 2140");
		
		addPackage(p);
		
		Package p2 = new Package();
		p2.setOrderId(2);
		p2.setStatus("shipped");
		p2.setTrackingNumber("aasdf234asdfwerwer134gf");
		p2.setDeliveryAddress("Unit 11, Crown Street, Merrylands, 4010");
		
		addPackage(p2);
	}
	
	@Override
	public void addPackage(Package p) {
		p.setId(counter.incrementAndGet());
		packages.add(p);
	}

	@Override
	public void deletePackage(long packageId) {
		for (int i = 0; i < packages.size(); i ++){
			if (packages.get(i).getId() == packageId){
				packages.remove(i);
				break;
			}
		}
	}

	@Override
	public List<Package> getAllPackages() {
		return packages;
	}

	@Override
	public Package getPackageById(long packageId) {
		for (Package p: packages){
			if (p.getId() == packageId)
				return p;
		}
		return null;
	}
}
