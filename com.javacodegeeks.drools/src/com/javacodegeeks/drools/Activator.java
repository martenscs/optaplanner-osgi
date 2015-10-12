package com.javacodegeeks.drools;

import org.kie.api.KieBase;
import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

import com.javacodegeeks.drools.model.Customer;
import com.javacodegeeks.drools.model.Product;

public class Activator implements BundleActivator {

	private static BundleContext context;

	static BundleContext getContext() {
		return context;
	}

	private KieSession ksession;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.osgi.framework.BundleActivator#start(org.osgi.framework.BundleContext
	 * )
	 */
	public void start(BundleContext bundleContext) throws Exception {
		Activator.context = bundleContext;
		KieServices ks = KieServices.Factory.get();
		KieContainer kcont = ks.newKieClasspathContainer(getClass()
				.getClassLoader());
		KieBase kbase = kcont.getKieBase("sampleDecisionKBase");

		this.ksession = kbase.newKieSession();
		System.out.println("KieSession created.");

		Customer customer = new Customer();
		Product p1 = new Product("Laptop", 15000);
		Product p2 = new Product("Mobile", 5000);
		Product p3 = new Product("Books", 2000);
		customer.addItem(p1, 1);
		customer.addItem(p2, 2);
		customer.addItem(p3, 5);
		customer.setCoupon("DISC01");

		ksession.insert(customer);
		// Fire the rules
		ksession.fireAllRules();
		System.out.println("First Customer\n" + customer);

		Customer newCustomer = Customer.newCustomer();
		newCustomer.addItem(p1, 1);
		newCustomer.addItem(p2, 2);


		ksession.insert(newCustomer);
		// Fire the rules
		ksession.fireAllRules();
		System.out.println("*********************************");
		System.out.println("Second Customer\n" + newCustomer);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.osgi.framework.BundleActivator#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext bundleContext) throws Exception {
		Activator.context = null;
	}

}
