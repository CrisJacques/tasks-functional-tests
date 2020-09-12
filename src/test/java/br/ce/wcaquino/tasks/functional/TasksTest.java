package br.ce.wcaquino.tasks.functional;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

public class TasksTest {
	
	public WebDriver acessarAplicacao() throws MalformedURLException {
		System.setProperty("webdriver.chrome.driver", "C:\\webdrivers\\chromedriver\\85\\chromedriver.exe");
		
		// Inicialização normal (sem Grid)
		//		WebDriver driver = new ChromeDriver();//como foi colocado no path o caminho até o ChromeDriver, não precisa setar o caminho do driver aqui (mas no build do Jenkins dá erro, aí preciso colocar o caminho aqui)
		
		// Inicialização usando Selenium Grid
		DesiredCapabilities cap = DesiredCapabilities.chrome();
		WebDriver driver;
		driver = new RemoteWebDriver(new URL("http://192.168.99.100:4444/wd/hub"), cap);//peguei essa URL do prompt onde o hub está rodando
		// se quiser rodar na minha máquina, o ip é: 192.168.1.106
		// se quiser rodar no ambiente dockerizado, o ip é: 192.168.99.100
		
		//Daqui pra baixo é igual usando Selenium Grid ou não
		driver.navigate().to("http://192.168.1.106:8080/tasks");//não dá pra usar localhost se rodar em ambiente dockerizado, pq a aplicação não estará dentro dos containeres!
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		return driver;
	}
	
	@Test
	public void deveSalvarTarefaComSucesso() throws MalformedURLException {
		WebDriver driver = acessarAplicacao();
		try {
			//clicar em Add Todo
			driver.findElement(By.id("addTodo")).click();		
			
			//escrever a descrição
			driver.findElement(By.id("task")).sendKeys("Teste via Selenium");
			
			//escrever a data
			driver.findElement(By.id("dueDate")).sendKeys("10/10/2030");		
			
			//clicar em salvar
			driver.findElement(By.id("saveButton")).click();
			
			//validar mensagem de sucesso
			String message = driver.findElement(By.id("message")).getText();
			Assert.assertEquals("Success!", message);
		} finally {
			//fechar o browser
			driver.quit();
		}
				
	}
	
	@Test
	public void naoDeveSalvarTarefaSemDescricao() throws MalformedURLException {
		WebDriver driver = acessarAplicacao();
		try {
			//clicar em Add Todo
			driver.findElement(By.id("addTodo")).click();		
			
			//escrever a data
			driver.findElement(By.id("dueDate")).sendKeys("10/10/2030");		
			
			//clicar em salvar
			driver.findElement(By.id("saveButton")).click();
			
			//validar mensagem de sucesso
			String message = driver.findElement(By.id("message")).getText();
			Assert.assertEquals("Fill the task description", message);
		} finally {
			//fechar o browser
			driver.quit();
		}
				
	}
	
	@Test
	public void naoDeveSalvarTarefaSemData() throws MalformedURLException {
		WebDriver driver = acessarAplicacao();
		try {
			//clicar em Add Todo
			driver.findElement(By.id("addTodo")).click();		
			
			//escrever a descrição
			driver.findElement(By.id("task")).sendKeys("Teste via Selenium");
			
			//clicar em salvar
			driver.findElement(By.id("saveButton")).click();
			
			//validar mensagem de sucesso
			String message = driver.findElement(By.id("message")).getText();
			Assert.assertEquals("Fill the due date", message);
		} finally {
			//fechar o browser
			driver.quit();
		}
				
	}
	
	@Test
	public void naoDeveSalvarTarefaComDataPassada() throws MalformedURLException {
		WebDriver driver = acessarAplicacao();
		try {
			//clicar em Add Todo
			driver.findElement(By.id("addTodo")).click();		
			
			//escrever a descrição
			driver.findElement(By.id("task")).sendKeys("Teste via Selenium");
			
			//escrever a data
			driver.findElement(By.id("dueDate")).sendKeys("10/10/2010");		
			
			//clicar em salvar
			driver.findElement(By.id("saveButton")).click();
			
			//validar mensagem de sucesso
			String message = driver.findElement(By.id("message")).getText();
			Assert.assertEquals("Due date must not be in past", message);
		} finally {
			//fechar o browser
			driver.quit();
		}
				
	}

}
