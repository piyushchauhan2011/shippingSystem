package org.piyush.controllers;

import java.util.List;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.piyush.dao.DaoFactory;

import org.springframework.web.bind.annotation.RequestMethod;
import org.piyush.models.Package;
import org.piyush.dao.PackageDao;

@RestController
@RequestMapping("/packages")
public class PackageController {
	
	private PackageDao pdao = DaoFactory.getInstance().getPackageDao();
	
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
    public Package create() {
    	Package p = new Package();
    	pdao.addPackage(p);
    	return p;
    }
        
    @RequestMapping(value="/{id}", method=RequestMethod.DELETE)
    public Package delete(@PathVariable("id") long id) {
    	Package p = pdao.getPackageById(id);
    	pdao.deletePackage(id);
    	return p;
    }
}