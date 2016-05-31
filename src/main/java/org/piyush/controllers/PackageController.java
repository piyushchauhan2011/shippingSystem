package org.piyush.controllers;

import java.util.List;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.web.bind.annotation.RequestMethod;
import org.piyush.models.Package;
import org.piyush.models.PackageItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.piyush.dao.PackageRepository;
import org.piyush.dao.PackageItemRepository;

@RestController
@CrossOrigin(origins = "http://localhost:8080")
@RequestMapping("/packages")
public class PackageController {
	
	protected final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private PackageRepository pdao;
    
    @Autowired
    private PackageItemRepository pidao;
	
	@Context
	UriInfo uriInfo; // like an instance variable definition

    @RequestMapping(method=RequestMethod.GET)
    public List<Package> index() {
        return pdao.getAllPackages();
    }
    
    @RequestMapping(value="/{id}", method=RequestMethod.GET)
    public Package read(@PathVariable("id") long id) {
    	return pdao.getPackageById(id);
    }
    
    @RequestMapping(method=RequestMethod.POST)
    public Package create(@RequestBody Package p) {
    	p = pdao.insertPackage(p);
    	return p;
    }
    
    @RequestMapping(value="/{id}", method=RequestMethod.PUT)
    public Package update(@PathVariable("id") long id, @RequestBody Package p) {
    	p = pdao.updatePackage(id, p);
    	return p;
    }
    
    @RequestMapping(value="/{id}/item", method=RequestMethod.POST)
    public Package addItem(@PathVariable("id") long id, @RequestBody PackageItem pi) {
    	Package p = pdao.getPackageById(id);
    	return pidao.insertPackageItem(pi, p);
    }
    
    @RequestMapping(value="/{id}/item/{itemId}", method=RequestMethod.DELETE)
    public Package index(
    		@PathVariable("id") long id,
    		@PathVariable("itemId") long itemId) {
    	Package p = pdao.getPackageById(id);
        return pidao.deletePackageItem(p, itemId);
    }
        
    @RequestMapping(value="/{id}", method=RequestMethod.DELETE)
    public Package delete(@PathVariable("id") long id) {
    	Package p = pdao.getPackageById(id);
    	pdao.deletePackage(id);
    	return p;
    }
    
    @RequestMapping(value="/track/{trackingNumber}", method=RequestMethod.GET)
    public Package track(@PathVariable("trackingNumber") String trackingNumber) {
    	System.out.println(trackingNumber);
    	Package p = pdao.getPackageByTrackingNumber(trackingNumber);
    	return p;
    }
}