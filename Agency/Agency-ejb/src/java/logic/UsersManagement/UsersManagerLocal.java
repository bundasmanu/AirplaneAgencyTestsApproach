/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic.UsersManagement;

import java.util.List;
import javax.ejb.Local;
import logic.NoPermissionException;
import logic.SignInValue;
import logic.TUserDTO;

/**
 *
 * @author bruno
 */
@Local
public interface UsersManagerLocal {
    SignInValue signIn(String username, String password);
    boolean signUp(TUserDTO userDTO);
    boolean acceptTUser(TUserDTO userDTO, String username);
    boolean editTUser(TUser userTmp);
    TUserDTO getTUserDTO(String username);
    TUser getTUserByUsername(String usernameOfWantedUser);
    boolean depositToAccount(float amount,String username);
    Double getBalance(String username);
    List<TUserDTO> findAllUsers();
    TUserDTO findUser(int id);
    void verifyPermission(String username, int permissionType) throws NoPermissionException;

}
