package ru.nsu.picaptcha.data.dao.impl;

import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import ru.nsu.picaptcha.data.dao.ISessionDao;

@Repository("sessionDao")
public class SessionDao implements ISessionDao {

  @Autowired
  private SessionFactory sessionFactory;
  private Logger logger = Logger.getLogger(this.getClass());
  
}
