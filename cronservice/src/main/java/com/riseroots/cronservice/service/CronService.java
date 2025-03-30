package com.riseroots.cronservice.service;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.riseroots.cronservice.model.Customer;
import com.riseroots.cronservice.repository.CustomerRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class CronService {

	private static final Logger logger = LoggerFactory.getLogger(CronService.class);

	private final CustomerRepository customerRepository;

	@Value("${cronservice.report.misFile}")
	private String misFileName;

	@Value("${cronservice.report.duplicateFile}")
	private String duplicateFileName;

	@Value("${cronservice.report.backupPrefix}")
	private String backupFilePrefix;

	@Value("${cronservice.report.csv}")
	private String csv;

	@Value("${cron.threadpool.size}")
	private int threadPoolSize;

	@Value("${cronservice.report.timestamppattern}")
	private String timeStampPattern;

	@Scheduled(cron = "0 0/30 * * * ?")
	public void generateMisReport() {
		List<Customer> customers = customerRepository.findAll();

		Path reportPath = Path.of(misFileName);

		if (Files.exists(reportPath)) {
			String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern(timeStampPattern));
			Path backupReportPath = Path.of(backupFilePrefix + timestamp + csv);
			try {
				Files.copy(reportPath, backupReportPath, StandardCopyOption.REPLACE_EXISTING);
				logger.info("Backup of existing report created: " + backupReportPath.toAbsolutePath());
			} catch (IOException e) {
				logger.error("Failed to create a backup of the existing report: ", e);
			}
		}

		try (FileWriter writer = new FileWriter(reportPath.toFile())) {
			writer.append("CustomerName, Category, Occupation, MobileNumber\n");
			customers.stream().map(customer -> String.join(", ", customer.getCustomerName(), customer.getCategory(),
					customer.getOccupation(), customer.getMobileNumber())).forEach(line -> {
						try {
							writer.append(line).append("\n");
						} catch (IOException e) {
							logger.error("Error while writing customer data to the report: ", e);
						}
					});
			logger.info("MIS Report generated: " + reportPath.toAbsolutePath());
		} catch (IOException e) {
			logger.error("Error while generating MIS report: ", e);
		}
	}

	@Scheduled(cron = "0 0/30 * * * ?")
	public void detectDuplicateCustomers() {
		List<Customer> customers = customerRepository.findAll();
		ExecutorService executor = Executors.newFixedThreadPool(threadPoolSize);
		AtomicBoolean fileWritten = new AtomicBoolean(false);
		Set<String> existingDuplicates = readExistingDuplicates();
		customers.parallelStream()
				.filter(customer -> customerRepository.countByMobileNumberAndCustomerName(customer.getMobileNumber(),
						customer.getCustomerName()) > 1)
				.map(customer -> String.format("Duplicate detected for customer: %s | Mobile: %s%n",
						customer.getCustomerName(), customer.getMobileNumber()))
				.filter(duplicateInfo -> !existingDuplicates.contains(duplicateInfo)) // Append only new duplicates
				.forEach(duplicateInfo -> {
					if (fileWritten.compareAndSet(false, true)) {
						try {
							Path path = Path.of(duplicateFileName);
							Files.write(path, duplicateInfo.getBytes(), StandardOpenOption.CREATE,
									StandardOpenOption.APPEND);
							System.out.println("Duplicate written: " + duplicateInfo);
						} catch (IOException e) {
							System.err.println("Error writing to file: " + e.getMessage());
						}
					}
				});
		executor.shutdown();
	}

	private Set<String> readExistingDuplicates() {
		try {
			Path path = Path.of(duplicateFileName);
			if (Files.exists(path)) {
				return Files.lines(path).collect(Collectors.toSet());
			}
		} catch (IOException e) {
			System.err.println("Error reading existing duplicates file: " + e.getMessage());
		}
		return Set.of();
	}

	@EventListener(ApplicationReadyEvent.class) 
	public void runOnceAfterStartup() { 
	    System.out.println("Task executed once after startup."); 
	    
	    List<Customer> customerList = customerRepository.findAll(); 
	    
	    System.out.println("Customer Object>>> " + customerList.stream().collect(Collectors.toList())); 
	    
	    Map<String, List<Customer>> custMap = customerList.stream().collect(Collector.groupingBy((Customer.getCategorry)))
	    
	    Map<String, List<Customer>> custMap = customerList.stream().collect(Collectors.groupingBy(Customer::getCategory)); 
	    custMap.forEach((category, customers) -> { 
	        System.out.println(category);
	        for (Customer customer : customers) { 
	            System.out.println(customer);
	        } 
	    });

	    Map<String, List<Customer>> customersByCategory = customerList.stream()
	        .collect(Collectors.groupingBy(Customer::getCategory)); 
	    
	    customersByCategory.forEach((category, customers) -> { 
	        System.out.println("Category: " + category); 
	        customers.forEach(System.out::println); 
	    });

	    Map<String, Long> customerMapCount = customerList.stream()
	        .collect(Collectors.groupingBy(Customer::getOccupation, Collectors.counting())); 
	    
	    customerMapCount.forEach((occupation, count) -> { 
	        System.out.println(occupation + "-->" + count); 
	    });

	    List<String> customerDataList = customerList.stream()
	        .map(Customer::getCustomerName)
	        .collect(Collectors.toList()); 
	    
	    customerDataList.forEach(System.out::println);

	    List<Customer> customersStartingWithA = customerList.stream()
	        .filter(c -> c.getCustomerName().startsWith("A"))
	        .collect(Collectors.toList());

	    customersStartingWithA.forEach(System.out::println); 
	}

}