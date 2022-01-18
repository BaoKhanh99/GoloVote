package com.bk.golovotespring.repository;

import com.bk.golovotespring.entity.AccountRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface AccountRoleRepository extends JpaRepository<AccountRole, Integer> {
    @Query(value = "select name from role join accountrole \n" +
            "on role.id_role = accountrole.id_role join account\n" +
            "on accountrole.id_account = account.id_account where account.id_account =?1",nativeQuery = true)
    List<String> getAllRoleName(Integer idUser);
}
