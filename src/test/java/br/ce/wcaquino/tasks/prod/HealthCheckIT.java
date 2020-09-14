package br.ce.wcaquino.tasks.prod;

import java.util.concurrent.TimeUnit;

import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class HealthCheckIT {
	
	@Test
	public void healthCheck() {
		System.setProperty("webdriver.chrome.driver", "C:\\webdrivers\\chromedriver\\85\\chromedriver.exe");
		
		// Inicialização normal (sem Grid)
		WebDriver driver = new ChromeDriver();
		try {
			driver.navigate().to("http://192.168.99.100:9999/tasks");
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			String version = driver.findElement(By.id("version")).getText();
			Assert.assertTrue(version.startsWith("build"));//a ideia é só ver se o front carrega com sucesso (pq se ele carregou com sucesso, siginifica que o banco e o backend tbm subiram com sucesso)
		}finally {
			driver.quit();
		}		
	}
}
