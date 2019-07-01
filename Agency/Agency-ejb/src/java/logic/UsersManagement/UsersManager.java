/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic.UsersManagement;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import logic.Config;
import logic.DTOFactory;
import logic.NoPermissionException;
import logic.SignInValue;
import logic.TUserDTO;

/**
 *
 * @author bruno
 */
@Singleton
public class UsersManager implements UsersManagerLocal {

    @EJB
    TUserFacadeLocal userFacade;
    
    @PostConstruct
    private void init() {
        verifyIfAdminExist();
    }
    
    @Override
    public SignInValue signIn(String username, String password) {
        TUser user = getTUserByUsername(username);
        
        if(user == null)
            return SignInValue.NOT_FOUND;
        
        if(!user.getPassword().equals(password))
            return SignInValue.WRONG_CREDENTIALS;
        
        return user.getAccepted() ? SignInValue.SUCCESS : SignInValue.NOT_ACCEPTED;
    }
    
    @Override
    public boolean signUp(TUserDTO userDTO) {
        
        
        TUser useraux = getTUserByUsername(userDTO.getUsername());
        
        if(useraux!=null)
            return false;
        
        if(userDTO.getUsertype() != Config.CLIENT && userDTO.getUsertype() != Config.OPERATOR)
            return false;
        
        TUser newUser = new TUser();
        
        newUser.setUsername(userDTO.getUsername());
        newUser.setPassword(userDTO.getPassword());
        newUser.setUsertype(userDTO.getUsertype());
        newUser.setAccepted(false);     
        newUser.setTPurchaseCollection(new ArrayList());
        
        if(userDTO.getUsertype()==Config.CLIENT)
        {
            newUser.setBalance((double) 0);
            newUser.setClientname(userDTO.getClientName());
        }
        
        userFacade.create(newUser);
        return true;
    }
    
    @Override
    public boolean acceptTUser(TUserDTO userDTO, String username) {
        TUser user = getTUserByUsername(userDTO.getUsername());
        if(user == null)
            return false;
        user.setAccepted(true);
        return true;
        
    }
    
    @Override
    public TUserDTO getTUserDTO(String username) {
        if(username == null)
            return null;
        TUser user = getTUserByUsername(username);
        return DTOFactory.getTUserDTOFromTUser(user);
    }
    
    
    @Override
    public TUser getTUserByUsername(String usernameOfWantedUser){
        for(TUser user : userFacade.findAll())
        {
            if(user.getUsername().equals(usernameOfWantedUser))
                return user;
        }
        return null;
    }
    
    @Override
    public boolean depositToAccount(float amount, String username) {
        TUser user = getTUserByUsername(username);
        
        if(user == null)
            return false;
        
        if(amount <= 0)
            return false;
        
        user.setBalance(user.getBalance() + amount);
        userFacade.edit(user);
        
        return true;
        
    }
    
    @Override
    public Double getBalance(String username) {
        TUser user = getTUserByUsername(username);
        
        if(user == null)
            return 0.0;
        
        return user.getBalance();
    }
    
    @Override
    public List<TUserDTO> findAllUsers() {
        List<TUserDTO> userList = new ArrayList();
        
        for(TUser user : userFacade.findAll())
        {
            userList.add(DTOFactory.getTUserDTOFromTUser(user));
        }
        return userList;
    }
    
    
    //Auxiliary methoths

    private void verifyIfAdminExist() {
        if(getTUserByUsername("admin")==null)
        {
            TUser admin = new TUser();
            admin.setAccepted(true);
            admin.setUsername("admin");
            admin.setPassword("admin");
            admin.setUsertype(Config.OPERATOR);
            
            userFacade.create(admin);
        }
    }

    @Override
    public boolean editTUser(TUser userTmp) {
        if(userTmp == null)
            return false;
        userFacade.edit(userTmp);
        return true;
    }
    
    @Override
    public void verifyPermission(String username, int permissionType) throws NoPermissionException{
        String errorMessage = (Config.CLIENT == permissionType? Config.MSG_NO_PERMISSION: Config.MSG_NO_PERMISSION_OPERATOR);
        
        if(username == null || username.isEmpty())
            throw new NoPermissionException(errorMessage);
        
        TUserDTO userDTO = getTUserDTO(username);
        
        if(userDTO == null)
            throw new NoPermissionException(errorMessage);
        
        if(!userDTO.getAccepted())
            throw new NoPermissionException(errorMessage);       

        //se for um cliente e a permissao exigida for do tipo de cliente permite... caso contrario nao deixa (os operadores podem fazer tudo, portanto nao fiz validacao para os operadores)
        if(userDTO.getUsertype() != permissionType)
            throw new NoPermissionException(errorMessage);       
    }

    @Override
    public TUserDTO findUser(int id) {
        TUser user = userFacade.find(id);
        
        if(user == null)
            return null;
        
        return DTOFactory.getTUserDTOFromTUser(user);
    }

    
}
