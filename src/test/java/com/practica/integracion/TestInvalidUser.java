package com.practica.integracion;

import com.practica.integracion.DAO.AuthDAO;
import com.practica.integracion.DAO.GenericDAO;
import com.practica.integracion.DAO.User;
import com.practica.integracion.manager.SystemManager;
import com.practica.integracion.manager.SystemManagerException;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.naming.OperationNotSupportedException;
import java.util.ArrayList;
import java.util.Arrays;

import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TestInvalidUser {

	private static InOrder inOrder;
	private static final String validArgument = "validArgument";
	private static final String invalidArgument = "invalidArgument";
	private User invalidUser;
	private SystemManager systemManager;

	@Mock
	private static AuthDAO mockAuthDao;
	@Mock
	private static GenericDAO mockGenericDao;

	@BeforeEach
	public  void onCreateTest() {
		invalidUser= new User("invalidId","Robert","Pattinson","Av.Albacete",
				new ArrayList<Object>(Arrays.asList(1, 2)));
		when(mockAuthDao.getAuthData(invalidUser.getId())).thenReturn(null);
	}
	@Test
	@DisplayName("Invalid User startRemoteSystem")
	public void testInvalidStartRemoteSystem() throws OperationNotSupportedException {
		when(mockGenericDao.getSomeData(null, "where id=" + validArgument)).
				thenThrow(new OperationNotSupportedException("Invalid user"));
		inOrder = inOrder(mockAuthDao,mockGenericDao);
		systemManager = new SystemManager(mockAuthDao, mockGenericDao);
		Assertions.assertThrows(SystemManagerException.class,() -> {systemManager.startRemoteSystem(invalidUser.getId(), validArgument);});
		inOrder.verify(mockAuthDao).getAuthData(invalidUser.getId());
		inOrder.verify(mockGenericDao).getSomeData(null, "where id=" + validArgument);

	}

	@Test
	@DisplayName("Invalid User stopRemoteSystem")
	public void testStopRemoteSystem() throws OperationNotSupportedException {
		when(mockGenericDao.getSomeData(null, "where id=" + validArgument)).
				thenThrow(new OperationNotSupportedException("Invalid user"));

		inOrder = inOrder(mockAuthDao,mockGenericDao);
		systemManager = new SystemManager(mockAuthDao, mockGenericDao);
		Assertions.assertThrows(SystemManagerException.class,() -> {systemManager.stopRemoteSystem(invalidUser.getId(), validArgument);});
		inOrder.verify(mockAuthDao).getAuthData(invalidUser.getId());
		inOrder.verify(mockGenericDao).getSomeData(null, "where id=" + validArgument);
	}

	@Test
	@DisplayName("Invalid User addRemoteSystem")
	public void testAddRemoteSystem() throws OperationNotSupportedException {
		String newAddress = "Wisconsin";
		when(mockGenericDao.updateSomeData(null, newAddress))
				.thenThrow(new OperationNotSupportedException("Invalid user"));
		inOrder = inOrder(mockAuthDao, mockGenericDao);
		systemManager = new SystemManager(mockAuthDao, mockGenericDao);
		Assertions.assertThrows(SystemManagerException.class,() -> {systemManager.addRemoteSystem(invalidUser.getId(), newAddress);});
		inOrder.verify(mockAuthDao).getAuthData(invalidUser.getId());
		inOrder.verify(mockGenericDao).updateSomeData(null, newAddress);
	}

	@Test
	@DisplayName("Invalid User deleteRemoteSystem")
	public void testDeleteRemoteSystem() throws OperationNotSupportedException {
		when(mockGenericDao.deleteSomeData(null, "where id=" + validArgument))
				.thenThrow(new OperationNotSupportedException("Invalid user"));
		inOrder = inOrder(mockAuthDao, mockGenericDao);
		systemManager = new SystemManager(mockAuthDao, mockGenericDao);
		Assertions.assertThrows(SystemManagerException.class,() -> {systemManager.deleteRemoteSystem(invalidUser.getId(), validArgument);});
		inOrder.verify(mockAuthDao).getAuthData(invalidUser.getId());
		inOrder.verify(mockGenericDao).deleteSomeData(null, "where id=" + validArgument);
	}
	@Test
	@DisplayName("Invalid User startRemoteSystem and invalidArgument")
	public void testInvalidStartRemoteSystemInvalidArgument() throws OperationNotSupportedException {
		when(mockGenericDao.getSomeData(null, "where id=" + invalidArgument)).
				thenThrow(new OperationNotSupportedException("Invalid user"));
		inOrder = inOrder(mockAuthDao,mockGenericDao);
		systemManager = new SystemManager(mockAuthDao, mockGenericDao);
		Assertions.assertThrows(SystemManagerException.class,() -> {systemManager.startRemoteSystem(invalidUser.getId(), invalidArgument);});
		inOrder.verify(mockAuthDao).getAuthData(invalidUser.getId());
		inOrder.verify(mockGenericDao).getSomeData(null, "where id=" + invalidArgument);

	}

	@Test
	@DisplayName("Invalid User stopRemoteSystem and invalidArgument")
	public void testStopRemoteSystemInvalidArgument() throws OperationNotSupportedException {
		when(mockGenericDao.getSomeData(null, "where id=" + invalidArgument)).
				thenThrow(new OperationNotSupportedException("Invalid user"));
		inOrder = inOrder(mockAuthDao,mockGenericDao);
		systemManager = new SystemManager(mockAuthDao, mockGenericDao);
		Assertions.assertThrows(SystemManagerException.class,() -> {systemManager.stopRemoteSystem(invalidUser.getId(), invalidArgument);});
		inOrder.verify(mockAuthDao).getAuthData(invalidUser.getId());
		inOrder.verify(mockGenericDao).getSomeData(null, "where id=" + invalidArgument);
	}

	@Test
	@DisplayName("Invalid User addRemoteSystem and invalidArgument")
	public void testAddRemoteSystemInvalidArgument() throws OperationNotSupportedException {
		String invalidAddress = "Wi**sc|@#o2nsin";
		when(mockGenericDao.updateSomeData(null, invalidAddress)).
				thenThrow(new OperationNotSupportedException("Invalid user and argument"));
		inOrder = inOrder(mockAuthDao, mockGenericDao);
		systemManager = new SystemManager(mockAuthDao, mockGenericDao);
		Assertions.assertThrows(SystemManagerException.class,() -> {systemManager.addRemoteSystem(invalidUser.getId(), invalidAddress);});
		inOrder.verify(mockAuthDao).getAuthData(invalidUser.getId());
		inOrder.verify(mockGenericDao).updateSomeData(null, invalidAddress);
	}

	@Test
	@DisplayName("Invalid User deleteRemoteSystem and invalidArgument")
	public void testDeleteRemoteSystemInvalidArgument() throws OperationNotSupportedException {
		when(mockGenericDao.deleteSomeData(null, "where id=" + invalidArgument))
				.thenThrow(new OperationNotSupportedException("Invalid user and argument"));
		inOrder = inOrder(mockAuthDao, mockGenericDao);
		systemManager = new SystemManager(mockAuthDao, mockGenericDao);
		Assertions.assertThrows(SystemManagerException.class,() -> {systemManager.deleteRemoteSystem(invalidUser.getId(), invalidArgument);});
		inOrder.verify(mockAuthDao).getAuthData(invalidUser.getId());
		inOrder.verify(mockGenericDao).deleteSomeData(null, "where id=" + invalidArgument);
	}

}
