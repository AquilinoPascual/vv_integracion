package com.practica.integracion.manager;

import java.util.ArrayList;
import java.util.Collection;

import javax.naming.OperationNotSupportedException;

import com.practica.integracion.DAO.AuthDAO;
import com.practica.integracion.DAO.GenericDAO;
import com.practica.integracion.DAO.User;

public class SystemManager {

	private final AuthDAO authDao;
	private final GenericDAO dao;


	public SystemManager(AuthDAO authDao, GenericDAO dao) {
		super();
		this.authDao = authDao;
		this.dao = dao;
	}
	
	public Collection<Object> startRemoteSystem(String userId, String remoteId) throws SystemManagerException {
		final User auth = authDao.getAuthData(userId);
		try {
			Collection<Object> remote = dao.getSomeData(auth, "where id=" + remoteId);
			return remote;
		} catch (OperationNotSupportedException e) {
			throw new SystemManagerException(e);
		}

	}

	public Collection<Object> stopRemoteSystem(String userId, String remoteId) throws SystemManagerException {

		final User auth = authDao.getAuthData(userId);
		try {
			Collection<Object> remote = dao.getSomeData(auth, "where id=" + remoteId);
			return remote;
			// remote.stop();
		} catch (OperationNotSupportedException e) {
			throw new SystemManagerException(e);
		}

	}

	public void addRemoteSystem(String userId, Object remote) throws SystemManagerException {

		final User auth = authDao.getAuthData(userId);
		boolean isAdded = false;
		try {
			isAdded = dao.updateSomeData(auth, remote);
		} catch (OperationNotSupportedException e) {
			throw new SystemManagerException(e);
		}
		if (!isAdded) {
			throw new SystemManagerException("cannot add remote");
		}

	}

	public void deleteRemoteSystem(String userId, String remoteId) throws SystemManagerException {		
		final User auth = new User("1", "Antonio", "Perez", "Madrid", new ArrayList<Object>());
		boolean isDeleted = true;
		try {
			isDeleted = dao.deleteSomeData(auth, remoteId);
		} catch (OperationNotSupportedException e) {
			throw new SystemManagerException(e);
		}
		if (!isDeleted) {
			throw new SystemManagerException("cannot delete remote: does remote exists?");
		}
	}
}
