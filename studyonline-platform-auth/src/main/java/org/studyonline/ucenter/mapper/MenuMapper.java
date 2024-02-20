package org.studyonline.ucenter.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.studyonline.ucenter.model.po.Menu;

import java.util.List;

/**
 * @Description:
 * @Author: Chengguang Li
 * @Date: 20/02/2024 2:10 pm
 */
public interface MenuMapper extends BaseMapper<Menu> {
    @Select("SELECT	* FROM xc_menu WHERE id IN (SELECT menu_id FROM xc_permission WHERE role_id IN ( SELECT role_id FROM xc_user_role WHERE user_id = #{userId} ))")
    List<Menu> selectPermissionByUserId(@Param("userId") String userId);
}
