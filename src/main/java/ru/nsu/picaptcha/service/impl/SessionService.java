package ru.nsu.picaptcha.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import ru.nsu.picaptcha.data.dao.ISessionDao;
import ru.nsu.picaptcha.service.ISessionService;

@Service("sessionService")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class SessionService implements ISessionService {

  @Autowired
  private ISessionDao sessionDao;
  
}
