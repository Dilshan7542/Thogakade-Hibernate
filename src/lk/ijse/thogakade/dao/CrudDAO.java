package lk.ijse.thogakade.dao;

import lk.ijse.thogakade.entity.SuperEntity;
import org.hibernate.Session;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;

public interface CrudDAO<T extends SuperEntity,ID extends Serializable> extends SuperDAO{
        T save(Session session, T entity)throws SQLException;
        T update(Session session,T entity)throws SQLException;
        boolean delete(Session session,ID id)throws SQLException;
        T search(Session session,ID id)throws SQLException;
        List<T>getAll(Session session)throws SQLException;
}
