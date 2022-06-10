package com.practica.integracion;

import com.practica.integracion.DAO.AuthDAO;
import com.practica.integracion.DAO.GenericDAO;
import com.practica.integracion.DAO.User;
import com.practica.integracion.manager.SystemManager;
import com.practica.integracion.manager.SystemManagerException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.naming.OperationNotSupportedException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TestValidUser {

	private static InOrder inOrder;
	private static final String validArgument = "validArgument";
	private static final String invalidArgument = "invalidArgument";
	private static ArrayList<Object> expected;
	private User validUser;
	private SystemManager systemManager;

	@Mock
	private static AuthDAO mockAuthDao;
	@Mock
	private static GenericDAO mockGenericDao;

	@BeforeEach
	public  void onCreateTest() {
		validUser= new User("ValidId","Robert","Pattinson","Av.Albacete",
				new ArrayList<Object>(Arrays.asList(1, 2)));
		expected =  new ArrayList<>(Arrays.asList("hola", "adios"));
		when(mockAuthDao.getAuthData(validUser.getId())).thenReturn(validUser);
	}

	@Test
	@DisplayName("Valid User startRemoteSystem")
	public void testStartRemoteSystem() throws OperationNotSupportedException {

		when(mockGenericDao.getSomeData(validUser, "where id=" + validArgument)).thenReturn(expected);
		inOrder = inOrder(mockAuthDao, mockGenericDao);
		systemManager = new SystemManager(mockAuthDao, mockGenericDao);
		Collection<Object> valueReturned = null;
		try {
			valueReturned = systemManager.startRemoteSystem(validUser.getId(), validArgument);
		} catch (SystemManagerException e) {
			e.printStackTrace();
		}
		assertEquals(valueReturned.toString(), "[hola, adios]");
		inOrder.verify(mockAuthDao, times(1)).getAuthData(validUser.getId());
		inOrder.verify(mockGenericDao, times(1)).getSomeData(validUser, "where id=" + validArgument);
	}

	@Test
	@DisplayName("Valid User stopRemoteSystem")
	public void testStopRemoteSystem() throws OperationNotSupportedException, SystemManagerException{
		when(mockGenericDao.getSomeData(validUser, "where id=" + validArgument)).thenReturn(expected);
		inOrder = inOrder(mockAuthDao, mockGenericDao);
		systemManager = new SystemManager(mockAuthDao, mockGenericDao);
		Collection<Object> valueReturned = systemManager.stopRemoteSystem(validUser.getId(), validArgument);
		assertEquals(valueReturned.toString(), "[hola, adios]");
		inOrder.verify(mockAuthDao, times(1)).getAuthData(validUser.getId());
		inOrder.verify(mockGenericDao, times(1)).getSomeData(validUser, "where id=" + validArgument);

	}

	@Test
	@DisplayName("Valid User addRemoteSystem")
	public void testAddRemoteSystem() throws OperationNotSupportedException{

		when(mockGenericDao.updateSomeData(validUser, validArgument)).thenReturn(true);
		inOrder = inOrder(mockAuthDao, mockGenericDao);
		systemManager = new SystemManager(mockAuthDao, mockGenericDao);
		Assertions.assertDoesNotThrow(() -> {systemManager.addRemoteSystem(validUser.getId(), validArgument);});
		inOrder.verify(mockAuthDao, times(1)).getAuthData(validUser.getId());
		inOrder.verify(mockGenericDao, times(1)).updateSomeData(validUser, validArgument);


	}


	//Aunque el usuario sea valido, como dentro del método se genera un usuario distinto no se puede saber si
	//realmente se elimina lo deseado o no gracias al usuario valido o porque vale cualquier usuario, más adelante
	// en las pruebas de usuario invalido se puede comprobar que es porque en deleteRemoteSystem se puede eliminar
	// el argumento deseado con cualquier usuario
	@Test
	@DisplayName("Valid User deleteRemoteSystem")
	public void testDeleteRemoteSystem() throws OperationNotSupportedException {

		when(mockGenericDao.deleteSomeData(validUser, "where id=" + validArgument)).thenReturn(true);
		inOrder = inOrder(mockAuthDao, mockGenericDao);
		systemManager = new SystemManager(mockAuthDao, mockGenericDao);
		Assertions.assertDoesNotThrow(() -> {systemManager.deleteRemoteSystem(validUser.getId(), validArgument);});
		inOrder.verify(mockAuthDao, times(1)).getAuthData(validUser.getId());
		inOrder.verify(mockGenericDao, times(1)).deleteSomeData(validUser, "where id=" + validArgument);
	}

	@Test
	@DisplayName("Valid User startRemoteSystem Invalid System")
	public void testStartRemoteSystemInvalidSystem() throws OperationNotSupportedException {

		when(mockGenericDao.getSomeData(validUser, "where id=" + invalidArgument))
				.thenThrow(OperationNotSupportedException.class);
		inOrder = inOrder(mockAuthDao, mockGenericDao);
		systemManager = new SystemManager(mockAuthDao, mockGenericDao);
		assertThrows(SystemManagerException.class, () -> {
			systemManager.startRemoteSystem(validUser.getId(),invalidArgument);});
		inOrder.verify(mockAuthDao, times(1)).getAuthData(validUser.getId());
		inOrder.verify(mockGenericDao, times(1)).getSomeData(validUser, "where id=" + invalidArgument);

	}

	@Test
	@DisplayName("Valid User stopREmoteSystem Invalid System")
	public void testStopRemoteSystemInvalidSystem() throws OperationNotSupportedException{
		when(mockGenericDao.getSomeData(validUser, "where id=" + invalidArgument))
				.thenThrow(OperationNotSupportedException.class);
		inOrder = inOrder(mockAuthDao, mockGenericDao);
		systemManager = new SystemManager(mockAuthDao, mockGenericDao);
		assertThrows(SystemManagerException.class, () -> {
			systemManager.stopRemoteSystem(validUser.getId(),invalidArgument);});
		inOrder.verify(mockAuthDao, times(1)).getAuthData(validUser.getId());
		inOrder.verify(mockGenericDao, times(1)).getSomeData(validUser, "where id=" + invalidArgument);

	}



	@Test
	@DisplayName("Valid User addRemoteSystem Invalid System")
	public void testAddRemoteSystemInvalidSystem() throws OperationNotSupportedException{

		when(mockGenericDao.updateSomeData(validUser, invalidArgument)).thenReturn(false);
		inOrder = inOrder(mockAuthDao, mockGenericDao);
		systemManager = new SystemManager(mockAuthDao, mockGenericDao);

		Assertions.assertThrows(SystemManagerException.class,() -> {systemManager.addRemoteSystem(validUser.getId(), invalidArgument);}
				,"cannot add remote");
		inOrder.verify(mockAuthDao, times(1)).getAuthData(validUser.getId());
		inOrder.verify(mockGenericDao, times(1)).updateSomeData(validUser, invalidArgument);

	}
	//Aunque el usuario sea valido, como dentro del método se genera un usuario distinto no se puede saber si
	//realmente se elimina lo deseado o no gracias al usuario valido o porque vale cualquier usuario, más adelante
	// en las pruebas de usuario invalido se puede comprobar que es porque en deleteRemoteSystem se puede eliminar
	// el argumento deseado con cualquier usuario
	@Test
	@DisplayName("Valid User deleteRemoteSystem Invalid system")
	public void testDeleteRemoteSystemInvalidSystem() throws OperationNotSupportedException {

		when(mockGenericDao.deleteSomeData(validUser, invalidArgument)).thenReturn(false);
		inOrder = inOrder(mockAuthDao, mockGenericDao);
		systemManager = new SystemManager(mockAuthDao, mockGenericDao);
		Assertions.assertThrows(SystemManagerException.class,() -> {systemManager.deleteRemoteSystem(validUser.getId(), invalidArgument);},
				"cannot delete remote: does remote exists?");

		inOrder.verify(mockAuthDao, times(1)).getAuthData(validUser.getId());
		inOrder.verify(mockGenericDao, times(1)).deleteSomeData(validUser, invalidArgument);
	}
}
